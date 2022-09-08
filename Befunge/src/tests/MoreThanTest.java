package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.MoreThan;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MoreThanTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		MoreThan moreThan = new MoreThan ();
		befunge.getStack ().push ('2');
		befunge.getStack ().push ('4');
		moreThan.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), 0);
		befunge.getStack ().push ('a');
		befunge.getStack ().push ('4');
		moreThan.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), 1);
		befunge.getStack ().push ('9');
		befunge.getStack ().push ('4');
		moreThan.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (), 1);
	}

}