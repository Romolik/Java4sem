package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller is responsible for the end of the game, and saving the
 * results to a file
 */
public class GameOverController implements Initializable {

	private static PainterGameOver painterGameOver;
	private static GameController gameController;
	private static int numberLevel;

	@FXML
	private Label InfoLabel;

	@FXML
	private Label ScoreLabel;

	@FXML
	private Button restartButton;

	@FXML
	private Button enterButton;

	@FXML
	private Button exitButton;

	@FXML
	private TextField nameTextField;

	private static Label infoLabel;
	private static Label scoreLabel;

	public void start (String iL, int sL, GameController gc, int nL) {
		gameController = gc;
		numberLevel = nL;
		Stage stage = new Stage ();
		painterGameOver = new PainterGameOver ();
		painterGameOver.start (stage);
		infoLabel.setText (iL);
		scoreLabel.setText (String.valueOf (sL));
	}

	/**
	 * Looks at the file, and if the player scored more points than the
	 * people in the table, then replaces their result
	 * @param score The number of points that the player has scored
	 */
	private void parserRecordsTable (int score) {
		int countLine = 0;
		boolean newRecord = false;
		String oldRecord = null;
		StringBuffer inputBuffer = new StringBuffer();
		try (InputStream in =
					 //RecordsMenuController.class.getResourceAsStream (
						//	 "/records.txt");
					 new FileInputStream ("src/main/resourses/records.txt");
			BufferedReader reader = new BufferedReader (new InputStreamReader (in))) {
			String line;
			while ((line = reader.readLine ()) != null) {
				String []winner = line.split (" ");
				++countLine;
				if (countLine > 3 * (numberLevel - 1) &&
					countLine <= 3 * numberLevel) {
					if (!newRecord) {
						if (Integer.parseInt (winner[winner.length - 1]) < score) {
							newRecord = true;
							oldRecord = line;
							line = nameTextField.getText () + ' ' + score;
						}
					} else {
						String tmp = line;
						line = oldRecord;
						oldRecord = tmp;
					}
				}
				inputBuffer.append (line + '\n');
			}
		} catch (IOException e) {
			e.printStackTrace ();
		}
		try (FileOutputStream fileOut =
					 new FileOutputStream("src/main/resourses/records.txt")) {
			fileOut.write(inputBuffer.toString().getBytes());
			fileOut.flush ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	/**
	 * Responds to button clicks
	 */
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		infoLabel = InfoLabel;
		scoreLabel = ScoreLabel;
		exitButton.setOnAction (event -> {
			gameController.setTimer (false);
			painterGameOver.closeWindow ();
			gameController.getPainterGame ().closeWindow ();
			MainWindow mainWindow = new MainWindow ();
			mainWindow.start (new Stage ());
		});
		restartButton.setOnAction (event -> {
			try {
				painterGameOver.closeWindow ();
				gameController.getPainterGame ().closeWindow ();
				int numLevel = gameController.getNameLevel ();
				gameController = new GameController ();
				gameController.loadField (numLevel);
				gameController.start (new Stage ());
			} catch (Exception e) {
				e.printStackTrace ();
			}
		});
		enterButton.setOnAction (event -> {
			gameController.setTimer (false);
			painterGameOver.closeWindow ();
			gameController.getPainterGame ().closeWindow ();
			int score = Integer.parseInt (scoreLabel.getText ());
			parserRecordsTable (score);
			MainWindow mainWindow = new MainWindow ();
			mainWindow.start (new Stage ());
		});

	}

}
