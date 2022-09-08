package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The team exchanges places for the top and under the top.
 */
public class Swap implements Commands {
	/**
	 * The team exchanges places for the top and under the top.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Swap command is used");
		context.checkEmptyStack (context.getStack ());
		char a = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		char b = context.getStack ().pop ();
		context.getStack ().push (a);
		context.getStack ().push (b);
	}

}
