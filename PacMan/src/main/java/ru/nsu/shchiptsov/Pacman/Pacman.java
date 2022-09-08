package main.java.ru.nsu.shchiptsov.Pacman;

public class Pacman extends Entity {
	private int countHP = 3;
	private boolean rageMode = false;
	private direction waitTurn;

	public void setWaitTurn (direction dir) {
		waitTurn = dir;
	}

	public direction getWaitTurn () {
		return waitTurn;
	}

	public boolean isRageMode () {
		return rageMode;
	}

	public void onRageMode () {
		rageMode = true;
	}

	public void offRageMode () {
		rageMode = false;
	}

	public int getCountHP () {
		return countHP;
	}

	/**
	 * Reduces the number of lives of the Pacman by 1
	 */
	public void  damageHp () {
		--countHP;
	}

	/**
	 * The method determines the Pacman movement
	 * @param sizeCellY Height of the game cell in pixels
	 * @param sizeCellX Width of the game cell in pixels
	 * @param pacmanX Current PacMan coordinates by x
	 * @param pacmanY Current PacMan coordinates by y
	 * @param model The playing model that stores the current state of the cell
	 */
	@Override
	public void move (int sizeCellY, int sizeCellX, int pacmanX,
					  int pacmanY, Model model) {
		int newX = (getMoveX () + getX () + sizeCellX * model.getSizeX ()) % (sizeCellX * model.getSizeX ());
		setX (newX);
		int newY = (getMoveY () + getY () + sizeCellY * model.getSizeY ()) % (sizeCellY * model.getSizeY ());
		setY (newY);
	}

	/**
	 * Checks the availability of the cell that Pacman goes to
	 * @param model The playing model that stores the current state of the cell
	 * @param sizeCellsY Height of the game cell in pixels
	 * @param sizeCellsX Width of the game cell in pixels
	 * @return Returns true if the Pacman can move to the cell in question
	 */
	public boolean checkMove (Model model, int sizeCellsY, int sizeCellsX) {
		int x;
		int y;
		if (getMoveX () > 0) {
			x = ((getX () + sizeCellsX / 2) + sizeCellsX * model.getSizeX ())
				% (sizeCellsX * model.getSizeX ()) / sizeCellsX;
		} else {
			x = ((getX () - sizeCellsX / 2 + getMoveX ()) + sizeCellsX * model.getSizeX ())
				% (sizeCellsX * model.getSizeX ()) / sizeCellsX;
		}
		if (getMoveY () > 0) {
			y = (getY () + sizeCellsY / 2 + sizeCellsY * model.getSizeY ()) %
					(sizeCellsY * model.getSizeY ()) / sizeCellsY;
		} else {
			y = (getY () - sizeCellsY / 2 + getMoveY () + sizeCellsY * model.getSizeY ()) %
				(sizeCellsY * model.getSizeY ()) / sizeCellsY;
		}
		return isValidField (model.getAvailableCells ()[y][x], false);
	}
}