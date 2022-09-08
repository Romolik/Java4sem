package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command performs vertex and sub-vertex multiplication.
 */
public class Multiplication implements Commands {
	/**
	 * The command performs vertex and sub-vertex multiplication.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Multiplication command is used");
		context.checkEmptyStack (context.getStack ());
		char a = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		char b = context.getStack ().pop ();
		char res = (char) (a * b);
		context.getStack ().push (res);
	}

}
