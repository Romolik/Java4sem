package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.EndProgramm;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class EndProgrammTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		EndProgramm endProgramm = new EndProgramm ();
		Assert.assertEquals (befunge.getNoEndProgram (), true);
		endProgramm.useCommand (befunge);
		Assert.assertEquals (befunge.getNoEndProgram (), false);
	}

}