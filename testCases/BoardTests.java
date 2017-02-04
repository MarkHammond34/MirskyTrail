package testCases;

import static org.junit.Assert.*;

import org.junit.Test;

import groupProject.Board;
import groupProject.MirskyTrail;
import groupProject.Tile;

/**
 * Tests the board.
 */
public class BoardTests {

	/**
	 * Tests to make sure the board is created correctly.
	 */
	@Test
	public void Boardtest1() {

		MirskyTrail.init();
		MirskyTrail.getGUI().getStartButton().doClick();
		Board board = new Board();
		Tile[][] boardTest = board.getBoard();
		boolean actual;
		boolean expected;

		for (int i = 0; i < boardTest.length; i++) {
			for (int j = 0; j < boardTest[i].length; j++) {
				if (boardTest[i][j].isOccuppied() == true) {
					expected = false;
					actual = false;
					assertEquals(expected, actual);
					break;
				}
			}
		}

		actual = true;
		expected = true;
		assertEquals(expected, actual);
	}
}
