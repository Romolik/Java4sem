package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command performs Integer division.
 */
public class Division implements Commands {
	/**
	 *The command performs Integer division.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Division command is used");
		context.checkEmptyStack (context.getStack ());
		char a = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		char b = context.getStack ().pop ();
		char res;
		try {
			res = (char) (b / a);
		} catch (ArithmeticException e) {
			e.printStackTrace ();
			return;
		}
		context.getStack ().push (res);
	}

}
