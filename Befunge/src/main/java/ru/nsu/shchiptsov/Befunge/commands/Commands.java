package  main.java.ru.nsu.shchiptsov.Befunge.commands;

import main.java.ru.nsu.shchiptsov.Befunge.ExecutionContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Common interface for all Befunge-93 language commands.
 */
public interface Commands {
	/**
	 * Using the command.
	 * @param context {@link ExecutionContext}.
	 */
	void useCommand (ExecutionContext context);

	static boolean pushStackOpenQuotationMarks (ExecutionContext context,
												Character commandSymbol) {
		if (context.getOpenQuotationMarks () && commandSymbol != '"') {
			context.getStack ().push (commandSymbol);
			return true;
		}
		return false;
	}
	Logger logger = LogManager.getLogger (Commands.class);

}
