package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller that is responsible for the game itself
 */
public class GameController implements Initializable {
	private static Stage stage;
	private Model model;
	private int numLevel;
	private final PainterGame painterGame = new PainterGame ();
	private final int dx = painterGame.getSizeCellsX () / Entity.countCellDiv;
	private final int dy = painterGame.getSizeCellsY () / Entity.countCellDiv;

	@FXML
	private Label sL;

	private static Label scoreLabel;

	private boolean checkCrossroad (int x, int y) {
		return (model.getPacman ().isValidField (model.getAvailableCells ()[y][x], false) &&
			(model.getPacman ().getX () - painterGame.getSizeCellsX () / 2) %  painterGame.getSizeCellsX () == 0 &&
			(model.getPacman ().getY () - painterGame.getSizeCellsY () / 2) % painterGame.getSizeCellsY () == 0);
	}

	/**
	 * Responds to button clicks. Changes the direction of the pacman
	 * movement or calls the pause menu
	 */
	private void onKeyPressed () {
		stage.getScene ().setOnKeyPressed (keyEvent -> {
			if (keyEvent.getCode () == KeyCode.RIGHT) {
					int x = ((model.getPacman ().getX () + painterGame.getSizeCellsX ()) %
							 (painterGame.getSizeCellsX () * model.getSizeX ())) / painterGame.getSizeCellsX ();
					int y = model.getPacman ().getY () / painterGame.getSizeCellsY ();
					if (checkCrossroad (x, y)) {
						model.getPacman ().setMoveX (dx);
						model.getPacman ().setMoveY (0);
						model.getPacman ().setWaitTurn (Entity.direction.None);
					} else {
					model.getPacman ().setWaitTurn (Entity.direction.Right);
					}
			} else if (keyEvent.getCode () == KeyCode.LEFT) {
				int x = ((model.getPacman ().getX () - painterGame.getSizeCellsX () +
						 model.getSizeX () * painterGame.getSizeCellsX ()) %
						 (painterGame.getSizeCellsX () * model.getSizeX ())) / painterGame.getSizeCellsX ();
				int y = model.getPacman ().getY () / painterGame.getSizeCellsY ();
				if (checkCrossroad (x, y)) {
					model.getPacman ().setMoveX (-dx);
					model.getPacman ().setMoveY (0);
					model.getPacman ().setWaitTurn (Entity.direction.None);
				} else {
					model.getPacman ().setWaitTurn (Entity.direction.Left);
				}
			} else if (keyEvent.getCode () == KeyCode.UP) {
				int x = model.getPacman ().getX () / painterGame.getSizeCellsX ();
				int y = ((model.getPacman ().getY () - painterGame.getSizeCellsY () +
						 model.getSizeY () * painterGame.getSizeCellsY ()) %
						 (painterGame.getSizeCellsY () * model.getSizeY ())) / painterGame.getSizeCellsY ();
				if (checkCrossroad (x, y)) {
					model.getPacman ().setMoveX (0);
					model.getPacman ().setMoveY (-dy);
					model.getPacman ().setWaitTurn (Entity.direction.None);
				} else {
					model.getPacman ().setWaitTurn (Entity.direction.Up);
				}
			} else if (keyEvent.getCode () == KeyCode.DOWN) {
				int x = model.getPacman ().getX () / painterGame.getSizeCellsX ();
				int y = ((model.getPacman ().getY () + painterGame.getSizeCellsY ()) %
						 (painterGame.getSizeCellsY () * model.getSizeY ())) / painterGame.getSizeCellsY ();
				if (checkCrossroad (x, y)) {
					model.getPacman ().setMoveX (0);
					model.getPacman ().setMoveY (dy);
					model.getPacman ().setWaitTurn (Entity.direction.None);
				} else {
					model.getPacman ().setWaitTurn (Entity.direction.Down);
				}
			} else if (keyEvent.getCode () == KeyCode.ESCAPE) {
				timer.stop ();
				PauseMenuController pauseMenuController =
						new PauseMenuController ();
				pauseMenuController.start (this);
			}
		});
	}

	/**
	 * A timer that triggers the nextFrame function, which makes a move and
	 * redraws the playing model
	 */
	private final AnimationTimer timer = new AnimationTimer () {

		@Override
		public void handle (long l) {
			onKeyPressed ();
			scoreLabel.setText (String.valueOf (model.getScore ()));
			nextFrame ();
		}

	};

	public PainterGame getPainterGame () {
		return painterGame;
	}

	public void setTimer (boolean runTimer) {
		if (runTimer) {
			timer.start ();
		} else {
			timer.stop ();
		}
	}

	public int getNameLevel () {
		return numLevel;
	}

	/**
	 * Loads the game level
	 * @param numLevel1 The number of the level to load
	 */
	public void loadField (int numLevel1) {
		model = new Model (painterGame.getSizeCellsX (),
						   painterGame.getSizeCellsY ());
		model.loadLevel ("/Level" + numLevel1 + ".txt");
		model.updateField ();
		numLevel = numLevel1;
	}

	private boolean processOutVal (Model.returnCodes ret) {
		if (ret == Model.returnCodes.FullDied) {
			timer.stop ();
			GameOverController gameOverController = new GameOverController ();
			gameOverController.start ("You died", model.getScore (), this, numLevel);
			return true;
		} else if (ret == Model.returnCodes.Died) {
			painterGame.clearGhosts (model.getGhosts (), model);
			painterGame.reductionHp (model.getPacman (), model.getSizeY ());
			model.updateField ();
			painterGame.paintInitialField (model);
			painterGame.start (stage);
			return true;
		} else if (ret == Model.returnCodes.Win) {
			timer.stop ();
			GameOverController gameOverController = new GameOverController ();
			gameOverController.start ("You win", model.getScore (), this, numLevel);
			return true;
		}
		return false;
	}

	/**
	 * Redraws the playing model and makes a move for the creatures
	 */
	private void nextFrame () {
		painterGame.clearGhosts (model.getGhosts (), model);
		Model.returnCodes ret;
		ret = model.moveGhosts ();
		if (processOutVal (ret)) {
			return;
		}
		Pacman pacman = model.getPacman ();
		int sizeCellsX = painterGame.getSizeCellsX ();
		int sizeCellsY = painterGame.getSizeCellsY ();
		int sizeY = model.getSizeY ();
		int sizeX = model.getSizeX ();
		if (pacman.getWaitTurn () == Entity.direction.Right) {
			int x = ((pacman.getX () + sizeCellsX) % (sizeCellsX * sizeX)) / sizeCellsX;
			int y = pacman.getY () / sizeCellsY;
			if (checkCrossroad (x, y)) {
				pacman.setMoveX (dx);
				pacman.setMoveY (0);
				pacman.setWaitTurn (Entity.direction.None);
			}
		} else if (pacman.getWaitTurn () == Entity.direction.Left) {
			int x = ((pacman.getX () - sizeCellsX + sizeX * sizeCellsX) %
					 (sizeCellsX * sizeX)) / sizeCellsX;
			int y = pacman.getY () / sizeCellsY;
			if (checkCrossroad (x, y)) {
				pacman.setMoveX (-dx);
				pacman.setMoveY (0);
				pacman.setWaitTurn (Entity.direction.None);
			}
		} else if (pacman.getWaitTurn () == Entity.direction.Up) {
			int x = pacman.getX () / sizeCellsX;
			int y = ((pacman.getY () - sizeCellsY + sizeY * sizeCellsY) %
					 (sizeCellsY * sizeY)) / sizeCellsY;
			if (checkCrossroad (x, y)) {
				pacman.setMoveX (0);
				pacman.setMoveY (-dy);
				pacman.setWaitTurn (Entity.direction.None);
			}
		} else if (pacman.getWaitTurn () == Entity.direction.Down) {
			int x = pacman.getX () / sizeCellsX;
			int y = ((pacman.getY () + sizeCellsY) % (sizeCellsY * sizeY)) /
					sizeCellsY;
			if (checkCrossroad (x, y)) {
				pacman.setMoveX (0);
				pacman.setMoveY (dy);
				pacman.setWaitTurn (Entity.direction.None);
			}
		}
		if (model.getPacman ().checkMove (model, painterGame.getSizeCellsY (),
										  painterGame.getSizeCellsX ())) {
			painterGame.paintPacman (model.getPacman ());
		}
		ret = model.movePacman ();
		if (!processOutVal (ret)) {
			painterGame.paintGhosts (model.getGhosts ());
		}
	}

	/**
	 * Initializes and starts the game level
	 * @param primaryStage This is the main stage that will change the scenes
	 */
	public void start (Stage primaryStage) {
		stage = primaryStage;
		painterGame.start (stage);
		painterGame.paintInitialField (model);
		timer.start ();
	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		scoreLabel = sL;
	}

}