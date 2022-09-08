package main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command enables / disables the mode of writing commands to the stack.
 */
public class QuotationMarks implements Commands{
	/**
	 * This command enables / disables the mode of writing commands to the
	 * stack.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace ("QuotationMarks command is used");
		context.switchOpenQuotationMarks ();
	}
}
