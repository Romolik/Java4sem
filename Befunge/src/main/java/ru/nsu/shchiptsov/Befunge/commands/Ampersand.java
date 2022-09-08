package main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command asks the user for a number and puts it on the stack.
 */
public class Ampersand implements Commands {
	/**
	 * The command asks the user for a number and puts it on the stack.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
			int number = context.inputStream();
			context.getStack ().push ((char) number);
	}

}
