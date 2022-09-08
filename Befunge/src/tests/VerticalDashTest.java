package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.VerticalDash;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class VerticalDashTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		VerticalDash verticalDash = new VerticalDash ();
		verticalDash.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),0);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),1);
		befunge.getStack ().push ('1');
		verticalDash.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),0);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),-1);
		verticalDash.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),0);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),1);
	}

}