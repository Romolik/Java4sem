package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.MoveLeft;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MoveLeftTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		MoveLeft moveLeft = new MoveLeft ();
		Assert.assertEquals (befunge.getPointer ().getMoveX (), 1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
		moveLeft.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),-1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
	}

}