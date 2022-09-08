package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command performs vertex and sub-vertex subtraction.
 */
public class Minus implements Commands {
	/**
	 * The command performs vertex and sub-vertex subtraction.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Minus command is used");
		context.checkEmptyStack (context.getStack ());
		char a = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		char b = context.getStack ().pop ();
		char res = (char) (b - a);
		context.getStack ().push (res);
	}

}
