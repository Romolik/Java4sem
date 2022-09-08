package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command deletes a vertex.
 */
public class Drop implements Commands {
	/**
	 * The command deletes a vertex.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Drop command is used");
		context.checkEmptyStack (context.getStack ());
		context.getStack ().pop ();
	}

}
