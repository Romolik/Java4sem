package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command asks the user for a character and puts its ASCII code on the
 * stack.
 */
public class Tilda implements Commands {
	/**
	 * The command asks the user for a character and puts its ASCII code on the
	 * stack.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Tilda command is used");
		char symbol = (char) context.inputStream ();
		context.getStack ().push (symbol);

	}

}
