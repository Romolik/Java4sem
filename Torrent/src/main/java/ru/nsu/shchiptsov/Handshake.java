package main.java.ru.nsu.shchiptsov;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;


public class Handshake {
	Handshake () {
		generatePeerID ();
	}
	private char symbol = 19;
	private String nameProtocol = "BitTorrent Protocol";
	private String reservedBytes = "00000000";
	private byte[] SHA1 = new byte[20];
	private String peerID = "";

	private void generatePeerID () {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String fullalphabet = alphabet + alphabet.toLowerCase() + "0123456789";
		Random random = new Random();

		for (int i = 0; i < 20; ++i) {
			char code = fullalphabet.charAt (random.nextInt (2 * alphabet.length () + 10));
			peerID += code;
		}

	}

	public void setSHA1(byte[] cur) {
		SHA1 = cur;
	}

	public byte[] getNameProtocol () {
		return (symbol + nameProtocol).getBytes (StandardCharsets.UTF_8);
	}

	public byte[] getReservedBytes () {
		return reservedBytes.getBytes (StandardCharsets.UTF_8);
	}

	public byte[] getSHA1 () {
		return SHA1;
	}

	public byte[] getPeerID () {
		return peerID.getBytes (StandardCharsets.UTF_8);
	}

	public byte[] getHandshake () {
		byte[] tmp = (symbol + nameProtocol + reservedBytes).getBytes (StandardCharsets.UTF_8);
		byte[] tmp2 = peerID.getBytes (StandardCharsets.UTF_8);
		return ByteBuffer.allocate (68).put (tmp).put (SHA1).put (tmp2).array ();
	}

}
