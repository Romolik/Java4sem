package main.java.ru.nsu.shchiptsov;

import com.dampcake.bencode.BencodeOutputStream;


import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;


public class Server {
	public static int numPort;
	private Handshake handshake = new Handshake ();
	private Messages messages = new Messages ();
	public static Map<String,ArrayList<String>> SHA1Parts = new HashMap<> ();
	public static Map<String, Map<Integer, String>> blocks = new HashMap<> ();
	private Map<Integer, Integer> downloadBlocks = new HashMap<> ();
	private final int pieceLength = 512 * 1024;

	Server (int num) {
		numPort = num;
		go ();
	}

	private void createMetaFile (String nameFile) {
		File file = new File (nameFile);
		final long length = file.length ();
		MessageDigest sha1 = null;
		List<String> SHA1 = new ArrayList<> ();

		try {
			sha1 = MessageDigest.getInstance ("SHA-1");
		} catch (Exception e) {
			e.printStackTrace ();
		}

		try (InputStream input = new FileInputStream (file)) {
			int index = 0;
			byte[] buffer = new byte[pieceLength];
			int len = input.read (buffer);
			while (len != -1) {
				Map<Integer, String> tmp = blocks.get ("fileTest.txt");
				if (tmp == null) {
					tmp = new HashMap<> ();
				}
				tmp.put (index, new String (buffer, StandardCharsets.UTF_8));
				blocks.put ("fileTest.txt", tmp);
				++index;
				sha1.update (buffer, 0, len);
				SHA1.add (Arrays.toString (sha1.digest ()));
				len = input.read (buffer);
			}
		} catch (Exception e) {
			e.printStackTrace ();
		}

		//SHA1Parts.get ("fileTest.txt").addAll (SHA1);

		try (FileOutputStream output = new FileOutputStream ("./src/main" +
															 "/resourses/metainfo2.txt")) {
			BencodeOutputStream bencoder = new BencodeOutputStream (output);
			bencoder.writeDictionary (new HashMap<> () {{
				put ("announce", InetAddress.getLocalHost ().getHostAddress () + " " +
					 numPort);
				put ("name", "fileTest2.txt");
				put ("piece length", String.valueOf (pieceLength));
				put ("length", String.valueOf (length));
				put ("pieces", SHA1);
			}});
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private void initializeServer () {
		MessageDigest sha1 = null;
		try {
			sha1 = MessageDigest.getInstance ("SHA-1");
		} catch (Exception e) {
			e.printStackTrace ();
		}
		// 4003 - содержит 2куска fileTest2.txt
		// 4004 - cодержит первые 5кусков файла fileTest.txt
		// 4005 - cодержит последние 5кусков файла fileTest.txt
		if (numPort == 4004 || numPort == 4005 || numPort == 4003) {
			String fileName;
			if (numPort == 4003) {
				fileName = "fileTest2.txt";
			} else {
				fileName = "fileTest.txt";
			}
			try (InputStream input = new FileInputStream ("./src/main" +
														  "/resourses/" + fileName)) {
				int index = 0;
				byte[] buffer = new byte[pieceLength];
				int len = input.read (buffer);
				while (len != -1) {
					if (numPort == 4004 && index % 2 == 0) {
						Map<Integer, String> tmp = blocks.get (fileName);
						if (tmp == null) {
							tmp = new HashMap<> ();
						}
						tmp.put (index, new String (buffer, StandardCharsets.UTF_8));
						blocks.put (fileName, tmp);
						sha1.update (buffer, 0, len);
						if (SHA1Parts.get (fileName) == null) {
							ArrayList tmpArrayList = new ArrayList ();
							tmpArrayList.add (Arrays.toString (sha1.digest ()));
							SHA1Parts.put (fileName, tmpArrayList);
						} else {
							SHA1Parts.get (fileName).add (Arrays.toString (sha1.digest ()));
						}
					} else if (numPort == 4005 && index % 2 == 1) {
						Map<Integer, String> tmp = blocks.get (fileName);
						if (tmp == null) {
							tmp = new HashMap<> ();
						}
						tmp.put (index, new String (buffer, StandardCharsets.UTF_8));
						blocks.put (fileName, tmp);
						sha1.update (buffer, 0, len);
						if (SHA1Parts.get (fileName) == null) {
							ArrayList tmpArrayList = new ArrayList ();
							tmpArrayList.add (Arrays.toString (sha1.digest ()));
							SHA1Parts.put (fileName, tmpArrayList);
						} else {
							SHA1Parts.get (fileName).add (Arrays.toString (sha1.digest ()));
						}
					} else  if (numPort == 4003){
						Map<Integer, String> tmp = blocks.get (fileName);
						if (tmp == null) {
							tmp = new HashMap<> ();
						}
						tmp.put (index, new String (buffer, StandardCharsets.UTF_8));
						blocks.put (fileName, tmp);
						sha1.update (buffer, 0, len);
						if (SHA1Parts.get (fileName) == null) {
							ArrayList tmpArrayList = new ArrayList ();
							tmpArrayList.add (Arrays.toString (sha1.digest ()));
							SHA1Parts.put (fileName, tmpArrayList);
						} else {
							SHA1Parts.get (fileName).add (Arrays.toString (sha1.digest ()));
						}
					}
					len = input.read (buffer);
					++index;
				}
			} catch (Exception e) {
				e.printStackTrace ();
			}
		}
	}

	public void go () {
		//createMetaFile ("./src/main/resourses/fileTest2.txt");
		if(numPort == 4003 || numPort == 4004 || numPort == 4005) {
			initializeServer ();
		}

		Selector selector = null;
		ServerSocket serverSocket = null;
		ByteBuffer buf = ByteBuffer.allocateDirect (512 * 1024 + 13);
		try {
			selector = Selector.open ();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open ();
			serverSocketChannel.configureBlocking (false);
			serverSocketChannel.socket ().bind (new InetSocketAddress (numPort));
			serverSocketChannel.register (selector, SelectionKey.OP_ACCEPT);
			serverSocket = serverSocketChannel.socket ();
		} catch (IOException e) {
			System.err.println ("Unable to setup environment");
			System.exit (-1);
		}
		try {
			System.out.println ("Сервер запущен!");
			while (true) {
				int count = selector.select ();
				if (count == 0) {
					continue;
				}
				Iterator it = selector.selectedKeys ().iterator ();
				while (it.hasNext ()) {
					SelectionKey selKey = (SelectionKey) it.next ();
					it.remove ();

					Socket socket;
					SocketChannel channel = null;
					buf.clear ();
					if (selKey.isAcceptable ()) {
						try {
							socket = serverSocket.accept ();
							System.out.println ("Connection from: " + socket);
							channel = socket.getChannel ();
						} catch (IOException e) {
							System.err.println ("Unable to accept channel");
							e.printStackTrace ();
							selKey.cancel ();
						}
						if (channel != null) {
							try {
								System.out.println ("Watch for something to read");
								channel.configureBlocking (false);
								channel.register (selector,
												  SelectionKey.OP_READ);
							} catch (IOException e) {
								System.err.println ("Unable to use channel");
								e.printStackTrace ();
								selKey.cancel ();
							}
						}
					}
					if (selKey.isReadable ()) {
						String nameFile = null;
						SocketChannel socketChannel =
								(SocketChannel) selKey.channel ();
						byte[] newHandshake = new byte[68];
						boolean flagConnection = true;
						socketChannel.read (ByteBuffer.wrap (newHandshake));
						byte[] SHA1 = new byte[20];
						if (newHandshake[0] == 19) {
							System.arraycopy (newHandshake, 28, SHA1, 0, 20);
							boolean contains = false;
							for (Map.Entry<String, ArrayList<String>> entry : SHA1Parts.entrySet ()) {
								if (entry.getValue ().contains (Arrays.toString (SHA1))) {
									contains = true;
									nameFile = entry.getKey ();
									break;
								}
							}

							if (contains) {
								handshake.setSHA1 (SHA1);
							} else {
								byte[] badSHA1 = new byte[20];
								for (int i = 0; i < 20; ++i) {
									badSHA1[i] = 0;
								}
								handshake.setSHA1 (badSHA1);
							}

							for (int i = 0; i < 48; ++i) {
								if (newHandshake[i] !=
									handshake.getHandshake ()[i]) {
									flagConnection = false;
									break;
								}
							}

							if (flagConnection) {
								socketChannel.write (ByteBuffer.wrap (handshake.getHandshake ()));
							} else {
								socketChannel.close ();
								continue;
							}
						} else {
							messages.recvMessageServer (ByteBuffer.wrap (newHandshake), blocks, socketChannel,
														downloadBlocks, nameFile);
							continue;
						}

							try {
								while (buf.hasRemaining ()) {
									if (socketChannel.read (buf) > 0) {
										buf.flip ();
										messages.recvMessageServer (buf, blocks, socketChannel, downloadBlocks,
																	nameFile);
										buf.clear ();
										buf.flip ();
									}
								}
							} catch (IOException e) {
								System.err.println ("Error writing back bytes");
								e.printStackTrace ();
								selKey.cancel ();
							}

							try {
								System.out.println ("Closing...");
								socketChannel.close ();
							} catch (IOException e) {
								e.printStackTrace ();
								selKey.cancel ();
							}
					}
					System.out.println ("Next...");
				}
			}
		} catch (IOException e) {
			System.err.println ("Error during select()");
			e.printStackTrace ();
		}
	}

	public static void main (String[] args) {
		Server server = new Server (Integer.parseInt (args[0]));
	}

}
