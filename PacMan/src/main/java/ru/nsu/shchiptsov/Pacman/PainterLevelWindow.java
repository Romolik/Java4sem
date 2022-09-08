package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PainterLevelWindow extends Application {

	private static Stage stage;

	@Override
	public void start (Stage primaryStage) {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader ();
		loader.setLocation (getClass ().getResource ("/LevelWindow.fxml"));
		try {
			loader.load ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
		Parent root = loader.getRoot ();
		Scene scene = new Scene (root);
		stage.setScene (scene);
		stage.show ();
	}

}
