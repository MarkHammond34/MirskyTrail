package testCases;

import static org.junit.Assert.*;

import java.util.LinkedList;

import javax.swing.Icon;

import org.junit.Test;

import groupProject.Board;
import groupProject.GUI;
import groupProject.MirskyTrail;
import groupProject.Movement;
import groupProject.Player;
import groupProject.Tile;

/**
 * 
 * @author
 * 
 */
public class MovementTests {

	/**
	 * Tests checking for valid move conditions. Conditions checked: Distance
	 * too great, not enough supplies, valid move, trying to move onto supplies.
	 */
	@Test
	public void checkIfValidMoveTest() {
		MirskyTrail.init();
		Board board = new Board();

		// Creates a new instance of Movement.
		Movement movement = new Movement();

		boolean expected = false;
		boolean actual = movement.checkIfValidMove(board, 5, 5, 10, 10, 6, 10, 10, new LinkedList<Tile>(), 1);
		assertEquals(expected, actual);

		actual = movement.checkIfValidMove(board, 10, 5, 5, 5, 2, 1, 1, new LinkedList<Tile>(), 1);
		assertEquals(expected, actual);
		
		actual = movement.checkIfValidMove(board, 13, 10, 15, 10, 5, 20, 20, new LinkedList<Tile>(), 1);
		assertEquals(expected, actual);

		expected = true;
		actual = movement.checkIfValidMove(board, 2, 0, 0, 0, 6, 10, 10, new LinkedList<Tile>(), 1);
		assertEquals(expected, actual);
	}

	/**
	 * tests moving backwards
	 */
	@Test
	public void checkIfBackwardsValidMoveTest() {
		MirskyTrail.init();
		MirskyTrail.getGUI().getStartButton().doClick();
		Player player = new Player(0, 9, 1);
		player.buyCrew();
		player.buyCrew();
		player.buySupplies();
		player.buySupplies();

		Movement movement = new Movement();

		Tile desiredTile = MirskyTrail.getBoard().getBoard()[1][9];

		movement.makeMove(player, MirskyTrail.getBoard(), 6,
				desiredTile.getxPos(), desiredTile.getyPos());

		desiredTile = MirskyTrail.getBoard().getBoard()[0][9];

		movement.makeMove(player, MirskyTrail.getBoard(), 6,
				desiredTile.getxPos(), desiredTile.getyPos());
	}

	/**
	 * 
	 */
	@Test
	public void makeMoveTest() {
		MirskyTrail.init();
		MirskyTrail.getGUI().getStartButton().doClick();
		Player player = new Player(0, 9, 1);
		player.buyCrew();
		player.buyCrew();
		player.buySupplies();
		player.buySupplies();

		Movement movement = new Movement();

		Tile desiredTile = MirskyTrail.getBoard().getBoard()[1][9];
		player.rollDice();

		movement.makeMove(player, MirskyTrail.getBoard(),
				player.getCurrentDiceRoll(), desiredTile.getxPos(),
				desiredTile.getyPos());

		// Test track length change
		int actualTrackLength = player.getTracks().size();
		int expectedTrackLength = 1;
		assertEquals(expectedTrackLength, actualTrackLength);

		// Test for new tile being added to tracks
		int expectedNewTileXPos = 1;
		int expectedNewTileYPos = 9;
		int actualNewTileXPos = player.getTracks().get(0).getxPos();
		int actualNewTileYPos = player.getTracks().get(0).getyPos();
		assertEquals(expectedNewTileXPos, actualNewTileXPos);
		assertEquals(expectedNewTileYPos, actualNewTileYPos);

		// Tests that correct supplies and crew were taken out
		int expectedSupplyCount = 1;
		int expectedCrewCount = 2;
		int actualSupplyCount = player.getSupplyCount();
		int actualCrewCount = player.getCrewCount();
		assertEquals(expectedSupplyCount, actualSupplyCount);
		assertEquals(expectedCrewCount, actualCrewCount);

	}

	/**
	 * 
	 */
	@Test
	public void getPathTest() {
		MirskyTrail.init();
		MirskyTrail.getGUI().getStartButton().doClick();

		Movement movement = new Movement();

		Tile tile2 = MirskyTrail.getBoard().getBoard()[0][1];
		Tile tile3 = MirskyTrail.getBoard().getBoard()[0][2];
		Tile tile4 = MirskyTrail.getBoard().getBoard()[0][3];

		Tile[] expected = { tile2, tile3, tile4 };
		Tile[] actual = movement.getPath(MirskyTrail.getBoard(), 0, 0, 0, 3);

		for (int i = 0; i < actual.length; i++) {
			assertEquals(expected[i].getxPos(), actual[i].getxPos());
			assertEquals(expected[i].getyPos(), actual[i].getyPos());
		}
	}

	/**
	 * 
	 */
	@Test
	public void trackContainsTest() {

		MirskyTrail.init();
		Player player = new Player(0, 9, 1);
		MirskyTrail.getGUI().getStartButton().doClick();

		Movement movement = new Movement();

		Tile tile1 = MirskyTrail.getBoard().getBoard()[0][0];
		Tile tile4 = MirskyTrail.getBoard().getBoard()[0][3];
		Tile tile2 = MirskyTrail.getBoard().getBoard()[0][9];

		player.getTracks().add(tile1);

		boolean expected = true;
		boolean actual = movement.trackContains(player.getTracks(), tile1,
				player.getPlayerNumber());
		assertEquals(expected, actual);

		boolean expected1 = false;
		boolean actual1 = movement.trackContains(player.getTracks(), tile4,
				player.getPlayerNumber());
		assertEquals(expected1, actual1);

		boolean expected2 = true;
		boolean actual2 = movement.trackContains(player.getTracks(), tile2,
				player.getPlayerNumber());
		assertEquals(expected2, actual2);

	}

	/**
	 * Tests for the update board method
	 */
	@Test
	public void updateGUIAfterMoveTest() {
		MirskyTrail.init();
		MirskyTrail.getGUI().getStartButton().doClick();
		Movement movement = new Movement();

		LinkedList<Tile> tracks = new LinkedList<Tile>();
		for (int i = 0; i < 5; i++) {
			tracks.add(new Tile(0, i, 18, 18, false, Tile.PLAINS));
		}

		movement.updateGUIAfterMove(1, tracks, MirskyTrail.getBoard());

		// Test that the tiles in the tracks are occupied after updateTracks is
		// run
		for (int i = 0; i < 5; i++) {
			boolean actual = MirskyTrail.getBoard().getBoard()[0][i]
					.isOccuppied();
			boolean expected = true;
			assertEquals(expected, actual);
		}

		// Test that proper icons were set for the tracks
		for (int i = 0; i < 4; i++) {
			Icon actual = GUI.getMap()[0][i].getIcon();
			Icon expected = GUI.getTrackIcons()[0];
			assertEquals(expected, actual);
		}

		// Test that proper icon was set for the player piece
		Icon actual = GUI.getMap()[0][4].getIcon();
		Icon expected = GUI.getPieceIcons()[0];
		assertEquals(expected, actual);
	}

}
