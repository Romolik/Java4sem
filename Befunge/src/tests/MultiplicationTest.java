package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MultiplicationTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		befunge.setInputStreamForTests (null);
		befunge.interpretation ("./src/main/resources/factoryConfig.properties",
								"32*9*@");
		Assert.assertEquals ((int)befunge.getStack ().pop (),54);
	}

}