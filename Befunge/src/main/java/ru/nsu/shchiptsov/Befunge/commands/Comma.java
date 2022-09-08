package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

import java.nio.charset.StandardCharsets;

/**
 * The command prints the top of the stack as an integer.
 */
public class Comma implements Commands {
	/**
	 * The command prints the top of the stack as an integer.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		context.checkEmptyStack (context.getStack ());
		String str = context.getStack ().pop ().toString ();
		context.outputStream (str.getBytes (StandardCharsets.UTF_8));
	}

}
