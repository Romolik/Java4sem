package main.java.ru.nsu.shchiptsov.Befunge;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class ExecutionContext {
	/**
	 * Allowed number of characters per file line to interpret.
	 */
	static public final int ROWS = 80;
	/**
	 * Allowed number of lines in the file to interpret.
	 */
	static public final int COLUMNS = 25;
	/**
	 * The main data structure in Befunge. All operations use it to get
	 * arguments and write results.
	 */
	private Stack<Character> stack = new Stack<> ();
	public Stack<Character> getStack () {
		return stack;
	}
	/**
	 * This is where the program that we interpret is stored.
	 */
	private char[][] table = new char[COLUMNS][ROWS];
	{
		for (char[] row : table) {
			Arrays.fill (row, ' ');
		}
	}
	public char [][] getTable () {
		return table;
	}

	/**
	 * Shows the openness of quotation marks
	 */
	private boolean openQuotationMarks = false;
	public boolean getOpenQuotationMarks () {
		return openQuotationMarks;
	}
	public void switchOpenQuotationMarks () {
		openQuotationMarks = !openQuotationMarks;
	}
	private InputStream InputStreamForTests;
	protected boolean isInputStreamForTests = false;
	private OutputStream OutputStreamForTests;
	protected boolean isOutputStreamForTests = false;
	private boolean noEndProgram = true;

	public boolean getNoEndProgram () {
		return noEndProgram;
	}
	public void switchNoEndProgram () {
		noEndProgram = false;
	}

	/**
	 * For Tests.
	 * @param inputStream Stream for Tests.
	 */
	public void setInputStreamForTests (InputStream inputStream) {
		InputStreamForTests = inputStream;
		isInputStreamForTests = true;
	}
	/**
	 * For Tests.
	 * @param outputStream Stream for Tests.
	 */
	public void setOutputStreamForTests (OutputStream outputStream) {
		OutputStreamForTests = outputStream;
		isOutputStreamForTests = true;
	}
	/**
	 * Writes in the test thread or in the console.
	 */
	public void outputStream (byte[] symbols) {
		if (isOutputStreamForTests) {
			try {
				OutputStreamForTests.write (symbols);
			} catch (IOException e) {
				e.printStackTrace ();
			}
		} else {
			Charset ch = Charset.forName ("UTF-8");
			String str = new String (symbols, ch);
			System.out.print (str);
		}
	}
	/**
	 * Reads from the test thread or from the console.
	 * @return character code.
	 */
	public int inputStream () {
		if (isInputStreamForTests) {
			try {
				return InputStreamForTests.read ();
			} catch (Exception e) {
				e.printStackTrace ();
			}
		} else {
			try (Scanner sc = new Scanner (System.in)) {
				return sc.nextInt ();
			} catch (Exception e) {
				e.printStackTrace ();
			}
		}
		return 0;
	}
	/**
	 * @see Pointer
	 */
	private Pointer pointer = new Pointer ();
	public Pointer getPointer () {
		return pointer;
	}
	/**
	 *At the beginning of the execution, the program counter is located in the
	 * upper left cell of the field and is directed to the right. It moves in
	 * the direction it points, one cell at a time. When the pointer hits the
	 * cell with the command, it is executed. Commands can affect the direction
	 * of the pointer.
	 */
	public class Pointer {

		/**
		 * Coordinates pointed to by the pointer in the table.
		 */
		private int x = 0, y=0;

		/**
		 * Coordinates by which the pointer is shifted in one move in the table.
		 */
		private int moveX = 1, moveY = 0;

		/**
		 * Function for getting the value {@link Pointer#x}.
		 * @return The x coordinate in the table.
		 */
		public int getX () {
			return x;
		}

		/**
		 * Function for getting the value {@link Pointer#y}.
		 * @return The y coordinate in the table.
		 */
		public int getY () {
			return y;
		}

		/**
		 * Shifts the pointer by one step.
		 */
		public void move () {
			x = (x + moveX + ROWS) % ROWS;
			y = (y + moveY + COLUMNS) % COLUMNS;
		}

		/**
		 * Changes the pointer offset in one step.
		 * @param moveXX New offset of the pointer in the x coordinate.
		 * @param moveYY New offset of the pointer in the y coordinate.
		 */
		public void setMove (int moveXX, int moveYY) {
			moveX = moveXX;
			moveY = moveYY;
		}
		public int getMoveX () {
			return moveX;
		}
		public int getMoveY () {
			return moveY;
		}
	}

	/**
	 * Checks the stack for empty before pop, if the stack is empty adds a null
	 * element.
	 * @param stack The main data structure in Befunge. All operations use it to
	 * get arguments and write results.
	 */
	public void checkEmptyStack (Stack<Character> stack) {
		if (stack.empty ()) {
			stack.push ((char) (0));
		}
	}

}
