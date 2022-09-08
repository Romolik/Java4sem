package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class TildaTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		String str = "ab7";
		InputStream stream =
				new ByteArrayInputStream (str.getBytes (StandardCharsets.UTF_8));
		befunge.setInputStreamForTests (stream);
		befunge.interpretation ("./src/main/resources/factoryConfig.properties",
								"~~~@");
		Assert.assertEquals ((int)befunge.getStack ().pop (), '7');
		Assert.assertEquals ((int)befunge.getStack ().pop (),'b');
		Assert.assertEquals ((int)befunge.getStack ().pop (),'a');
	}

}