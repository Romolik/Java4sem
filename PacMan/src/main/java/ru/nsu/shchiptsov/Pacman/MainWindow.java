package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * This class controls the main menu of the game.
 */
public class MainWindow extends Application {

	private static Stage stage;

	private int countLevel = 2;

	public static void main (String[] args) {
		launch (args);
	}

	@FXML
	public Button newGameButton;

	@FXML
	private Button recordsButton;

	@FXML
	private Button exitButton;

	/**
	 * This method is responsible for responding to button clicks.
	 */
	@FXML
	void initialize () {
		newGameButton.setOnAction (event -> {
			LevelWindowController levelWindowController =
					new LevelWindowController ();
			levelWindowController.start (stage);
		});
		exitButton.setOnAction (event -> {
			System.exit (0);
		});
		recordsButton.setOnAction (exent -> {
			RecordsMenuController recordsMenuController =
					new RecordsMenuController ();
			recordsMenuController.start (stage, countLevel);
		});
	}

	/**
	 * Initializes the main menu of the game
	 * @param primaryStage This is the main stage that will change the scenes
	 */
	@Override
	public void start (Stage primaryStage) {
		primaryStage.setTitle ("PacMan");
		FXMLLoader loader = new FXMLLoader ();
		loader.setLocation (getClass ().getResource ("/MainWindow.fxml"));
		try {
			loader.load ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
		stage = primaryStage;
		stage.setOnCloseRequest (new EventHandler<WindowEvent> () {
			@Override
			public void handle (WindowEvent event) {
				event.consume ();
			}
		});

		Parent root = loader.getRoot ();
		primaryStage.setResizable (false);
		primaryStage.setFullScreenExitKeyCombination (KeyCombination.NO_MATCH);
		Scene scene = new Scene (root);
		primaryStage.setScene (scene);
		primaryStage.show ();
	}
}