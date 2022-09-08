package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller is responsible for pausing the game
 */
public class PauseMenuController implements Initializable {
	
	private static PainterPauseMenu painterPauseMenu;
	private static GameController gameController;

	@FXML
	private Button resumeButton;

	@FXML
	private Button exitButton;

	public void start (GameController gC) {
		gameController = gC;
		Stage stage = new Stage ();
		stage.setOnCloseRequest (new EventHandler<WindowEvent> () {
			@Override
			public void handle (WindowEvent event) {
				event.consume();
			}
		});
		painterPauseMenu = new PainterPauseMenu ();
		painterPauseMenu.start (stage);
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
	resumeButton.setOnAction (event -> {
		gameController.setTimer (true);
		painterPauseMenu.closeWindow ();
	});

	exitButton.setOnAction (event -> {
		gameController.setTimer (false);
		painterPauseMenu.closeWindow ();
		gameController.getPainterGame ().closeWindow ();
		MainWindow mainWindow = new MainWindow ();
		mainWindow.start (new Stage ());
	});
	}

}
