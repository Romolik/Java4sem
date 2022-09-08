package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command from the stack extracts the coordinates of the cell;
 * the ASCII character code for these coordinates is placed on the stack.
 */
public class Get implements Commands {
	/**
	 * The command from the stack extracts the coordinates of the cell;
	 * the ASCII character code for these coordinates is placed on the stack.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Get command is used");
		context.checkEmptyStack (context.getStack ());
		int y = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		int x = context.getStack ().pop ();
		context.getStack ().push (context.getTable ()
									[y % ExecutionContext.COLUMNS]
									[x % ExecutionContext.ROWS]);
	}

}
