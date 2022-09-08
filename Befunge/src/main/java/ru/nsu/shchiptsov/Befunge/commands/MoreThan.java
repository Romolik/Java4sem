package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command performs a "more than" comparison: if the sub-vertex is larger
 * than the vertex, 1 is placed on the stack, otherwise 0.
 */
public class MoreThan implements Commands {
	/**
	 * The command performs a "more than" comparison: if the sub-vertex is
	 * larger than the vertex, 1 is placed on the stack, otherwise 0.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("MoreThan command is used");
		context.checkEmptyStack (context.getStack ());
		char a = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		char b = context.getStack ().pop ();
		context.getStack ().push (b);
		context.getStack ().push (a);
		if (b > a) {
			context.getStack ().push ((char) 1);
		} else {
			context.getStack ().push ((char) 0);
		}
	}

}
