package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Plus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PlusTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		befunge.getStack ().push ((char)1);
		befunge.getStack ().push ((char)2);
		Plus plus = new Plus ();
		plus.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (),(char)3);
	}

}