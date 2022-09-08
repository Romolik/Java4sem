package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Minus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MinusTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		befunge.getStack ().push ((char)3);
		befunge.getStack ().push ((char)2);
		Minus minus = new Minus ();
		minus.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), (char)1);
		befunge.getStack ().push ((char)1);
		befunge.getStack ().push ((char)1);
		minus.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), (char)0);
		befunge.getStack ().push ((char)3);
		befunge.getStack ().push ((char)4);
		minus.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), (char)65535);
	}

}