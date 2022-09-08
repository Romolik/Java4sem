package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.MoveDown;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MoveDownTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		MoveDown moveDown = new MoveDown ();
		Assert.assertEquals (befunge.getPointer ().getMoveX (), 1);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),0);
		moveDown.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getMoveX (),0);
		Assert.assertEquals (befunge.getPointer ().getMoveY (),1);
	}

}