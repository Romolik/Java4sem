package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command changes the direction of movement. The pointer moves to the down
 * if there is 0 at the top of the stack, otherwise it moves to the up.
 */
public class VerticalDash implements Commands {
	/**
	 * The command changes the direction of movement. The pointer moves to the
	 * down if there is 0 at the top of the stack, otherwise it moves to the up.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("VerticalDash command is used");
		context.checkEmptyStack (context.getStack ());
		if (context.getStack ().pop () == 0) {
			context.getPointer ().setMove (0, 1);
		} else {
			context.getPointer ().setMove (0, -1);
		}
	}

}
