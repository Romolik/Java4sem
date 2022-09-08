package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.DivMod;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DivModTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		DivMod divMod = new DivMod ();
		befunge.getStack ().push ((char)8);
		befunge.getStack ().push ((char)3);
		divMod.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), (char)2);
	}

}