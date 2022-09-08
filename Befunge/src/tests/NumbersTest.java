package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class NumbersTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		befunge.setInputStreamForTests (null);
		befunge.interpretation ("./src/main/resources/factoryConfig.properties",
								"328@");
		Assert.assertEquals ((int) befunge.getStack ().pop (), 8);
		Assert.assertEquals ((int) befunge.getStack ().pop (), 2);
		Assert.assertEquals ((int) befunge.getStack ().pop (), 3);
	}

}