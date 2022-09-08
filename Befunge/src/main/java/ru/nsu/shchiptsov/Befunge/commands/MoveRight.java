package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command changes the direction of movement. The pointer moves right.
 */
public class MoveRight implements Commands {
	/**
	 * The command changes the direction of movement. The pointer moves right.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("MoveRight command is used");
		context.getPointer ().setMove (1, 0);
	}

}
