package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command puts a copy of the vertex on the stack.
 */
public class Dup implements Commands {
	/**
	 * The command puts a copy of the vertex on the stack.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Dup command is used");
		context.checkEmptyStack (context.getStack ());
		context.getStack ().push (context.getStack ().peek ());
	}

}
