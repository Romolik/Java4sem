package  main.java.ru.nsu.shchiptsov.Befunge;

public class Main {
	public static void main (String[] args) {
		Befunge befunge = new Befunge ();
		befunge.interpretation ("./src/main/resources/factoryConfig.properties",
								"./src/main/resources/Matrix.txt");
	}
}
