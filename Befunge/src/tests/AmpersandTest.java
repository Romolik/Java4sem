package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AmpersandTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		String str = "823";
		InputStream stream =
			new ByteArrayInputStream (str.getBytes (StandardCharsets.UTF_8));
		befunge.setInputStreamForTests (stream);
		befunge.interpretation ("./src/main/resources/factoryConfig.properties",
								"&&&@");
		Assert.assertEquals ((int)befunge.getStack ().pop (),'3');
		Assert.assertEquals ((int)befunge.getStack ().pop (),'2');
		Assert.assertEquals ((int)befunge.getStack ().pop (),'8');

	}

}