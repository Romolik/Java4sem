package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

import java.nio.charset.StandardCharsets;

/**
 * The command prints the top of the stack as an integerю
 */
public class Point implements Commands {
	/**
	 * The command prints the top of the stack as an integer.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Point command is used");
		context.checkEmptyStack (context.getStack ());
		int a = (int) context.getStack ().pop ();
		String str = String.valueOf (a);
		context.outputStream (str.getBytes (StandardCharsets.UTF_8));

	}

}
