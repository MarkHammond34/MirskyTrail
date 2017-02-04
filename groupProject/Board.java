package groupProject;

import java.awt.Color;
import javax.swing.JButton;

/**
 * Creates the board for the game.
 * 
 * @author Corey Kuehl
 * @version 1
 */
public class Board {

	/**
	 * Map for the board.
	 */
	private Tile[][] board;

	/**
	 * Number of rows.
	 */
	final public static int NUM_ROWS = 39;

	/**
	 * Number of columns.
	 */
	final public static int NUM_COLS = 21;

	/**
	 * Width of the tile.
	 */
	final public static int WIDTH = 18;

	/**
	 * Height of the tile.
	 */
	final public static int HEIGHT = 18;

	// TEST
	Movement movement = new Movement();

	/**
	 * Creates the Board.
	 */
	public Board() {
		/** Creates the map */
		board = new Tile[NUM_ROWS][NUM_COLS];
		createBoard(GUI.getMap(), board);
	}

	/**
	 * Mirrors the state of the GUI.
	 * 
	 * @param btns
	 *            array of JButtons from the GUI.
	 * @param board
	 *            array of Tiles to match the GUI.
	 */
	public void createBoard(JButton[][] btns, Tile[][] board) {
		// Set the tile types based on the background color of the JButtons.
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (btns[i][j].getBackground() == Color.lightGray) {
					board[i][j] = new Tile(i, j, i * 18, j * 18, false, Tile.PLAINS);
				} else if (btns[i][j].getBackground() == Color.magenta) {
					board[i][j] = new Tile(i, j, i * 18, j * 18, false, Tile.SUPPLY);
				} else if (btns[i][j].getBackground() == Color.green) {
					board[i][j] = new Tile(i, j, i * 18, j * 18, false, Tile.FOREST);
				} else if (btns[i][j].getBackground() == Color.cyan) {
					board[i][j] = new Tile(i, j, i * 18, j * 18, false, Tile.WATER);
				} else if (btns[i][j].getBackground() == Color.gray) {
					board[i][j] = new Tile(i, j, i * 18, j * 18, false, Tile.MOUNTAINS);
				} else if (btns[i][j].getBackground() == Color.pink) {
					board[i][j] = new Tile(i, j, i * 18, j * 18, false, Tile.START);
				} else {
					board[i][j] = new Tile(i, j, i * 18, j * 18, false, Tile.GOAL);
				}
			}
		}
	}

	/**
	 * Gets the current state of the tile board.
	 * 
	 * @return the character version of the tile map.
	 */
	public Tile[][] getBoard() {
		return board;
	}
}
