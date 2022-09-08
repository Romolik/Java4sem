package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command puts the numbers on the stack.
 */
public class Numbers implements Commands {
	/**
	 * The command puts the numbers on the stack.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Numbers command is used");
		context.getStack ().push ((char) (context.getTable ()
										[context.getPointer ().getY ()]
										[context.getPointer ().getX ()] - '0'));
	}

}
