package main.java.ru.nsu.shchiptsov;

import com.dampcake.bencode.BencodeInputStream;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;


public class Client {
	private String nameMetainfoFile;
	private int countDownloadPiece = 0;
	private Handshake handshake = new Handshake ();
	private Map<String, Object> metainfoFile;
	private Messages messages = new Messages ();
	private List<String[]> availablePeers = new ArrayList<> ();
	private int countServers;

	Client (String file, String[] ports) {
		nameMetainfoFile = file;
		countServers = ports.length - 3;
		try {
			for (int i = 3; i < ports.length; ++i) {
				if (Server.numPort != Integer.parseInt (ports[i])) {
					availablePeers.add ( new String[]{
							InetAddress.getLocalHost ().getHostAddress (),
							String.valueOf (Integer.parseInt (ports[i]))});
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace ();
		}
		go ();
	}

	private void decodeMetaFile () {
		try (FileInputStream input = new FileInputStream (nameMetainfoFile)) {
			BencodeInputStream bencode = new BencodeInputStream (input);
			metainfoFile = bencode.readDictionary ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private void buildingFile () {
		try (FileOutputStream output = new FileOutputStream (
				("Download" + new String (handshake.getPeerID (), StandardCharsets.UTF_8) + metainfoFile.get ("name")),
				true)) {
			for (int i = 0; i < Server.blocks.get (metainfoFile.get ("name")).size (); ++i) {
				output.write (Server.blocks.get (metainfoFile.get ("name")).get (i).getBytes (StandardCharsets.UTF_8));
			}
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private List<byte[]> parserStringSHA1toByte (List<String> str) {
		List<byte[]> sha1 = new ArrayList<> ();
		for (int i = 0; i < str.size (); ++i) {
			String[] s = str.get (i).replace ("[", "").replace ("]", "")
								 .replace (" ", "").split (",");
			byte[] tmp = new byte[20];
			for (int j = 0; j < 20; ++j) {
				tmp[j] = Byte.parseByte (s[j]);
			}
			sha1.add (tmp);
		}
		return sha1;
	}

	private boolean checkPart (int index, List<byte[]> sha1) {
		MessageDigest tmpSHA1 = null;

		try {
			tmpSHA1 = MessageDigest.getInstance ("SHA-1");
		} catch (Exception e) {
			e.printStackTrace ();
		}

		byte[] tmp = Server.blocks.get (metainfoFile.get ("name")).get (index).getBytes (StandardCharsets.UTF_8);
		tmpSHA1.update (tmp, 0, tmp.length);
		byte[] byteTmpSHA1 = tmpSHA1.digest ();
		for (int i = 0; i < 20; ++i) {
			if (sha1.get (index)[i] != byteTmpSHA1[i]) {
				return false;
			}
		}
		if (Server.SHA1Parts.get (metainfoFile.get ("name")) == null) {
			ArrayList tmpArrayList = new ArrayList ();
			tmpArrayList.add (Arrays.toString (byteTmpSHA1));
			Server.SHA1Parts.put ((String) metainfoFile.get ("name"), tmpArrayList);
		} else {
			Server.SHA1Parts.get (metainfoFile.get ("name")).add (Arrays.toString (byteTmpSHA1));
		}
		return true;
	}

	public void go () {
			Socket clientSocket = null;
			decodeMetaFile ();
			List<byte[]> sha1 = parserStringSHA1toByte ((List<String>) metainfoFile.get ("pieces"));
			try {
				while (countDownloadPiece < sha1.size ()) {
					for (int i = 0; i < countServers; ++i) {
						try {
							clientSocket = new Socket (availablePeers.get (i)[0],
													   Integer.parseInt (availablePeers.get (i)[1]));
						} catch (IOException e) {
							System.out.println (availablePeers.get (i)[1] + " port not available");
							try {
								Thread.sleep (3000);
							} catch (InterruptedException exception) {
								exception.printStackTrace ();
							}
							continue;
						}
						System.out.println ("connect to " + clientSocket);
						try {
							handshake.setSHA1 (sha1.get (countDownloadPiece));
							clientSocket.getOutputStream ().write (handshake.getHandshake ());
							byte[] newHandshake = new byte[68];
							clientSocket.getInputStream ().read (newHandshake);
							boolean flagConnection = true;
							for (int j = 0; j < 48; ++j) {
								if (newHandshake[j] != handshake.getHandshake ()[j]) {
									flagConnection = false;
									break;
								}
							}

							if (!flagConnection) {
								clientSocket.close ();
							} else {
								System.out.println (countDownloadPiece + " " +
													"download from " + clientSocket);
								messages.setRequest (countDownloadPiece, 0, 512 * 1024);
								clientSocket.getOutputStream ().write (messages.sendMessage6 ());
								messages.recvMessageClient (clientSocket,
															Server.blocks,
															(String) metainfoFile.get ("name"));
								if (checkPart (countDownloadPiece, sha1)) {
									clientSocket.close ();
									++countDownloadPiece;
									messages.setHave (countDownloadPiece);
									messages.sendMessage4 (availablePeers,
														   Server.numPort);
									try {
										Thread.sleep (5000);
									} catch (InterruptedException e) {
										e.printStackTrace ();
									}
									break;
								}

							}
							clientSocket.close ();
						} catch (IOException e) {
							e.printStackTrace ();
						}
					}
				}
				if (countDownloadPiece == sha1.size ()) {
					System.out.println ("File downloaded");
					TorrentClient.executor.submit (this::buildingFile);
				}
			} finally {
				System.out.println ("Клиент был закрыт...");
				try {
					clientSocket.close ();
				} catch (IOException e) {
					e.printStackTrace ();
				}
			}

	}

	public static void main (String[] args) {
		Client client = new Client (args[0], args);
	}

}
