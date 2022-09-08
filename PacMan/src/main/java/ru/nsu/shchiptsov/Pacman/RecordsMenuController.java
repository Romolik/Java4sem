package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This controller is responsible for displaying the high score table
 */
public class RecordsMenuController implements Initializable {
	private static Stage stage;
	private final static PainterRecordsMenu painterRecordsMenu =
			new PainterRecordsMenu ();

	@FXML
	private ListView<String> textPlace;

	@FXML
	private Button backButton;

	private static ListView<String> tP;

	/**
	 * Reads from a file and displays the top three players in each level
	 * @param primaryStage This is the main stage that will change the scenes
	 * @param countLevel The number of possible levels in the game
	 */
	public void start (Stage primaryStage, int countLevel) {
		stage = primaryStage;
		painterRecordsMenu.start (stage);
		List<String> str = new ArrayList<> ();
		try (InputStream in =
				//	 RecordsMenuController.class.getResourceAsStream (
				//		 "/records.txt");
					 new FileInputStream ("src/main/resourses/records.txt");
			 BufferedReader reader = new BufferedReader (
					 new InputStreamReader (in))) {
			for (int i = 0; i < countLevel; ++i) {
				int countString = 0;
				str.add ("Level " + (i + 1) + ":");
				String line;
				while ((line = reader.readLine ()) != null) {
					str.add (line);
					++countString;
					if (countString % 3 == 0) {
						break;
					}
				}
			}
			tP.getItems().addAll (str);
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		tP = textPlace;
		backButton.setOnAction (event -> {
			stage.close ();
			MainWindow mainWindow = new MainWindow ();
			mainWindow.start (new Stage ());
		});
	}

}
