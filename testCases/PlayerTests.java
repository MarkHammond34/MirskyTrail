package testCases;

import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Test;

import groupProject.Display;
import groupProject.GUI;
import groupProject.MirskyTrail;
import groupProject.Player;
import groupProject.Tile;

/**
 * Tests the player class.
 */
public class PlayerTests {

	/**
	 * Tests the functionality to buy supplies
	 */
	@Test
	public void playerBuySuppliesTest() {
		// Testing that the player is not able to purchase any supplies they
		// cannot afford
		Player p = new Player(0, 0, 1);
		p.setBalance(1);
		p.buySupplies();
		p.buySupplies();

		int expectedBalance = 0;
		int actualBalance = p.getCurrentBalance();
		assertEquals(expectedBalance, actualBalance);

		int expectedSupplyCount = 1;
		int actualSupplyCount = p.getSupplyCount();
		assertEquals(expectedSupplyCount, actualSupplyCount);
	}

	/**
	 * Tests the functionality to buy crew
	 */
	@Test
	public void playerBuyCrewTest() {
		// Testing that the player is not able to purchase any crew members they
		// cannot afford
		Player p = new Player(0, 0, 1);
		p.setBalance(7);
		p.buyCrew();
		p.buyCrew();

		int expectedBalance = 3;
		int actualBalance = p.getCurrentBalance();
		assertEquals(expectedBalance, actualBalance);

		int expectedCrewCount = 1;
		int actualSupplyCount = p.getCrewCount();
		assertEquals(expectedCrewCount, actualSupplyCount);
	}

	/**
	 * Tests the functionality to buy security
	 */
	@Test
	public void playerBuySecurityTest() {
		// Testing that the player is not able to purchase any security guards
		// they cannot afford
		Player p = new Player(0, 0, 1);
		p.setBalance(3);
		p.buySecurity();
		p.buySecurity();

		int expectedBalance = 1;
		int actualBalance = p.getCurrentBalance();
		assertEquals(expectedBalance, actualBalance);

		int expectedCrewCount = 1;
		int actualSupplyCount = p.getSecurityCount();
		assertEquals(expectedCrewCount, actualSupplyCount);
	}

	/**
	 * Makes sure that the piece is moved properly.
	 */
	@Test
	public void movePieceTest() {
		MirskyTrail.init();
		MirskyTrail.getGUI().getStartButton().doClick();
		Player player = new Player(0, 9, 1);
		player.rollDice();

		for (int i = 0; i < 5; i++) {
			player.buyCrew();
			player.buySupplies();
		}

		player.movePiece(MirskyTrail.getBoard(), 1, 9);

		// Test for piece being moved
		int expectedPlayerXPos = 1;
		int expectedPlayerYPos = 9;
		int actualPlayerXPos = player.getCurrentXPos();
		int actualPlayerYPos = player.getCurrentYPos();

		assertEquals(expectedPlayerXPos, actualPlayerXPos);
		assertEquals(expectedPlayerYPos, actualPlayerYPos);

		// Test move count was increased by 1
		int expectedMoveCount = 1;
		int actualMoveCount = player.getMoveCount();

		assertEquals(expectedMoveCount, actualMoveCount);

		// Test tracks were added
		int expectedTracksSize = 2;
		int actualTracksSize = player.getTracks().size();

		assertEquals(expectedTracksSize, actualTracksSize);

		// Test the move has to be valid
		boolean expected = false;
		boolean actual = player.movePiece(MirskyTrail.getBoard(), 10, 10);

		assertEquals(expected, actual);
	}

	/**
	 * Tests the value output from the dice roll.
	 */
	@Test
	public void rollDiceTest() {
		Player player = new Player(0, 0, 1);
		player.rollDice();

		boolean expected = true;
		boolean actual;
		if (player.getCurrentDiceRoll() != 0) {
			actual = true;
		} else {
			actual = false;
		}

		assertEquals(expected, actual);
	}

	/**
	 * Tests the isStealValid() method.
	 */
	@Test
	public void isStealValidTest1() {
		JButton stealButton = new JButton();
		Player current = new Player(10, 10, 1);
		Player victim = new Player(10, 10, 1);

		stealButton.setEnabled(true);

		Boolean expected = false;
		Boolean actual = current.stealIsValid(current, victim);

		assertEquals(expected, actual);
	}

	/**
	 * Tests the isStealValid() method.
	 */
	@Test
	public void isStealValidTest2() {
		Player current = new Player(10, 10, 1);
		Player victim = new Player(10, 10, 1);

		victim.buySupplies();
		current.buySecurity();
		MirskyTrail.incrementRoundCount();
		MirskyTrail.incrementRoundCount();

		Boolean expected = true;
		Boolean actual = current.stealIsValid(current, victim);

		assertEquals(expected, actual);
	}

	/**
	 * Tests the isStealValid() method.
	 */
	@Test
	public void isStealValidTest3() {
		Player current = new Player(10, 10, 1);
		Player victim = new Player(10, 10, 1);

		victim.buySupplies();
		current.buySecurity();

		Boolean expected = true;
		Boolean actual = current.stealIsValid(current, victim);

		assertEquals(expected, actual);
	}

	/**
	 * Tests the steal being attempted, shows either winner, loser, or tie
	 */
	@Test
	public void stealTest() {
		Player stealer = new Player(10, 10, 10);
		stealer.buySecurity();
		stealer.buySupplies();
		Player victim = new Player(10, 10, 10);
		victim.buySecurity();
		victim.buySupplies();
		Display display = new Display();
		JButton steal = new JButton();
		boolean isRollTie = true;

		stealer.attemptSteal(stealer, stealer, victim);

		if (stealer.getSupplyCount() == 2) {
			int expected = 2;
			int actual = stealer.getSupplyCount();
			assertEquals(expected, actual);
		} else if (stealer.getSecurityCount() == 0) {
			int expected = 0;
			int actual = stealer.getSecurityCount();
			assertEquals(expected, actual);
		} else {
			display.stealTied(steal, isRollTie);
			boolean expected = true;
			boolean actual = steal.isEnabled();
			assertEquals(expected, actual);
		}
	}

	/**
	 * Checks if the outcome of the steal is a tie.
	 */
	@Test
	public void stealTiedTest() {
		Display display = new Display();
		JButton steal = new JButton();
		boolean isRollTie = true;

		display.stealTied(steal, isRollTie);
		boolean expected = true;
		boolean actual = steal.isEnabled();
		assertEquals(expected, actual);
	}

	/**
	 * Tests the Good fortune and Natural Disasters method.
	 */
	@Test
	public void randomEventsTest() {
		MirskyTrail.init();
		MirskyTrail.getGUI().getStartButton().doClick();
		Player player = new Player(75, 0, 0);
		GUI gui = new GUI();

		int actual = player.randomEvents(gui.getEventPanels());
		int expected = player.randomEvents(gui.getEventPanels());

		assertEquals(expected, actual);
	}

	/**
	 * Tests the refillIsValid supplies method
	 */
	@Test
	public void refillIsValidTest1() {
		Player player1 = new Player(10, 12, 6);

		Boolean expected = false;
		Boolean actual = player1.refillIsValid();

		assertEquals(expected, actual);

	}

	/**
	 * Tests the refillIsValid supplies method
	 */
	@Test
	public void refillIsValidTest2() {
		Player player1 = new Player(10, 13, 5);

		Boolean expected = false;
		Boolean actual = player1.refillIsValid();

		assertEquals(expected, actual);

	}

	/**
	 * Tests the refillIsValid supplies method
	 */
	@Test
	public void refillIsValidTest3() {
		Player player1 = new Player(10, 22, 0);

		Boolean expected = false;
		Boolean actual = player1.refillIsValid();

		assertEquals(expected, actual);

	}

	/**
	 * Tests the refillIsValid supplies method
	 */
	@Test
	public void refillIsValidTest4() {
		Player player1 = new Player(10, 23, 19);

		Boolean expected = false;
		Boolean actual = player1.refillIsValid();

		assertEquals(expected, actual);

	}

	/**
	 * Tests the refillIsValid supplies method
	 */
	@Test
	public void refillIsValidTest5() {
		Player player1 = new Player(10, 35, 2);

		Boolean expected = false;
		Boolean actual = player1.refillIsValid();

		assertEquals(expected, actual);

	}

	/**
	 * Tests the refillIsValid supplies method
	 */
	@Test
	public void refillIsValidTest6() {
		Player player1 = new Player(10, 31, 13);

		Boolean expected = false;
		Boolean actual = player1.refillIsValid();

		assertEquals(expected, actual);

	}

	/**
	 * Tests the refillIsValid supplies method
	 */
	@Test
	public void refillIsValidTest7() {
		Player player1 = new Player(10, 36, 19);

		Boolean expected = false;
		Boolean actual = player1.refillIsValid();

		assertEquals(expected, actual);

	}

	/**
	 * Checks to make sure that the resources are updated properly.
	 */
	@Test
	public void updateResourcesAfterMoveTest() {
		Player player = new Player(0, 0, 1);

		Tile tile1 = new Tile(0, 0, 18, 18, false, Tile.PLAINS);
		Tile tile2 = new Tile(0, 1, 18, 18, false, Tile.MOUNTAINS);

		// Buy 5 supplies and crew
		for (int i = 0; i < 5; i++) {
			player.buyCrew();
			player.buySupplies();
		}

		player.updateResourcesAfterMove(tile1);
		int expectedSupplies = 4;
		int actualSupplies = player.getSupplyCount();

		assertEquals(expectedSupplies, actualSupplies);

		player.updateResourcesAfterMove(tile2);
		int expectedSupplies1 = 3;
		int actualSupplies1 = player.getSupplyCount();

		assertEquals(expectedSupplies1, actualSupplies1);
	}
}
