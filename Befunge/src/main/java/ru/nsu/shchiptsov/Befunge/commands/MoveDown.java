package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command changes the direction of movement. The pointer moves down.
 */
public class MoveDown implements Commands {
	/**
	 * The command changes the direction of movement. The pointer moves down.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("MoveDown command is used");
		context.getPointer ().setMove (0, 1);
	}

}
