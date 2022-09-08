package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.Negation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class NegationTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		Negation negation = new Negation ();
		negation.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (),1);
		befunge.getStack ().push ('1');
		negation.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (),0);
		befunge.getStack ().push ('3');
		negation.useCommand (befunge);
		Assert.assertEquals ((int)befunge.getStack ().pop (),0);
	}

}