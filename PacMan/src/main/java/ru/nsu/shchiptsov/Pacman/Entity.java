package main.java.ru.nsu.shchiptsov.Pacman;

/**
 * The class from which the creatures are inherited: Pacman and ghosts.
 */
public abstract class Entity {
	private int x;
	private int y;
	private int moveX = 0;
	private int moveY = 0;
	static protected final int countCellDiv = 10;
	enum direction {Left, Right, Down, Up, None};

	/**
	 * Determines whether the ghosts can pass through the red cells(gates)
	 */
	protected boolean openDoor;

	protected void setOpenDoor (boolean flag) {
		openDoor = flag;
	}

	/**
	 * A method that is redefined by creatures so that each creature can walk
	 * uniquely
	 * @param sizeCellY Height of the game cell in pixels
	 * @param sizeCellX Width of the game cell in pixels
	 * @param pacmanX Current PacMan coordinates by x
	 * @param pacmanY Current PacMan coordinates by y
	 * @param model The playing model that stores the current state of the cell
	 */
	public abstract void move (int sizeCellY, int sizeCellX, int pacmanX,
					int pacmanY, Model model);

	public int getMoveX () {
		return moveX;
	}
	public void setMoveX (int dx) {
		moveX = dx;
	}
	public int getMoveY () {
		return moveY;
	}
	public void setMoveY (int dy) {
		moveY = dy;
	}

	protected boolean isValidField (char currentCell, boolean openDoor) {
		if (openDoor) {
			return currentCell != 'w';
		}
		return currentCell != 'w' && currentCell != 't';
	}

	public int getX () {
		return x;
	}
	public void setX (int newX) {
		x = newX;
	}
	public int getY () {
		return y;
	}
	public void setY (int newY) {
		y = newY;
	}

}
