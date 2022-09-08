package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;

/**
 * The team that completes the process of interpreting the program.
 */
public class EndProgramm implements Commands {
	/**
	 * The team that completes the process of interpreting the program.
	 * @param context {@link ExecutionContext}.
	 */
	@Override
	public void useCommand (ExecutionContext context) {
		logger.trace("EndProgramm command is used");
		context.switchNoEndProgram ();
	}

}
