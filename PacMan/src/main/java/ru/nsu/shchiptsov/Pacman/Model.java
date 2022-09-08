package main.java.ru.nsu.shchiptsov.Pacman;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The playing field that stores the current state of the cell is also
 * responsible for the creatures moves
 */
public class Model {
	enum returnCodes {Win, Died, FullDied, Ok}
	private final int sizeX = 28;
	private final int sizeY = 31;
	private final char[][] availableCells = new char[sizeY][sizeX];
	private int countDot = 0;
	private final int sizeCellsX;
	private final int sizeCellsY;
	private final Pacman pacman = new Pacman ();
	private final List<Ghosts> ghosts = new ArrayList<> ();
	private final int countGhost = 4;
	private int score = 0;
	private int timeRagePacman = 0;

	public int getScore () {
		return score;
	}

	public int getSizeX () {
		return sizeX;
	}

	public int getSizeY () {
		return sizeY;
	}

	public char[][] getAvailableCells () {
		return availableCells;
	}

	public void setAvailableCells (int y, int x, char value) {
		availableCells[y][x] = value;
	}

	public Pacman getPacman () {
		return pacman;
	}

	public List<Ghosts> getGhosts () {
		return ghosts;
	}

	Model (int sizeX, int sizeY) {
		sizeCellsX = sizeX;
		sizeCellsY = sizeY;
		ghosts.add (new Ghost1 ());
		ghosts.add (new Ghost2 ());
		ghosts.add (new Ghost3 ());
		ghosts.add (new Ghost4 ());
	}

	/**
	 * Checks if Pacman and Ghost are on the same cell
	 * @return Returns ok if Pacman and ghosts are on different cells,
	 * FullDied if Pacman and ghosts are on the same cell and Pacman has no more
	 * lives, Died if Pacman and ghosts are on the same cell and Pacman still
	 * has lives
	 */
	private returnCodes checkDied () {
		int pacmanX = pacman.getX () / sizeCellsX;
		int pacmanY = pacman.getY () / sizeCellsY;
		for (int i = 0; i < countGhost; ++i) {
			int ghost1X = ghosts.get (i).getX () / sizeCellsX;
			int ghost1Y = ghosts.get (i).getY () / sizeCellsY;
			if (pacmanX == ghost1X && pacmanY == ghost1Y) {
				if (pacman.isRageMode ()) {
					if (i != 0) {
						ghosts.get (i).setOpenDoor (true);
					}
					ghosts.get (i).setX (ghosts.get (i).getBeginCoordinatesX ());
					ghosts.get (i).setY (ghosts.get (i).getBeginCoordinatesY ());
					ghosts.get (i).resetCountRepeatMove ();
					score += 200;
				} else {
					ghosts.get (1).setOpenDoor (true);
					ghosts.get (2).setOpenDoor (true);
					ghosts.get (3).setOpenDoor (true);
					pacman.damageHp ();
					if (pacman.getCountHP () == 0) {
						return returnCodes.FullDied;
					} else {
						countDot = 0;
						return returnCodes.Died;
					}
				}
			}
		}
		return returnCodes.Ok;
	}

	/**
	 * Makes a move for ghosts and Pacman
	 * @return Returns ok if Pacman and ghosts are on different cells, FullDied
	 * if Pacman and ghosts are on the same cell and Pacman has no more lives,
	 * Died if Pacman and ghosts are on the same cell and Pacman still has lives,
	 * Win if Pacman managed to eat all the apples
	 */
	public returnCodes moveGhosts () {
		for (int i = 0; i < countGhost; ++i) {
			ghosts.get (i).move (sizeCellsY, sizeCellsX, pacman.getX (), pacman.getY (), this);
		}
		returnCodes ret;
		ret = checkDied ();
		return ret;
	}

	public returnCodes movePacman () {
		if (pacman.isRageMode ()) {
			timeRagePacman++;
			if (timeRagePacman == 800) {
				pacman.offRageMode ();
				for (int i = 0; i < countGhost; ++i) {
					ghosts.get (i).offFrightMode ();
				}
			}
		}
		returnCodes ret;
		if (pacman.checkMove (this, sizeCellsY, sizeCellsX)) {
			pacman.move (sizeCellsY, sizeCellsX, pacman.getX (),
						 pacman.getY (), this);
			int y =
					(int) (pacman.getY () - 0.5 * sizeCellsY) / sizeCellsY;
			int x =
					(int) (pacman.getX () - 0.5 * sizeCellsX) / sizeCellsX;
			char currentCell = availableCells[y][x];
			if (currentCell == 'd') {
				setAvailableCells (y, x, 's');
				score += 10;
				--countDot;
			} else if (currentCell == 'e') {
				timeRagePacman = 0;
				setAvailableCells (y, x, 's');
				score += 50;
				pacman.onRageMode ();
				for (int i = 0; i < countGhost; ++i) {
					ghosts.get (i).onFrightMode ();
				}
			}
		}
		ret = checkDied ();
		if (ret != returnCodes.Ok) {
			return ret;
		}
		if (countDot == 0) {
			return returnCodes.Win;
		}
		return returnCodes.Ok;
	}

	/**
	 * Parses the playing field
	 */
	public void updateField () {

		for (int i = 0; i < sizeY; ++i) {
			for (int j = 0; j < sizeX; ++j) {
				char symbol = availableCells[i][j];
				int x = (int) (j * sizeCellsX + 0.5 * sizeCellsX);
				int y = (int) (i * sizeCellsY + 0.5 * sizeCellsY);
				switch (symbol) {
					case ('d') -> ++countDot;
					case ('p') -> {
						pacman.setX (x);
						pacman.setY (y);
					}
					case ('1') -> {
						ghosts.get (0).setX (x);
						ghosts.get (0).setY (y);
						ghosts.get (0).setPrevСoordinates (
								y / sizeCellsY,
								x / sizeCellsX);
						ghosts.get (0).setBeginCoordinates (x, y);
						ghosts.get (0).resetCountRepeatMove ();
					}
					case ('2') -> {
						ghosts.get (1).setX (x);
						ghosts.get (1).setY (y);
						ghosts.get (1).setPrevСoordinates (
								y / sizeCellsY,
								x / sizeCellsX);
						ghosts.get (1).setBeginCoordinates (x, y);
						ghosts.get (1).resetCountRepeatMove ();
					}
					case ('3') -> {
						ghosts.get (2).setX (x);
						ghosts.get (2).setY (y);
						ghosts.get (2).setPrevСoordinates (
								y / sizeCellsY,
								x / sizeCellsX);
						ghosts.get (2).setBeginCoordinates (x, y);
						ghosts.get (2).resetCountRepeatMove ();
					}
					case ('4') -> {
						ghosts.get (3).setX (x);
						ghosts.get (3).setY (y);
						ghosts.get (3).setPrevСoordinates (
								y / sizeCellsY,
								x / sizeCellsX);
						ghosts.get (3).setBeginCoordinates (x, y);
						ghosts.get (3).resetCountRepeatMove ();
					}
				}
			}
		}
	}

	/**
	 * Reads and parses a file that is a game board
	 * @param nameLevel Name of the level to load
	 */
	public void loadLevel (String nameLevel) {
		String str;
		int countLine = 0;
		try (InputStream input = Model.class.getResourceAsStream (nameLevel);
			 BufferedReader reader =
					new BufferedReader(new InputStreamReader (input))){
			while ((str = reader.readLine ()) != null) {
				if (str.length () != sizeX || countLine == sizeY) {
					System.err.println ("Error file");
					return;
				} else {
					availableCells[countLine] = str.toCharArray ();
				}
				++countLine;
			}
		} catch(Exception e) {
			e.printStackTrace ();
		}
	}

}
