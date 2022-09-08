package main.java.ru.nsu.shchiptsov.Befunge;

public class FactoryCommandNotCreationExeption
	extends Throwable {
	public FactoryCommandNotCreationExeption (String message) {
		super(message);
	}

}
