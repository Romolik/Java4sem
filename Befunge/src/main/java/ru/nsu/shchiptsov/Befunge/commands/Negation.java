package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The command performs a negation: the zero at the vertex is replaced by 1,
 * the non-zero value is replaced by 0.
 */
public class Negation implements Commands {
	/**
	 * The command performs a negation: the zero at the vertex is replaced by 1,
	 * the non-zero value is replaced by 0.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("Negation command is used");
		context.checkEmptyStack (context.getStack ());
		char a = context.getStack ().pop ();
		if (a == 0) {
			context.getStack ().push ((char) 1);
		} else {
			context.getStack ().push ((char) 0);
		}
	}

}
