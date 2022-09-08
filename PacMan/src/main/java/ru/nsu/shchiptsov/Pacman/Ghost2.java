package main.java.ru.nsu.shchiptsov.Pacman;

import java.util.Random;

public class Ghost2 extends Ghosts {

	Ghost2 () {
		openDoor = true;
	}

	/**
	 * The method determines the Ghost 2 movement
	 *
	 * @param sizeCellY
	 * 		Height of the game cell in pixels
	 * @param sizeCellX
	 * 		Width of the game cell in pixels
	 * @param pacmanX
	 * 		Current PacMan coordinates by x
	 * @param pacmanY
	 * 		Current PacMan coordinates by y
	 * @param model
	 * 		The playing model that stores the current state of the cell
	 */
	@Override
	public void move (int sizeCellY, int sizeCellX, int pacmanX,
					  int pacmanY, Model model) {
		int x = getX () / sizeCellX;
		int y = getY () / sizeCellY;
		pacmanX /= sizeCellX;
		pacmanY /= sizeCellY;
		int dx = sizeCellX / countCellDiv;
		int dy = sizeCellY / countCellDiv;
		int countDirection;
		if (getCountRepeatMove () != 0) {
			setX ((getX () + getRepeatMoveX ()) %
				  (model.getSizeX () * sizeCellX));
			setY ((getY () + getRepeatMoveY ()) %
				  (model.getSizeY () * sizeCellY));
			if (addCountRepeatMove () == (countCellDiv - 1)) {
				resetCountRepeatMove ();
			}
		} else {
			countDirection = countingDirections (model, x, y);
			addCountRepeatMove ();
			if (model.getAvailableCells ()[y][x] == 't') {
				openDoor = false;
				setRepeatMoveX ((getX () - getPrevX ()) / countCellDiv);
				setRepeatMoveY ((getY () - getPrevY ()) / countCellDiv);
				setPrevСoordinates (getY (), getX ());
				setX (getX () + getRepeatMoveX ());
				setY (getY () + getRepeatMoveY ());
			} else if (openDoor) {
				Random random = new Random ();
				while (true) {
					int rand = random.nextInt (4);
					if (rand == 0) {
						if (isValidField (model.getAvailableCells ()[y]
							[(x - 1 + model.getSizeX ()) % model.getSizeX ()], openDoor) &&
							(y != (getPrevY () / sizeCellY) || (x - 1 + model.getSizeX ()) %
							model.getSizeX () != (getPrevX () / sizeCellX))) {
							setPrevСoordinates (getY (), getX ());
							setRepeatMoveX (-dx + sizeCellX * model.getSizeX ());
							setRepeatMoveY (0);
							setX ((getX () + getRepeatMoveX ()) % (model.getSizeX () * sizeCellX));
							break;
						}
					} else if (rand == 1) {
						if (isValidField (model.getAvailableCells ()[(y + 1) % model.getSizeY ()][x], openDoor) &&
							((y + 1) % model.getSizeY () != (getPrevY () / sizeCellY) || x != (getPrevX () / sizeCellX))) {
							setPrevСoordinates (getY (), getX ());
							setRepeatMoveY (dy);
							setRepeatMoveX (0);
							setY ((getY () + getRepeatMoveY ()) % (model.getSizeY () * sizeCellY));
							break;
						}
					} else if (rand == 2) {
						if (isValidField (model.getAvailableCells ()[(y - 1 + model.getSizeY ()) % model.getSizeY ()][x],
							openDoor) && ((y - 1 + model.getSizeY ()) % model.getSizeY () != (getPrevY () / sizeCellY) ||
							x != (getPrevX () / sizeCellX))) {
							setPrevСoordinates (getY (), getX ());
							setRepeatMoveY (-dy + sizeCellY * model.getSizeY ());
							setRepeatMoveX (0);
							setY ((getY () + getRepeatMoveY ()) % (model.getSizeY () * sizeCellY));
							break;
						}
					} else {
						if (isValidField (model.getAvailableCells ()[y][(x + 1) % model.getSizeX ()], openDoor) &&
							((x + 1) % model.getSizeX () != (getPrevX () / sizeCellX) || y != (getPrevY () / sizeCellY))) {
							setPrevСoordinates (getY (), getX ());
							setRepeatMoveX (dx);
							setRepeatMoveY (0);
							setX ((getX () + getRepeatMoveX ()) % (model.getSizeX () * sizeCellX));
							break;
						}
					}
				}
			} else {
				if (countDirection == 1) {
					int y1 = getPrevY ();
					int x1 = getPrevX ();
					setPrevСoordinates (getY (), getX ());
					setRepeatMoveX (getX () - getPrevX ());
					setRepeatMoveY (getY () - getPrevY ());
					setX (x1);
					setY (y1);
				} else if (countDirection > 2) {
					direction dir;
					if (!isFrightMode ()) {
						dir = findShortestPath (model, y, x, sizeCellX, sizeCellY, pacmanX, pacmanY);
					} else {
						dir = runAway (model, y, x, sizeCellX, sizeCellY, pacmanX, pacmanY);
					}
					chooseDir (dir, sizeCellX, sizeCellY, model);
				} else {
					if (isValidField (model.getAvailableCells ()[y][(x - 1 + model.getSizeX ()) % model.getSizeX ()],
						openDoor) && (y != (getPrevY () / sizeCellY) || (x - 1 + model.getSizeX ())
						% model.getSizeX () != (getPrevX () / sizeCellX))) {
						setPrevСoordinates (getY (), getX ());
						setRepeatMoveX (-dx + sizeCellX * model.getSizeX ());
						setRepeatMoveY (0);
						setX ((getX () + getRepeatMoveX ()) % (model.getSizeX () * sizeCellX));
					} else if (isValidField (model.getAvailableCells ()[(y + 1) % model.getSizeY ()][x], openDoor) &&
							   ((y + 1) % model.getSizeY () != (getPrevY () / sizeCellY) || x != (getPrevX () / sizeCellX))) {
						setPrevСoordinates (getY (), getX ());
						setRepeatMoveY (dy);
						setRepeatMoveX (0);
						setY ((getY () + getRepeatMoveY ()) % (model.getSizeY () * sizeCellY));
					} else if (isValidField (model.getAvailableCells ()[(y - 1 + model.getSizeY ()) % model.getSizeY ()][x],
								openDoor) && ((y - 1 + model.getSizeY ()) % model.getSizeY () != (getPrevY () / sizeCellY) ||
								x != (getPrevX () / sizeCellX))) {
						setPrevСoordinates (getY (), getX ());
						setRepeatMoveY (-dy + sizeCellY * model.getSizeY ());
						setRepeatMoveX (0);
						setY ((getY () + getRepeatMoveY ()) % (model.getSizeY () * sizeCellY));
					} else if (isValidField (model.getAvailableCells ()[y][(x + 1) % model.getSizeX ()], openDoor) &&
								((x + 1) % model.getSizeX () != (getPrevX () / sizeCellX) || y != (getPrevY () / sizeCellY))) {
						setPrevСoordinates (getY (), getX ());
						setRepeatMoveX (dx);
						setRepeatMoveY (0);
						setX ((getX () + getRepeatMoveX ()) % (model.getSizeX () * sizeCellX));
					}
				}
			}
		}
	}

}

