package main.java.ru.nsu.shchiptsov.Pacman;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PainterGame extends Application {
	private Stage stage;
	private Pane root;
	private final int sizeX = 28;
	private final int sizeY = 33;
	private int countHp = 3;
	private final int sizeCellsX = 20;
	private final int sizeCellsY = 20;
	ImageView iv1 = new ImageView();

	public int getSizeCellsX () {
		return sizeCellsX;
	}
	public int getSizeCellsY () {
		return sizeCellsY;
	}

	private Canvas canvas = new Canvas (sizeCellsX * sizeX, sizeCellsY * sizeY);
	private GraphicsContext context = canvas.getGraphicsContext2D ();
	private Map<String, Image> nameImage = new HashMap<> ();

	PainterGame () {
		Properties properties = new Properties ();
		try (InputStream in = PainterGame.class.getResourceAsStream ("/nameImage.properties")) {
			properties.load (in);
			properties.forEach ((key, val) -> {
				nameImage.put (key.toString (),  new Image(getClass().getResource (
						val.toString ()).toExternalForm()));
			});
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	public void reductionHp (Pacman pacman, int sizeY) {
		countHp = pacman.getCountHP ();
		context.clearRect (pacman.getX () - 0.5 * sizeCellsX,
						   pacman.getY () - 0.5 * sizeCellsY, sizeCellsX, sizeCellsY);
		context.clearRect (2 * (countHp - 1) * (sizeCellsX) + sizeCellsX,
						   sizeY * sizeCellsY + 0.25 * sizeCellsY,
						   sizeCellsX * 1.5, sizeCellsY * 1.5);

	}

	public void paintInitialField (Model model) {
		context.setStroke (Color.web ("rgb(255,230,129)"));
		context.setLineWidth (5);
		for (int i = 0; i < model.getSizeY (); ++i) {
			for (int j = 0; j < model.getSizeX (); ++j) {
				char codeCell = model.getAvailableCells ()[i][j];
				if (codeCell == 'w') {
					context.setFill (Color.web ("rgb(83,81,80)"));
					context.fillRect (j * sizeCellsX, i * sizeCellsY,
										   sizeCellsX, sizeCellsY);
				} else if (codeCell == 't') {
					context.setFill (Color.web ("rgb(186, 12, 47)"));
					context.fillRect (j * sizeCellsX, i * sizeCellsY,
										   sizeCellsX, sizeCellsY);
				} else if (codeCell == 'd') {
					context.drawImage (nameImage.get ("Dot"), j * sizeCellsX,
									   i * sizeCellsY, sizeCellsX, sizeCellsY);
				} else if (codeCell == 'e') {
					context.drawImage (nameImage.get ("Energizer"), j * sizeCellsX,
									   i * sizeCellsY, sizeCellsX, sizeCellsY);
				} else if (codeCell == 'p') {
					iv1.setImage (nameImage.get ("PacmanUp"));
					iv1.setX (j * sizeCellsX);
					iv1.setY (i * sizeCellsY);
				} else if (codeCell == '1') {
					context.drawImage (nameImage.get ("Ghost1"), j * sizeCellsX,
									   i * sizeCellsY, sizeCellsX, sizeCellsY);
				} else if (codeCell == '2') {
					context.drawImage (nameImage.get ("Ghost2"), j * sizeCellsX,
									   i * sizeCellsY, sizeCellsX, sizeCellsY);
				} else if (codeCell == '3') {
					context.drawImage (nameImage.get ("Ghost3"), j * sizeCellsX,
									   i * sizeCellsY, sizeCellsX, sizeCellsY);
				} else if (codeCell == '4') {
					context.drawImage (nameImage.get ("Ghost4"), j * sizeCellsX,
									   i * sizeCellsY, sizeCellsX, sizeCellsY);
				}
			}
		}

		for (int i = 0; i < countHp - 1; ++i) {
			context.drawImage (nameImage.get ("PacmanHP"), 2 * i * (sizeCellsX) + sizeCellsX,
							   model.getSizeY () * sizeCellsY + 0.25 * sizeCellsY,
							   sizeCellsX * 1.5, sizeCellsY * 1.5);
		}
		context.strokeLine (0, sizeCellsY * model.getSizeY (),
							sizeCellsX * model.getSizeX (), sizeCellsY * model.getSizeY ());
	}

	public void paintPacman (Pacman pacman) {
		context.clearRect (pacman.getX () - 0.5 * sizeCellsX,
						   pacman.getY () - 0.5 * sizeCellsY, sizeCellsX, sizeCellsY);
		context.clearRect ((pacman.getX () - 0.5 * sizeCellsX + pacman.getMoveX ()),
						   (pacman.getY () - 0.5 * sizeCellsY + pacman.getMoveY ()),
						   sizeCellsX, sizeCellsY);
		String directionPacman;
		if (pacman.getMoveX () > 0) {
			directionPacman = "PacmanRight";
		} else if (pacman.getMoveX () < 0) {
			directionPacman = "PacmanLeft";
		} else  if (pacman.getMoveY () > 0) {
			directionPacman = "PacmanDown";
		} else {
			directionPacman = "PacmanUp";
		}
		iv1.setImage (nameImage.get (directionPacman));
		iv1.setX ((pacman.getX () - 0.5 * sizeCellsX +
				   pacman.getMoveX ()));
		iv1.setY ((pacman.getY () - 0.5 * sizeCellsY +
				   pacman.getMoveY ()));
	}

	private void recoverCell (Model model, int y, int x) {
		if (model.getAvailableCells ()[y / sizeCellsY][x / sizeCellsX] == 'd') {
			context.drawImage (nameImage.get ("Dot"), x, y, sizeCellsX, sizeCellsY);
		} else if (model.getAvailableCells ()[y / sizeCellsY][x / sizeCellsX] == 'e') {
			context.drawImage (nameImage.get ("Energizer"), x, y, sizeCellsX, sizeCellsY);
		} else if (model.getAvailableCells ()[y / sizeCellsY][x / sizeCellsX] == 't') {
			context.setFill (Color.web ("rgb(186, 12, 47)"));
			context.fillRect (x, y, sizeCellsX, sizeCellsY);
		}
	}

	public void clearGhosts (List<Ghosts> ghosts, Model model) {
		for (Ghosts ghost : ghosts) {
			int x = ghost.getX ();
			int y = ghost.getY ();
			context.clearRect (x - sizeCellsX / 2, y - sizeCellsY / 2,
							   sizeCellsX, sizeCellsY);
			x = (x / sizeCellsX) * sizeCellsX;
			y = (y / sizeCellsY) * sizeCellsY;
			int oldX = (ghost.getPrevX () / sizeCellsX) * sizeCellsX;
			int oldY = (ghost.getPrevY () / sizeCellsY) * sizeCellsY;
			recoverCell (model, y, x);
			recoverCell (model, oldY, oldX);
		}
	}

	public void paintGhosts (List<Ghosts> ghosts) {
		for (int i = 0; i < ghosts.size (); ++i) {
			if (ghosts.get (i).isFrightMode ()) {
				context.drawImage (nameImage.get ("GhostFrightened"),
								   (ghosts.get (i).getX () - 0.5 * sizeCellsX),
								   (ghosts.get (i).getY () - 0.5 * sizeCellsY),
								   sizeCellsX, sizeCellsY);
			} else {
				context.drawImage (nameImage.get ("Ghost" + (i + 1)),
								   (ghosts.get (i).getX () - 0.5 * sizeCellsX),
								   (ghosts.get (i).getY () - 0.5 * sizeCellsY),
								   sizeCellsX, sizeCellsY);
			}
		}
	}

	@Override
	public void start (Stage st) {
		stage = st;
		FXMLLoader loader = new FXMLLoader ();
		loader.setLocation (getClass ().getResource ("/GameWindow.fxml"));
		try {
			loader.load ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
		root = loader.getRoot ();
		Scene scene = new Scene (root);
		stage.setScene (scene);
		stage.show ();
		iv1.setFitWidth (20);
		iv1.setFitHeight (20);
		root.getChildren ().add (iv1);
		root.getChildren ().add (canvas);
	}

	public void closeWindow () {
		stage.close ();
	}

}