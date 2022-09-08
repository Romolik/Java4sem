package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command adds a vertex and a sub-vertex.
 */
public class Plus implements Commands {
	/**
	 * The command adds a vertex and a sub-vertex.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Plus command is used");
		context.checkEmptyStack (context.getStack ());
		char a = context.getStack ().pop ();
		context.checkEmptyStack (context.getStack ());
		char b = context.getStack ().pop ();
		char res = (char) (a + b);
		context.getStack ().push (res);
	}

}