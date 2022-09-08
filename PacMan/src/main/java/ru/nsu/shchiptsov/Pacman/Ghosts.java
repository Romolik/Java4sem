package main.java.ru.nsu.shchiptsov.Pacman;

public abstract class Ghosts extends Entity{
	private int prevX;
	private int prevY;
	private int beginCoordinatesX;
	private int beginCoordinatesY;
	private boolean frightMode = false;
	private int repeatMoveX;
	private int repeatMoveY;
	private int countRepeatMove = 0;

	public int getCountRepeatMove () {
		return countRepeatMove;
	}

	public int addCountRepeatMove () {
		return countRepeatMove++;
	}

	public void setRepeatMoveX (int moveX) {
		repeatMoveX = moveX;
	}

	public void setRepeatMoveY (int moveY) {
		repeatMoveY = moveY;
	}

	public int getRepeatMoveX () {
		return repeatMoveX;
	}

	public int getRepeatMoveY () {
		return repeatMoveY;
	}

	public void setPrevСoordinates (int y, int x) {
		prevX = x;
		prevY = y;
	}

	public int getPrevX () {
		return prevX;
	}

	public int getPrevY () {
		return prevY;
	}

	public void resetCountRepeatMove () {
		countRepeatMove = 0;
	}

	public boolean isFrightMode() {
		return frightMode;
	}

	public void onFrightMode () {
		frightMode = true;
	}

	public void offFrightMode () {
		frightMode = false;
	}

	public int getBeginCoordinatesX () {
		return beginCoordinatesX;
	}
	public int getBeginCoordinatesY () {
		return beginCoordinatesY;
	}

	public void setBeginCoordinates (int x, int y) {
		beginCoordinatesX = x;
		beginCoordinatesY = y;
	}

	protected direction findShortestPath (Model model, int y, int x, int sizeCellX,
										  int sizeCellY, int pacmanX, int pacmanY) {
		direction dir = null;
		int shortDistX = -1;
		int shortDistY = -1;
		int tmpX = (x - 1 + model.getSizeX ()) % model.getSizeX ();
		int tmpY;
		if (isValidField (model.getAvailableCells ()[y][tmpX], openDoor) &&
			(y != (prevY / sizeCellY) || tmpX != (prevX / sizeCellX))) {
			dir = direction.Left;
			shortDistX = tmpX;
			shortDistY = y;
		}
		tmpY = (y + 1) % model.getSizeY ();
		if (isValidField (model.getAvailableCells ()[tmpY][x], openDoor) &&
			(tmpY != (prevY / sizeCellY) || x != (prevX / sizeCellX))) {
			if ((shortDistX == -1 && shortDistY == -1) || (Math.abs (shortDistX - pacmanX) +
				Math.abs (shortDistY - pacmanY)) > (Math.abs (tmpY - pacmanY) + Math.abs (x - pacmanX))) {
				dir = direction.Down;
				shortDistX = x;
				shortDistY = tmpY;
			}
		}

		tmpY = (y - 1 + model.getSizeY ()) % model.getSizeY ();
		if (isValidField (model.getAvailableCells ()[tmpY][x], openDoor) &&
			(tmpY != (prevY / sizeCellY) || x != (prevX / sizeCellX))) {
			if ((shortDistX == -1 && shortDistY == -1) || (Math.abs (shortDistX - pacmanX) +
				Math.abs (shortDistY - pacmanY)) > (Math.abs (tmpY - pacmanY) + Math.abs (x - pacmanX))) {
				dir = direction.Up;
				shortDistX = x;
				shortDistY = tmpY;
			}
		}

		tmpX = (x + 1) % model.getSizeX ();
		if (isValidField (model.getAvailableCells ()[y][tmpX], openDoor)
			&& (tmpX != (prevX / sizeCellX) || y != (prevY / sizeCellY))) {
			if ((shortDistX == -1 && shortDistY == -1) || (Math.abs (shortDistX - pacmanX) + Math.abs (shortDistY - pacmanY)) >
				(Math.abs (y - pacmanY) + Math.abs ((x + 1) % model.getSizeX () - pacmanX))) {
				dir = direction.Right;
			}
		}
		return dir;
	}

	protected direction runAway (Model model, int y, int x, int sizeCellX,
								 int sizeCellY, int pacmanX, int pacmanY) {
		direction dir = null;
		int shortDistX = -1;
		int shortDistY = -1;
		int tmpX = (x - 1 + model.getSizeX ()) % model.getSizeX ();
		if (isValidField (model.getAvailableCells ()[y][tmpX], openDoor) &&
			(y != (prevY / sizeCellY) || tmpX != (prevX / sizeCellX))) {
			dir = direction.Left;
			shortDistX = tmpX;
			shortDistY = y;
		}
		int tmpY = (y + 1) % model.getSizeY ();
		if (isValidField (model.getAvailableCells ()[tmpY][x], openDoor) &&
			(tmpY != (prevY / sizeCellY) || x != (prevX / sizeCellX))) {
			if ((shortDistX == -1 && shortDistY == -1) || (Math.abs (shortDistX - pacmanX) +
				 Math.abs (shortDistY - pacmanY)) < (Math.abs (tmpY - pacmanY) + Math.abs (x - pacmanX))) {
				dir = direction.Down;
				shortDistX = x;
				shortDistY = tmpY;
			}
		}

		tmpY = (y - 1 + model.getSizeY ()) % model.getSizeY ();
		if (isValidField (model.getAvailableCells ()[tmpY][x], openDoor) &&
			(tmpY != (prevY / sizeCellY) || x != (prevX / sizeCellX))) {
			if ((shortDistX == -1 && shortDistY == -1) || (Math.abs (shortDistX - pacmanX) +
				Math.abs (shortDistY - pacmanY)) < (Math.abs (tmpY - pacmanY) + Math.abs (x - pacmanX))) {
				dir = direction.Up;
				shortDistX = x;
				shortDistY = tmpY;
			}
		}

		tmpX = (x + 1) % model.getSizeX ();
		if (isValidField (model.getAvailableCells ()[y][tmpX], openDoor)
			&& (tmpX != (prevX / sizeCellX) || y != (prevY / sizeCellY))) {
			if ((shortDistX == -1 && shortDistY == -1) || (Math.abs (shortDistX - pacmanX) + Math.abs (shortDistY - pacmanY)) <
				(Math.abs (y - pacmanY) + Math.abs ((x + 1) % model.getSizeX () - pacmanX))) {
				dir = direction.Right;
			}
		}
		return dir;
	}

	protected int countingDirections (Model model, int x, int y) {
		int countDirection = 0;
		if (isValidField (
				model.getAvailableCells ()[y][(x - 1 + model.getSizeX ()) % model.getSizeX ()], openDoor)) {
			++countDirection;
		}
		if (isValidField (model.getAvailableCells ()[(y + 1) % model.getSizeY ()][x], openDoor)) {
			++countDirection;
		}
		if (isValidField (
				model.getAvailableCells ()[(y - 1 + model.getSizeY ()) % model.getSizeY ()][x], openDoor)) {
			++countDirection;
		}
		if (isValidField (
				model.getAvailableCells ()[y][(x + 1) % model.getSizeX ()], openDoor)) {
			++countDirection;
		}
		return countDirection;
	}

	protected void chooseDir (direction dir, int sizeCellX, int sizeCellY, Model model) {
		int dx = sizeCellX / 10;
		int dy = sizeCellY / 10;

		if (dir == direction.Left) {
			setPrevСoordinates (getY (), getX ());
			repeatMoveX = -dx + sizeCellX * model.getSizeX ();
			repeatMoveY = 0;
			setX ((getX () - dx + sizeCellX * model.getSizeX ()) %
				  (model.getSizeX () * sizeCellX));
		} else if (dir == direction.Down) {
			setPrevСoordinates (getY (), getX ());
			repeatMoveY = dy;
			repeatMoveX = 0;
			setY ((getY () + dy) % (model.getSizeY () * sizeCellY));
		} else if (dir == direction.Up) {
			setPrevСoordinates (getY (), getX ());
			repeatMoveY = -dy + sizeCellY * model.getSizeY ();
			repeatMoveX = 0;
			setY ((getY () - dy + sizeCellY * model.getSizeY ()) %
				  (model.getSizeY () * sizeCellY));
		} else {
			setPrevСoordinates (getY (), getX ());
			repeatMoveX = dx;
			repeatMoveY = 0;
			setX ((getX () + dx) % (model.getSizeX () * sizeCellX));
		}

	}

}
