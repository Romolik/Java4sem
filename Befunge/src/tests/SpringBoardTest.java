package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.MoveDown;
import main.java.ru.nsu.shchiptsov.Befunge.commands.SpringBoard;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SpringBoardTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		SpringBoard springBoard = new SpringBoard ();
		Assert.assertEquals (befunge.getPointer ().getX (), 0);
		Assert.assertEquals (befunge.getPointer ().getY (), 0);
		springBoard.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getX (), 1);
		Assert.assertEquals (befunge.getPointer ().getY (), 0);
		MoveDown moveDown = new MoveDown ();
		moveDown.useCommand (befunge);
		springBoard.useCommand (befunge);
		Assert.assertEquals (befunge.getPointer ().getX (), 1);
		Assert.assertEquals (befunge.getPointer ().getY (), 1);
	}

}