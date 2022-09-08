package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller is responsible for selecting the level
 */
public class LevelWindowController implements Initializable {

	private static Stage stage;
	private final PainterLevelWindow painterLevelWindow = new PainterLevelWindow ();

	@FXML
	private ComboBox<String> levelsComboBox;

	@FXML
	private Button backButton;

	public void start (Stage primaryStage) {
		stage = primaryStage;
		painterLevelWindow.start (stage);
	}

	/**
	 * By clicking on the buttons, it continues the game or returns to the main
	 * menu
	 */
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		levelsComboBox.getItems ().addAll ("Level 1", "Level 2");
		levelsComboBox.setOnAction (event -> {
			String s = levelsComboBox.getSelectionModel ().getSelectedItem ();
			if (s.equals ("Level 1")) {
				try {
					GameController gameController = new GameController ();
					gameController.loadField (1);
					gameController.start (stage);
				} catch (Exception e) {
					e.printStackTrace ();
				}
			} else if (s.equals ("Level 2")) {
				try {
					GameController gameController = new GameController ();
					gameController.loadField (2);
					gameController.start (stage);
				} catch (Exception e) {
					e.printStackTrace ();
				}
			}
		});
		backButton.setOnAction (event -> {
			stage.close ();
			MainWindow mainWindow = new MainWindow ();
			mainWindow.start (new Stage ());
		});
	}

}
