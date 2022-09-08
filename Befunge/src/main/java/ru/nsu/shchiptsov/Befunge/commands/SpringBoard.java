package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command skips the next cell in the table.
 */
public class SpringBoard implements Commands {
	/**
	 * The command skips the next cell in the table.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("SpringBoard command is used");
		context.getPointer ().move ();
	}

}
