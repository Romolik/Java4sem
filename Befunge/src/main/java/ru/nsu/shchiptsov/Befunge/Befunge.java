package main.java.ru.nsu.shchiptsov.Befunge;

import main.java.ru.nsu.shchiptsov.Befunge.commands.Commands;

import org.apache.log4j.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Roman Shchiptsov
 * @version 2.0 The Befunge program reads commands written in befunge-93 and the
 * command paths from files and interprets the received commands.
 */
public class Befunge extends ExecutionContext {

	Logger logger = LogManager.getLogger (Befunge.class);

	/**
	 * This method reads the source field from the file to the table for
	 * interpretation.
	 *
	 * @param nameTableFile
	 * 	- name of the file where we want to read from.
	 */
	private void readTable (String nameTableFile) {
		int countString = 0;

		if (!isInputStreamForTests) {
			try (BufferedReader reader = new BufferedReader (new FileReader (
				nameTableFile))) {

				String line;
				while ((line = reader.readLine ()) != null) {
					if (countString == COLUMNS) {
						logger.error ("Invalid count string");
						return;
					}
					if (line.length () > ROWS) {
						logger.error ("Invalid count symbols in string");
						return;
					}
					char[] arr = line.toCharArray ();
					for (int i = 0; i < line.length (); ++i) {
						getTable ()[countString][i] = arr[i];
					}
					++countString;
				}
			} catch (IOException e) {
				logger.error ("Error TableFile reading");
				e.printStackTrace ();
			}
		}
		else {
			/*
			For Tests
			 */
			InputStream stream = new ByteArrayInputStream (nameTableFile.getBytes (
				StandardCharsets.UTF_8));
			BufferedReader reader =
				new BufferedReader(new InputStreamReader(stream));
			String line;
			try {
				while ((line = reader.readLine ()) != null) {
					char[] arr = line.toCharArray ();
					for (int i = 0; i < line.length (); ++i) {
						getTable ()[countString][i] = arr[i];
					}
					++countString;
				}
			} catch (IOException e) {
				e.printStackTrace ();
			}
		}
	}

	/**
	 * The main method in the class that creates an instance of the factory and
	 * uses it to interpret the code from the file.
	 *
	 * @param nameFactoryFile
	 * 	- The name of the file from which the factory is initialized. It should
	 * 	store the correspondence between the operation designation and the path
	 * 	to it.
	 * @param nameTableFile
	 * 	- The name of the file from which we get the program in the befunge 93
	 * 	language, which we will interpret.
	 *
	 * @see Factory
	 */
	public void interpretation (String nameFactoryFile,
								String nameTableFile) {
		BasicConfigurator.configure ();
		Factory factory;
		try {
			logger.debug ("loading factoryConfig.properties");
			factory = new Factory (nameFactoryFile);
		} catch (FactoryConfigurationException e) {
			logger.error ("IO Error occurred, can't load factory properties");
			e.printStackTrace ();
			return;
		}

		logger.debug ("read table");
		readTable (nameTableFile);

		Commands command;
		while (getNoEndProgram ()) {
			try {
				if (Commands.pushStackOpenQuotationMarks (this,
					getTable ()[getPointer ().getY ()][getPointer ().getX ()]));
				else {
					command = (Commands) factory.getCommand (
					(getTable ()[getPointer ().getY ()][getPointer ().getX ()]));
					command.useCommand (this);
				}
			} catch (FactoryCommandNotCreationExeption e) {
				logger.error ("Error use command");
				e.printStackTrace ();
			}
			getPointer ().move ();
		}
	}

}
