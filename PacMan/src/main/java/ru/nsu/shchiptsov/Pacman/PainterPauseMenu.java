package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class PainterPauseMenu extends Application {
	public static Stage stage;

	@Override
	public void start (Stage primaryStage) {
		primaryStage.setTitle ("Pause menu");
		FXMLLoader loader = new FXMLLoader ();
		loader.setLocation (getClass ().getResource ("/PauseMenu.fxml"));
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
		stage.setOnCloseRequest (new EventHandler<WindowEvent> () {
			@Override
			public void handle (WindowEvent event) {
				event.consume();
			}
		});
		primaryStage.setScene (scene);
		stage.initModality (Modality.APPLICATION_MODAL);
		primaryStage.show ();
	}

	public void closeWindow () {
		stage.close ();
	}

}
