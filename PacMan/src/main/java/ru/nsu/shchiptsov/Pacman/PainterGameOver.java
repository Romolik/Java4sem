package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PainterGameOver extends Application {

	private static Stage stage;

	@Override
	public void start (Stage primaryStage) {
		primaryStage.setTitle ("Game over");
		FXMLLoader loader = new FXMLLoader ();
		loader.setLocation (getClass ().getResource ("/GameOver.fxml"));
		try {
			loader.load ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
		stage  = primaryStage;
		Parent root = loader.getRoot ();
		primaryStage.setResizable(false);
		primaryStage.setFullScreenExitKeyCombination (KeyCombination.NO_MATCH);
		Scene scene = new Scene (root);
		primaryStage.setScene (scene);
		stage.initModality (Modality.APPLICATION_MODAL);
		primaryStage.show ();
	}

	public void closeWindow () {
		stage.close ();
	}

}
