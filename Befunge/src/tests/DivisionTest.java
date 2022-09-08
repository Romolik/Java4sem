package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Division;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DivisionTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		Division division = new Division ();
		befunge.getStack ().push ((char)8);
		befunge.getStack ().push ((char)3);
		division.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), (char)2);
	}

}