package main.java.ru.nsu.shchiptsov.Befunge;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * The Factory class registers the class with the first encountered commands and
 * fills the pathClass with matches between the command name and the instance of
 * the command class that returns.
 */
public class Factory {

	Logger logger = LogManager.getLogger (Factory.class);
	/**
	 * Stores the match between the command name and the instance of the command
	 * class.
	 */
	private HashMap<Character, Object> commands = new HashMap<> ();
	/**
	 * Stores the correspondence between the operation designation and the path
	 * to that operation.
	 */
	private HashMap<Character, String> pathClass = new HashMap<> ();

	/**
	 * Fills the pathClass with a match between the short name of the command
	 * and the path to that command.
	 *
	 * @param fileName
	 * 	The name of the file to read from.
	 *
	 * @throws FactoryConfigurationException
	 * 	An exception is thrown if it was not possible to read from the fileName
	 * 	file.
	 */
	public Factory (String fileName) throws FactoryConfigurationException {
		Properties properties = new Properties ();
		try (FileInputStream in = new FileInputStream (fileName)) {
			properties.load (in);
			properties.forEach ((key, val) -> {
				logger.trace (
					String.format ("Loading a match: command %s path: %s",
								   key, val));
				pathClass.put (key.toString ().charAt (0), val.toString ());
			});
		} catch (IOException e) {
			throw new FactoryConfigurationException (
				"IO Error occurred, can't" +
				" load factory properties");
		}
	}

	/**
	 * Searches for the command class, creates an instance of the class, and
	 * adds it to registered commands.
	 *
	 * @param nameCommand
	 * 	Path to the command class.
	 *
	 * @return The command instance.
	 *
	 * @throws FactoryCommandNotCreationExeption
	 * 	An exception is thrown if there was no file with the command class.
	 */
	private Object createCommand (Character nameCommand)
	throws FactoryCommandNotCreationExeption {
			logger.debug ("Creating a class instance");
		java.lang.Class<?> cl;
		Object o;
		try {
			cl = java.lang.Class.forName (pathClass.get (nameCommand));
			o = cl.getDeclaredConstructor ().newInstance ();
		} catch (Exception e) {
			throw new FactoryCommandNotCreationExeption (
				"Command " + nameCommand + " was not created, skipping");
		}
		commands.put (nameCommand, o);
		return o;
	}

	/**
	 * Creates an instance of the command class, if it has not yet been created,
	 * or returns a ready-made instance of the command class from HashMap
	 * commands.
	 *
	 * @param nameCommand
	 * 	Path to the command class.
	 *
	 * @return Instance of the command class.
	 *
	 * @throws FactoryCommandNotCreationExeption
	 *    {@link Factory#createCommand}.
	 */
	public Object getCommand (char nameCommand)
	throws FactoryCommandNotCreationExeption {
		logger.debug ("Returning a class instance");
		if (commands.containsKey (nameCommand)) {
			return commands.get (nameCommand);
		} else {
			return createCommand (nameCommand);
		}
	}

}
