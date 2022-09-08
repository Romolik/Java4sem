package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command changes the direction of movement. The pointer moves left.
 */
public class MoveLeft implements Commands {
	/**
	 * The command changes the direction of movement. The pointer moves left.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("MoveLeft command is used");
		context.getPointer ().setMove (-1, 0);
	}

}
