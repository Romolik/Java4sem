package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Get;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GetTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		Get get = new Get ();
		befunge.getStack ().push ((char)0);
		befunge.getStack ().push ((char)0);
		befunge.getTable ()[0][0] = 'h';
		get.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), 'h');
	}

}