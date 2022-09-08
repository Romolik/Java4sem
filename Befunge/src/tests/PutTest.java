package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Put;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PutTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		Put put = new Put ();
		befunge.getStack ().push ('h');
		befunge.getStack ().push ((char)0);
		befunge.getStack ().push ((char)0);
		put.useCommand (befunge);
		Assert.assertEquals (befunge.getTable ()[0][0], 'h');

	}

}