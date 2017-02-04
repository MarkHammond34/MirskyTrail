package groupProject;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Initializes the game.
 * 
 * @author Corey Kuehl
 * @version 1
 */
public class MirskyTrail {

	/** Variables */
	private static GUI frame;
	private static Board board;
	private static Player player1;
	private static Player player2;
	private static double roundCount = 1;
	private static Display display;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Allows for the Game to look correct on different
					// platforms.
					try {
						UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					} catch (Exception e) {
						e.printStackTrace();
					}
					init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initializes the game.
	 */
	public static void init() {
		// Allows for the Game to look correct on different platforms.
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** display */
		display = new Display();

		/** Layout */
		frame = new GUI();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		/** board */
		board = new Board();

		/** 2 instances of player */
		player1 = new Player(0, 9, 1);
		player2 = new Player(0, 11, 2);
	}

	/**
	 * Returns the current player up
	 * 
	 * @return player1 if they are up, player2 if they are up
	 */
	public static Player getCurrentPlayerUp() {
		if (frame.isPlayer1Turn()) {
			return player1;
		} else if (frame.isPlayer2Turn()) {
			return player2;
		} else {
			return null;
		}
	}

	/**
	 * Handles movement of current player up once the button is clicked in the
	 * GUI
	 * 
	 * @param x
	 *            desired x position
	 * @param y
	 *            desired y position
	 * @return true if movement was successful, false if it was not
	 */
	public static boolean moveCurrentPlayer(int x, int y) {
		if (display.moveCanBeMade()) {
			if (getCurrentPlayerUp().movePiece(board, x, y)) {

				if (getCurrentPlayerUp().getPlayerNumber() == 1) {
					frame.setP1Crew(player1.getCrewCount());
					frame.setP1Supplies(player1.getSupplyCount());
				} else {
					frame.setP2Crew(player2.getCrewCount());
					frame.setP2Supplies(player2.getSupplyCount());
				}

				display.updateAfterMove(x, y, frame.getBtnP1BuySupplies(), frame.getBtnP2BuySupplies(),
						frame.getBtnP1HireCrew(), frame.getBtnP2HireCrew(), frame.getBtnP1HireSecGuard(),
						frame.getBtnP2HireSecGuard());

				frame.playTrainSound();

				return true;
			} else {
				JOptionPane.showMessageDialog(null, "INVALID MOVE!");
				return false;
			}
		}
		return false;
	}

	/**
	 * Resets the game
	 * 
	 * @return true if game was reset properly and false if game was not
	 */
	public static boolean resetGame() {
		player1 = new Player(0, 9, 1);
		player2 = new Player(0, 11, 2);
		roundCount = 1;
		board = new Board();
		frame.setVisible(false);
		frame = new GUI();

		frame.setP1Crew(player1.getCrewCount());
		frame.setP2Crew(player2.getCrewCount());
		frame.setP1Money(player1.getCurrentBalance());
		frame.setP2Money(player2.getCurrentBalance());
		frame.setP1Supplies(player1.getSupplyCount());
		frame.setP2Supplies(player2.getSupplyCount());
		frame.setP1SecurityGuards(player1.getSecurityCount());
		frame.setP2SecurityGuards(player2.getSecurityCount());

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		display = new Display();
		if (player1.getMoveCount() == 0 && player2.getMoveCount() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Closes the game
	 */
	public static void closeGame() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Returns the current player not up
	 * 
	 * @return player2 if they are not the player up player1 if they are not the
	 *         current player up
	 */
	public static Player getCurrentPlayerNotUp() {
		if (frame.isPlayer1Turn()) {
			return player2;
		} else if (frame.isPlayer2Turn()) {
			return player1;
		} else {
			return null;
		}
	}

	/**
	 * gets methods from Display
	 * 
	 * @return display
	 */
	public static Display getControlsOfButtons() {
		return display;
	}

	/**
	 * Increments the round by 0.5
	 */
	public static void incrementRoundCount() {
		roundCount += .5;
	}

	/**
	 * Returns the roundCount
	 * 
	 * @return roundCount number of rounds played
	 */
	public static double getRoundCount() {
		return roundCount;
	}

	/**
	 * Returns the board object
	 * 
	 * @return board board object that acts as a virtual game board
	 */
	public static Board getBoard() {
		return board;
	}

	/**
	 * Returns the GUI
	 * 
	 * @return frame the GUI used in the game
	 */
	public static GUI getGUI() {
		return frame;
	}
}
