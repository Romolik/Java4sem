package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.HorizontalDash;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class HorizontalDashTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		HorizontalDash horizontalDash = new HorizontalDash ();
		horizontalDash.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (), 1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
		befunge.getStack ().push ('1');
		horizontalDash.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),-1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
		horizontalDash.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
	}

}