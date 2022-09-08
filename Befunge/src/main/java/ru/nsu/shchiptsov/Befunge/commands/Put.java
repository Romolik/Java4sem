package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command from the stack extracts the coordinates of the cell and the
 * ASCII code of the character that is placed at these coordinates.
 */
public class Put implements Commands {
	/**
	 * The command from the stack extracts the coordinates of the cell and the
	 * ASCII code of the character that is placed at these coordinates.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Put command is used");
		context.checkEmptyStack (context.getStack ());
		int y = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		int x = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		context.getTable () [y % ExecutionContext.COLUMNS]
							[x % ExecutionContext.ROWS] = context.getStack ().pop ();
	}

}
