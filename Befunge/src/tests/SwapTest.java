package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Swap;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SwapTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		befunge.getStack ().push ('1');
		befunge.getStack ().push ('3');
		Swap swap = new Swap ();
		swap.useCommand (befunge);
		Assert.assertEquals ((int) befunge.getStack ().pop (), '1');
		Assert.assertEquals ((int) befunge.getStack ().pop (), '3');
	}

}