package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command changes the direction of movement. The pointer moves to the right
 * if there is 0 at the top of the stack, otherwise it moves to the left.
 */
public class HorizontalDash implements Commands {
	/**
	 * The command changes the direction of movement. The pointer moves to the
	 * right if there is 0 at the top of the stack, otherwise it moves to the
	 * left.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("HorizontalDash command is used");
		context.checkEmptyStack (context.getStack ());
		if (context.getStack ().pop () == 0) {
			context.getPointer ().setMove (1, 0);
		} else {
			context.getPointer ().setMove (-1, 0);
		}
	}

}
