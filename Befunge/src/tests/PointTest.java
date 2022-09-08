package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;

class PointTest {
	@Test
	public void useCommand () throws IOException {
		Befunge befunge = new Befunge ();
		OutputStream stream = new ByteArrayOutputStream ();
		befunge.setOutputStreamForTests (stream);
		befunge.setInputStreamForTests (null);
		befunge.interpretation ("./src/main/resources/factoryConfig.properties",
								"12..@");
		String string = stream.toString();
		StringReader reader = new StringReader(string);
		Assert.assertEquals (reader.read (),'2');
		Assert.assertEquals (reader.read (), '1');
	}

}