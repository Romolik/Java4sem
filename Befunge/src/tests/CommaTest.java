package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

class CommaTest {
	@Test
	public void useCommand () throws IOException {
		Befunge befunge = new Befunge ();
		OutputStream stream = new ByteArrayOutputStream ();
		befunge.setOutputStreamForTests (stream);
		befunge.setInputStreamForTests (null);
		befunge.interpretation ("./src/main/resources/factoryConfig.properties",
								"\"AB\",,@");
		String string = stream.toString();
		StringReader reader = new StringReader(string);
		Assert.assertEquals (reader.read (), 'B');
		Assert.assertEquals (reader.read (), 'A');
	}

}