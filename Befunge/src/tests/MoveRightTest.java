package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.MoveRight;
import main.java.ru.nsu.shchiptsov.Befunge.commands.MoveUp;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MoveRightTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		MoveUp moveUp = new MoveUp ();
		MoveRight moveRight = new MoveRight ();
		Assert.assertEquals (befunge.getPointer ().getMoveX (), 1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
		moveUp.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),0);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),-1);
		moveRight.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (), 1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
	}

}