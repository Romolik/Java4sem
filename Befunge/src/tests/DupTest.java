package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Dup;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DupTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		Dup dup = new Dup ();
		dup.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (),0);
		Assert.assertEquals ((int)befunge.getStack ().pop (),0);
		befunge.getStack ().push ('5');
		dup.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (),'5');
		Assert.assertEquals ((int)befunge.getStack ().pop (),'5');
	}

}