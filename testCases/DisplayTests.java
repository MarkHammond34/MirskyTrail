package testCases;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.junit.Test;

import groupProject.Display;
import groupProject.MirskyTrail;
import groupProject.Player;

/**
 * Tests the display class.
 */
public class DisplayTests {

	Display display = new Display();

	/**
	 * tests to see when changing turns if button disables properly
	 */
	@Test
	public void StartGameEnableDisableButtonsFalseTest() {
		JButton buySupplies = new JButton();
		JButton sellSupplies = new JButton();

		JPanel fullOfButtons = new JPanel();

		fullOfButtons.add(buySupplies);
		fullOfButtons.add(sellSupplies);

		display.changeTurns(fullOfButtons, false);
		boolean expected = false;
		assertEquals(expected, buySupplies.isEnabled());
	}

	/**
	 * tests to see when changing turns if button enables properly
	 */
	@Test
	public void StartGameEnableDisableButtonsTrueTest2() {
		JButton buySupplies = new JButton();
		JButton sellSupplies = new JButton();

		JPanel fullOfButtons = new JPanel();

		fullOfButtons.add(buySupplies);
		fullOfButtons.add(sellSupplies);

		display.changeTurns(fullOfButtons, true);
		boolean expected = true;
		assertEquals(expected, sellSupplies.isEnabled());
	}

	/**
	 * tests to see when purchaseButtons can be enabled
	 */
	@Test
	public void checkStateOfPurchasingButtonsTest() {

		JButton btnP1BuySupplies = new JButton();
		btnP1BuySupplies.setName("btnP1BuySupplies");
		JButton btnP1BuyCrew = new JButton();
		btnP1BuyCrew.setName("btnP1BuyCrew");
		JButton btnP1BuySecGuards = new JButton();
		btnP1BuySecGuards.setName("btnP1BuySecGuards");

		JButton btnP2BuySupplies = new JButton();
		btnP2BuySupplies.setName("btnP2BuySupplies");
		JButton btnP2BuyCrew = new JButton();
		btnP2BuyCrew.setName("btnP2BuyCrew");
		JButton btnP2BuySecGuard = new JButton();
		btnP2BuySecGuard.setName("btnP2BuySecGuard");
		boolean startSquare = true;
		boolean moveClicked = false;
		Player player1 = new Player(0, 9, 1);
		display.enableDisablePurchaseButtons(startSquare, moveClicked, player1, btnP1BuySupplies, btnP1BuyCrew,
				btnP1BuySecGuards);
		boolean expected = true;
		assertEquals(expected, btnP1BuySupplies.isEnabled());
		assertEquals(expected, btnP1BuyCrew.isEnabled());
		assertEquals(expected, btnP1BuySecGuards.isEnabled());
	}

	/**
	 * tests to see when purchase buttons are disabled
	 */
	@Test
	public void checkStateOfPurchasingButtonsTest2() {
		JButton btnP1BuySupplies = new JButton();
		btnP1BuySupplies.setName("btnP1BuySupplies");
		JButton btnP1BuyCrew = new JButton();
		btnP1BuyCrew.setName("btnP1BuyCrew");
		JButton btnP1BuySecGuards = new JButton();
		btnP1BuySecGuards.setName("btnP1BuySecGuards");

		JButton btnP2BuySupplies = new JButton();
		btnP2BuySupplies.setName("btnP2BuySupplies");
		JButton btnP2BuyCrew = new JButton();
		btnP2BuyCrew.setName("btnP2BuyCrew");
		JButton btnP2BuySecGuard = new JButton();
		btnP2BuySecGuard.setName("btnP2BuySecGuard");
		boolean startSquare = false;
		boolean moveClicked = false;

		Player player2 = new Player(0, 9, 1);
		display.enableDisablePurchaseButtons(startSquare, moveClicked, player2, btnP2BuySupplies, btnP2BuyCrew,
				btnP2BuySecGuard);
		boolean expected = false;
		assertEquals(expected, btnP2BuySupplies.isEnabled());
		expected = false;
		assertEquals(expected, btnP2BuyCrew.isEnabled());
		expected = false;
		assertEquals(expected, btnP2BuySecGuard.isEnabled());
	}

	/**
	 * test for move being enabled
	 */
	@Test
	public void enableDisableMoveTest() {
		int roundCount = 2;
		JButton moveButton = new JButton();

		display.enableDisableMoveButton(moveButton, roundCount);
		boolean expected = true;
		boolean actual = moveButton.isEnabled();

		assertEquals(expected, actual);
	}

	/**
	 * test for move being disabled
	 */
	@Test
	public void enableDisableMoveTest2() {
		int roundCount = 1;
		JButton moveButton = new JButton();

		display.enableDisableMoveButton(moveButton, roundCount);
		boolean expected = false;
		boolean actual = moveButton.isEnabled();

		assertEquals(expected, actual);
	}

	/**
	 * test for when an action is made and all buttons must be disabled except
	 * finish
	 */
	@Test
	public void turnMustEndTest() {
		JButton supplies = new JButton();
		JButton crew = new JButton();
		JButton secGuards = new JButton();
		JButton steal = new JButton();
		JButton move = new JButton();
		JButton refill = new JButton();
		JButton finish = new JButton();

		display.turnMustEnd(supplies, crew, secGuards, steal, move, refill, finish);

		boolean expected = false;
		boolean actual = supplies.isEnabled();
		assertEquals(expected, actual);
	}

	/**
	 * test for when move disabled when return to start
	 */
	@Test
	public void moveEnabledWhenReturnToStartTest() {
		double whichRoundP1 = 2.0;
		double whichRoundP2 = 0.0;
		double roundCount = 2.0;
		boolean moveEnabledP1 = false;
		boolean moveEnabledP2 = false;
		JButton btnMoveP1 = new JButton();
		JButton btnMoveP2 = new JButton();

		display.moveEnabledAfterReturnToStart(whichRoundP1, whichRoundP2, roundCount, moveEnabledP1, moveEnabledP2,
				btnMoveP1, btnMoveP2);
		boolean expected = false;
		boolean actual = btnMoveP1.isEnabled();
		assertEquals(expected, actual);
	}

	/**
	 * test for when move enabled when return to start
	 */
	@Test
	public void moveEnabledWhenReturnToStartTest2() {
		MirskyTrail.init();
		double whichRoundP1 = 2.0;
		double whichRoundP2 = 0.0;
		double roundCount = 4.0;
		boolean moveEnabledP1 = true;
		boolean moveEnabledP2 = false;
		JButton btnMoveP1 = new JButton();
		JButton btnMoveP2 = new JButton();

		display.moveEnabledAfterReturnToStart(whichRoundP1, whichRoundP2, roundCount, moveEnabledP1, moveEnabledP2,
				btnMoveP1, btnMoveP2);
		boolean expected = true;
		boolean actual = btnMoveP1.isEnabled();

		assertEquals(expected, actual);
	}
}
