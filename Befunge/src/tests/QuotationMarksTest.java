package tests;

import main.java.ru.nsu.shchiptsov.Befunge.Befunge;
import main.java.ru.nsu.shchiptsov.Befunge.commands.QuotationMarks;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class QuotationMarksTest {
	@Test
	public void useCommand () {
		Befunge befunge = new Befunge ();
		QuotationMarks quotationMarks = new QuotationMarks ();
		Assert.assertEquals (befunge.getOpenQuotationMarks (), false);
		quotationMarks.useCommand (befunge);
		Assert.assertEquals (befunge.getOpenQuotationMarks (),true);
	}

}