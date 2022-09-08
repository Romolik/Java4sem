package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command changes the direction of movement. The pointer moves up.
 */
public class MoveUp implements Commands {
	/**
	 * The command changes the direction of movement. The pointer moves up.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("MoveUp command is used");
		context.getPointer ().setMove (0, -1);
	}

}
