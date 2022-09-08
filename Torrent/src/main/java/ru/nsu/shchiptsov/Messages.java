package main.java.ru.nsu.shchiptsov;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {
	private String bitfield;
	private int messageLength;
	private byte messageType;
	private int have;
	private BtPiece piece = new BtPiece ();
	private BtRequest request = new BtRequest ();
	private BtRequest cancel = new BtRequest ();
	private char[] data;

	static class BtRequest {
		int index;
		int begin;
		int length;
		public byte[] getByte () {
			return ByteBuffer.allocate(12).order (ByteOrder.BIG_ENDIAN)
					.putInt (index).putInt (begin).putInt (length).array ();
		}
	}

	static class BtPiece {
		int index;
		int begin;
		String piece;
		public byte[] getByte () {
			return ByteBuffer.allocate(8 + piece.length ()).order (ByteOrder.BIG_ENDIAN)
						   .putInt (index).putInt (begin).put (piece.getBytes (StandardCharsets.UTF_8)).array ();
		}
	}

	public void setRequest (int index, int begin, int length) {
		request.index = index;
		request.begin = begin;
		request.length = length;
	}

	public void setHave (int h) {
		have = h;
	}

	public byte[] sendMessage0 () {
		messageLength = 1;
		messageType = 0;
		byte[] byteMessage =
				ByteBuffer.allocate(messageLength + 4).order (ByteOrder.BIG_ENDIAN)
						.putInt (messageLength).put (messageType).array ();
		return byteMessage;
	}

	public byte[] sendMessage1 () {
		messageLength = 1;
		messageType = 1;
		byte[] byteMessage =
				ByteBuffer.allocate(messageLength + 4).order (ByteOrder.BIG_ENDIAN)
						.putInt (messageLength).put (messageType).array ();
		return byteMessage;
	}

	public byte[] sendMessage2 () {
		messageLength = 1;
		messageType = 2;
		byte[] byteMessage =
				ByteBuffer.allocate(messageLength + 4).order (ByteOrder.BIG_ENDIAN)
						.putInt (messageLength).put (messageType).array ();
		return byteMessage;
	}

	public byte[] sendMessage3 () {
		messageLength = 1;
		messageType = 3;
		byte[] byteMessage =
				ByteBuffer.allocate(messageLength + 4).order (ByteOrder.BIG_ENDIAN)
						.putInt (messageLength).put (messageType).array ();
		return byteMessage;
	}

	public void sendMessage4 (List<String[]> availablePeers,
							   int numPort) {
		messageLength = 5;
		messageType = 4;
		byte[] byteMessage =
				ByteBuffer.allocate(messageLength + 8).order (ByteOrder.BIG_ENDIAN)
				.putInt (messageLength).put (messageType).putInt (have).putInt (numPort).array ();
		for (String[] availablePeer : availablePeers) {
			try {
				if (Integer.parseInt (availablePeer[1]) != Server.numPort) {
					Socket clientSocket = new Socket (availablePeer[0],
													  Integer.parseInt (
															  availablePeer[1]));
					clientSocket.getOutputStream ().write (byteMessage);
				}
			} catch (IOException e) {
				System.out.println ("Server not available");
			}
		}
	}

	public byte[] sendMessage5 () {
		//bitfield = "\1\1\0";
		messageLength = 1 + bitfield.length ();
		messageType = 5;
		byte[] byteMessage  = ByteBuffer.allocate(messageLength + 4).order (ByteOrder.BIG_ENDIAN)
								.putInt (messageLength).put (messageType)
								.put (bitfield.getBytes (StandardCharsets.UTF_8)).array ();
		return byteMessage;
	}

	public byte[] sendMessage6 () {
		messageLength = 13;
		messageType = 6;
		byte[] prefix = ByteBuffer.allocate(5).order (ByteOrder.BIG_ENDIAN)
								  .putInt (messageLength).put (messageType).array ();
		byte[] suffix = request.getByte ();
		return ByteBuffer.allocate (prefix.length + suffix.length).put (prefix).put (suffix).array ();
	}

	public byte[] sendMessage7 () {
		messageLength = 9 + piece.piece.length ();
		messageType = 7;
		byte[] prefix = ByteBuffer.allocate(5).order (ByteOrder.BIG_ENDIAN)
								.putInt (messageLength).put (messageType).array ();
		byte[] suffix = piece.getByte ();
		return ByteBuffer.allocate (prefix.length + suffix.length).put (prefix).put (suffix).array ();
	}

	public byte[] sendMessage8 () {
		messageLength = 13;
		messageType = 8;
		byte[] prefix = ByteBuffer.allocate(5).order (ByteOrder.BIG_ENDIAN)
								.putInt (messageLength).put (messageType).array ();
		byte[] suffix = cancel.getByte ();
		return ByteBuffer.allocate (prefix.length + suffix.length).put (prefix).put (suffix).array ();
	}

	public void recvMessageClient (Socket socket, Map<String, Map<Integer, String>> blocks,
								   String fileName) {
		byte [] type = new byte[1];
		int len;
		try {
			byte [] buf = new byte[4];
			socket.getInputStream ().read (buf, 0, 4);
			ByteBuffer message = ByteBuffer.wrap (tmp);
			len = message.order(ByteOrder.BIG_ENDIAN).getInt();
			buf = new byte[len];
			int getByte = 0;
			while ((getByte += socket.getInputStream ().read (buf, getByte, len - getByte)) != len);
			message = ByteBuffer.wrap (buf);
			message.get (type, 0, 1);
			if (type[0] == 7) {
				int index = message.order(ByteOrder.BIG_ENDIAN).getInt();
				int begin = message.order(ByteOrder.BIG_ENDIAN).getInt();
				len -= 9;
				byte [] tmp = new byte[len];
				message.get (tmp, begin , len);
				Map<Integer, String> tmpMap = blocks.get (fileName);
				if (tmpMap == null) {
					tmpMap = new HashMap<> ();
				}
				tmpMap.put (index, new String (tmp, StandardCharsets.UTF_8));
				blocks.put (fileName, tmpMap);
			}
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	public void recvMessageServer (ByteBuffer message,
								   Map<String, Map<Integer, String>> blocks,
								   SocketChannel socketChannel,
								   Map<Integer, Integer> downloadBlocks,
								   String nameFile) {
		byte [] type = new byte[1];
		int len = message.order(ByteOrder.BIG_ENDIAN).getInt();

		message.get (type, 0, 1);
		if (type[0] == 4) {
			int index = message.order(ByteOrder.BIG_ENDIAN).getInt();
			int numPort = message.order(ByteOrder.BIG_ENDIAN).getInt();
			downloadBlocks.put (numPort, index);
		} else if (type[0] == 6) {
			piece.index = message.order(ByteOrder.BIG_ENDIAN).getInt();
			piece.begin = message.order(ByteOrder.BIG_ENDIAN).getInt();
			int lenPiece = message.order(ByteOrder.BIG_ENDIAN).getInt();
			piece.piece = blocks.get (nameFile).get (piece.index).substring (piece.begin, piece.begin + lenPiece);
			try {
				int byteSend = 0;
				ByteBuffer mes = ByteBuffer.wrap (sendMessage7 ());
				while ((byteSend += socketChannel.write (mes)) < (9 + piece.piece.length ()));
			} catch (IOException e) {
				e.printStackTrace ();
			}
		}
	}
}
