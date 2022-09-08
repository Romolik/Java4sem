package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Drop;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DropTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		Drop drop = new Drop ();
		befunge.getStack ().push ('2');
		Assert.assertEquals ((int)befunge.getStack ().peek (), '2');
		drop.useCommand (befunge);
		Assert.assertEquals (befunge.getStack ().empty (), true);
		befunge.getStack ().push ('3');
		befunge.getStack ().push ('8');
		drop.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().peek (), '3');
	}

}