package groupProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Interface for methods that control or affect the buttons.
 */
public class Display implements ButtonController {

	/**
	 * Variables that correspond to what buttons are clicked
	 */
	private boolean moveButtonWasClicked;
	private boolean finishTurnButtonWasClicked;
	private boolean rollButtonWasClicked;
	private boolean stealButtonWasClicked;
	private boolean refillButtonWasClicked;
	private boolean startSquareP1 = true;
	private boolean startSquareP2 = true;
	private boolean isMoveEnabledAfterReturnToStartP1;
	private boolean isMoveEnabledAfterReturnToStartP2;
	private double returnToStartOnRoundP1;
	private double returnToStartOnRoundP2;

	/**
	 * Sets color of the button passed in depending on if its a i and j
	 * positions
	 * 
	 * @param i
	 *            x position of button
	 * @param j
	 *            y position of button
	 * @param button
	 *            JButton being changed
	 */
	@Override
	public void setTileColor(int i, int j, JButton button) {
		// Allows for the Game to look correct on different platforms.
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// COL 0
		if (i == 0) {
			if (j == 10) {
				button.setBackground(Color.magenta);
				button.setOpaque(true);
				// Start square P1
			} else if (i == 0 && j == 9) {
				button.setBorder(null);
				button.setBackground(Color.pink);
				button.setFont(new Font("Tahoma", Font.BOLD, 16));
				button.setIcon(GUI.getPieceIcons()[0]);
				button.setOpaque(true);
				// Start square P2
			} else if (i == 0 && j == 11) {
				button.setBorder(null);
				button.setIcon(GUI.getPieceIcons()[1]);
				button.setBackground(Color.pink);
				button.setFont(new Font("Tahoma", Font.BOLD, 16));
				button.setOpaque(true);
			} else {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			}
			// COL 1, 2, 3, 7, 8, 23, 24, 25, 31, 32, 37
		} else if (i > 0 && i < 4 || i == 7 || i == 8 || i > 22 && i < 26
				|| i == 31 || i == 32 || i == 37) {
			button.setBackground(Color.lightGray);
			button.setOpaque(true);
			// COL 4, 5, 6
		} else if (i > 3 && i < 7) {
			if (j < 6 || j > 14) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.cyan);
				button.setOpaque(true);
			}
			// COL 9, 10
		} else if (i == 9 || i == 10) {
			if (j < 4 || j > 5 && j < 15 || j > 16) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.green);
				button.setOpaque(true);
			}
			// COL 11, 12
		} else if (i == 11 || i == 12) {
			if (j < 4 || j > 8 && j < 12 || j > 16) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.green);
				button.setOpaque(true);
			}
			// COL 13, 14
		} else if (i == 13 || i == 14) {
			if (j < 5 || j > 15) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else if (i == 13 && j == 6 || i == 13 && j == 14) {
				button.setBackground(Color.magenta);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.green);
				button.setOpaque(true);
			}
			// COL 15, 16, 17, 18
		} else if (i == 15 || i == 16 || i == 17 || i == 18) {
			if (j < 8 || j > 12) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else if (i == 15 && j == 10) {
				button.setBackground(Color.magenta);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.green);
				button.setOpaque(true);
			}
			// COL 19, 20, 21
		} else if (i == 19 || i == 20 || i == 21) {
			if (j < 3 || j > 17) {
				button.setBackground(Color.cyan);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			}
			// COL 22, 35
		} else if (i == 22 || i == 35) {
			if (j == 1 || j == 19) {
				button.setBackground(Color.magenta);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			}
			// COL 26, 29
		} else if (i == 26 || i == 29) {
			if (j < 6 || j > 14) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.gray);
				button.setOpaque(true);
			}
			// COL 27
		} else if (i == 27) {
			if (j < 3 || j > 17) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.gray);
				button.setOpaque(true);
			}
			// COL 28
		} else if (i == 28) {
			if (j < 1 || j > 19) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else if (j == 10) {
				button.setBackground(Color.magenta);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.gray);
				button.setOpaque(true);
			}
			// COL 30
		} else if (i == 30) {
			if (j > 8 && j < 12) {
				button.setBackground(Color.gray);
				button.setOpaque(true);
			} else if (j == 7 || j == 13) {
				button.setBackground(Color.magenta);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			}
			// COL 33
		} else if (i == 33) {
			if (j > 8 && j < 12) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.cyan);
				button.setOpaque(true);
			}
			// COL 34
		} else if (i == 34) {
			if (j > 4 && j < 16) {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.cyan);
				button.setOpaque(true);
			}
			// COL 36
		} else if (i == 36) {
			if (j > 7 && j < 13) {
				button.setBackground(Color.cyan);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			}
			// COL 37
		} else {
			if (j == 10) {
				button.setBackground(Color.orange);
				button.setOpaque(true);
			} else {
				button.setBackground(Color.lightGray);
				button.setOpaque(true);
			}
		}
	}

	/**
	 * Imports the images of the dice and creates icons for each one
	 * 
	 * @param dieIcons
	 *            image of the die
	 * @param trackIcons
	 *            image of the tracks
	 * @param pieceIcons
	 *            image of the piece
	 * @param eventImages
	 *            images of the events that pop up
	 * @return the correct image
	 */
	@Override
	public boolean importGameImages(ImageIcon[] dieIcons,
			ImageIcon[] trackIcons, ImageIcon[] pieceIcons,
			ImageIcon[] eventImages) {
		try {
			dieIcons[0] = new ImageIcon("src/res/die/die1.gif");
			dieIcons[1] = new ImageIcon("src/res/die/die2.gif");
			dieIcons[2] = new ImageIcon("src/res/die/die3.gif");
			dieIcons[3] = new ImageIcon("src/res/die/die4.gif");
			dieIcons[4] = new ImageIcon("src/res/die/die5.gif");
			dieIcons[5] = new ImageIcon("src/res/die/die6.gif");

			trackIcons[0] = new ImageIcon(
					"src/res/player/RedTrackHorizontal.gif");
			trackIcons[1] = new ImageIcon(
					"src/res/player/BlueTrackHorizontal.gif");
			trackIcons[2] = new ImageIcon(
					"src/res/player/RedBlueCrossTrack.gif");

			pieceIcons[0] = new ImageIcon("src/res/player/Player1Piece.gif");
			pieceIcons[1] = new ImageIcon("src/res/player/Player2Piece.gif");

			eventImages[0] = new ImageIcon("src/res/event/storm.png");
			eventImages[1] = new ImageIcon("src/res/event/tsunami.png");
			eventImages[2] = new ImageIcon("src/res/event/trump.png");
			eventImages[3] = new ImageIcon("src/res/event/snake.png");
			eventImages[4] = new ImageIcon("src/res/event/quiz.png");
			eventImages[5] = new ImageIcon("src/res/event/monster.png");
			eventImages[6] = new ImageIcon("src/res/event/quiz2.png");
			eventImages[7] = new ImageIcon("src/res/event/camp.png");
			eventImages[8] = new ImageIcon("src/res/event/game.png");
			eventImages[9] = new ImageIcon("src/res/event/lottery.png");
			eventImages[10] = new ImageIcon("src/res/event/team.png");
			eventImages[11] = new ImageIcon("src/res/event/toy.png");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method will randomly select a player to go first
	 * 
	 * @param panelPlayer1
	 *            buttons to be enabled/disabled
	 * @param panelPlayer2
	 *            buttons to be enabled/disabled
	 */
	public int firstTurn(JPanel panelPlayer1, JPanel panelPlayer2) {
		Random generator = new Random();
		int startFirst = generator.nextInt(2);

		if (startFirst == 0) {
			changeTurns(panelPlayer1, true);
			return 1;
		} else {
			changeTurns(panelPlayer2, true);
			return 2;
		}
	}

	/**
	 * This method will enable/disable buttons for the respective player based
	 * on who's turn it is
	 * 
	 * @param panel
	 *            of buttons for the respective player
	 * @param isEnabled
	 *            true or false
	 */
	public void changeTurns(JPanel panel, boolean isEnabled) {

		Component[] components = panel.getComponents();

		for (int i = 0; i < components.length; i++) {

			components[i].setEnabled(isEnabled);
		}
	}

	/**
	 * This method will check the current balance of the player and
	 * enable/disable buttons accordingly, buttons will be enabled only if on
	 * the start tile
	 * 
	 * @param startSquare
	 *            if true then enable buttons
	 * @param moveButtonClicked
	 *            if false then can enable buttons if needed
	 * @param player
	 *            get current player
	 * @param supplies
	 *            enabled/disabled button
	 * @param crew
	 *            enabled/disabled button
	 * @param secGuards
	 *            enabled/disabled button
	 */
	@Override
	public void enableDisablePurchaseButtons(boolean startSquare,
			boolean moveButtonClicked, Player player, JButton supplies,
			JButton crew, JButton secGuards) {

		if (startSquare && !moveButtonClicked) {
			if (player.getCurrentBalance() >= 4) {
				// All the buttons can be enabled for the player
				supplies.setEnabled(true);
				crew.setEnabled(true);
				secGuards.setEnabled(true);
			} else if (player.getCurrentBalance() >= 2) {
				switch (supplies.getName()) {
				// Buy Crew must be enabled bc not enough money
				case "btnP1BuySupplies":
				case "btnP2BuySupplies":
					supplies.setEnabled(true);
					break;
				}
				switch (crew.getName()) {
				// crew can't be enabled
				case "btnP1BuyCrew":
				case "btnP2BuyCrew":
					crew.setEnabled(false);
					break;
				}
				switch (secGuards.getName()) {
				// Sec guard can be enabled
				case "btnP1BuySecGuard":
				case "btnP2BuySecGuard":
					secGuards.setEnabled(true);
					break;
				}
			} else if (player.getCurrentBalance() == 1) {
				switch (supplies.getName()) {
				// supplies can be enabled
				case "btnP1BuySupplies":
				case "btnP2BuySupplies":
					supplies.setEnabled(true);
					break;
				}
				switch (crew.getName()) {
				// crew must be disabled, not enough money
				case "btnP1BuyCrew":
				case "btnP2BuyCrew":
					crew.setEnabled(false);
					break;
				}
				switch (secGuards.getName()) {
				// sec guard must be disabled, not enough money
				case "btnP1BuySecGuard":
				case "btnP2BuySecGuard":
					secGuards.setEnabled(false);
					break;
				}
			} else {
				// have $0, all buttons disabled bc not enough money
				supplies.setEnabled(false);
				crew.setEnabled(false);
				secGuards.setEnabled(false);
			}
		} else {
			supplies.setEnabled(false);
			crew.setEnabled(false);
			secGuards.setEnabled(false);
		}
	}

	/**
	 * This method will display who's turn it is
	 * 
	 * @param playersTurn
	 *            the player whose turn it is.
	 * @param player1
	 *            player 1
	 * @param player2
	 *            player 2
	 */
	public void displayPlayersTurn(JLabel playersTurn, JButton player1,
			JButton player2) {
		if (player1.getName() == "btnP1FinishedTurn") {
			if (player1.isEnabled() == true) {
				playersTurn.setText("Player 1's turn!");
			}
		}
		if (player2.getName() == "btnP2FinishedTurn") {
			if (player2.isEnabled() == true) {
				playersTurn.setText("Player 2's turn!");
			}
		}
	}

	/**
	 * This method will enable/disable the move button when round 2 begins
	 * 
	 * @param moveButton
	 *            move button in the gui
	 * @param roundCount
	 *            amount of rounds passed
	 */
	@Override
	public void enableDisableMoveButton(JButton moveButton, double roundCount) {
		if (roundCount >= 2) {
			moveButton.setEnabled(true);
		} else {
			moveButton.setEnabled(false);
		}
	}

	/**
	 * This method will enable/disable steal button if it's valid
	 * 
	 * @param currentPlayer
	 *            current player up
	 * @param victim
	 *            player not up
	 * @param stealButton
	 *            steal button from the gui
	 */
	@Override
	public void enableDisableStealButton(Player currentPlayer, Player victim,
			JButton stealButton) {
		if (currentPlayer.stealIsValid(currentPlayer, victim)) {
			stealButton.setEnabled(true);
		} else {
			stealButton.setEnabled(false);
		}
	}

	/**
	 * This method will enable/disable refill button if it's valid
	 * 
	 * @param currentPlayer
	 *            current player up.
	 * @param refillButton
	 *            refill button from the gui
	 */
	@Override
	public void enableDisableRefillButton(Player currentPlayer,
			JButton refillButton) {
		if (currentPlayer.refillIsValid() && MirskyTrail.getRoundCount() >= 2) {
			refillButton.setEnabled(true);
		} else {
			refillButton.setEnabled(false);
		}
	}

	/**
	 * this method will set the player's name and show to users
	 * 
	 * @param playerName
	 *            the player's name
	 * @param label
	 *            is the label being used
	 * @param playerNumber
	 *            is the number of the player whose turn it is
	 * @return true if set correctly, false otherwise
	 */
	@Override
	public boolean setPlayerName(String playerName, JLabel label,
			int playerNumber) {
		if (playerName.length() > 1) {
			label.setText(playerName);
			return true;
		} else {
			if (playerNumber == 1) {
				label.setText("Player 1");
				playerName = "Player 1";
			} else {
				label.setText("Player 2");
				playerName = "Player 2";
			}
			return false;
		}
	}

	/**
	 * if the steal attempt tied, only enable the steal button to try again
	 * 
	 * @param stealButton
	 *            button passed in
	 */
	public void stealTied(JButton stealButton, boolean isRollTie) {
		if (isRollTie) {
			stealButton.setEnabled(true);
		}
	}

	/**
	 * This method is called when a turn must end
	 * 
	 * @param supplies
	 *            the supplies button
	 * @param crew
	 *            hire crew button
	 * @param secGuards
	 *            hire sec guards button
	 * @param stealButton
	 *            steal button from the gui
	 * @param moveButton
	 *            move button from the gui
	 * @param refillButton
	 *            refill button from the gui
	 * @param finish
	 *            finish turn button from the gui
	 */
	public void turnMustEnd(JButton supplies, JButton crew, JButton secGuards,
			JButton stealButton, JButton moveButton, JButton refillButton,
			JButton finish) {
		supplies.setEnabled(false);
		crew.setEnabled(false);
		secGuards.setEnabled(false);
		stealButton.setEnabled(false);
		moveButton.setEnabled(false);
		refillButton.setEnabled(false);
		finish.setEnabled(true);
	}

	/**
	 * this method will determine is a move can be made
	 * 
	 * @return true if the move can be made
	 */
	public boolean moveCanBeMade() {
		if (MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked()
				&& MirskyTrail.getControlsOfButtons()
						.getIsRollButtonWasClicked()) {
			return true;
		}
		return false;
	}

	/**
	 * This method will make updates to buttons after move
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param btnP1BuySupplies
	 *            supplies for player 1
	 * @param btnP2BuySupplies
	 *            supplies for player 2
	 * @param btnP1HireCrew
	 *            button to hire crew for p1
	 * @param btnP2HireCrew
	 *            button to hire crew for p2
	 * @param btnP1HireSecGuards
	 *            button to hire sec guards for p1
	 * @param btnP2HireSecGuards
	 *            button to hire sec guards for p2
	 * @param winnerTextBox
	 *            text box for the user
	 * @param endOfGamePanel
	 *            end of game panel
	 */
	public void updateAfterMove(int x, int y, JButton btnP1BuySupplies,
			JButton btnP2BuySupplies, JButton btnP1HireCrew,
			JButton btnP2HireCrew, JButton btnP1HireSecGuards,
			JButton btnP2HireSecGuards) {
		if (MirskyTrail.getCurrentPlayerUp().getPlayerNumber() == 1) {
			enableDisablePurchaseButtons(MirskyTrail.getControlsOfButtons()
					.getIsStartSquareP1(), MirskyTrail.getControlsOfButtons()
					.getIsMoveButtonWasClicked(),
					MirskyTrail.getCurrentPlayerUp(), btnP1BuySupplies,
					btnP1HireCrew, btnP1HireSecGuards);
			// Check for winner
			if (x == 38 && y == 10) {
				MirskyTrail.closeGame();
			}
		} else {
			enableDisablePurchaseButtons(MirskyTrail.getControlsOfButtons()
					.getIsStartSquareP2(), MirskyTrail.getControlsOfButtons()
					.getIsMoveButtonWasClicked(),
					MirskyTrail.getCurrentPlayerUp(), btnP2BuySupplies,
					btnP2HireCrew, btnP2HireSecGuards);
			// Check for winner
			if (x == 38 && y == 10) {
				MirskyTrail.closeGame();
			}
		}
	}

	/**
	 * When returning to the start tile, must wait two turns before move is
	 * enabled
	 * 
	 * @param btnMoveP1
	 *            player 1s move button
	 * @param btnMoveP2
	 *            player 2s move button
	 */
	public void moveEnabledAfterReturnToStart(double whichRoundP1,
			double whichRoundP2, double roundCount, boolean moveEnabledP1,
			boolean moveEnabledP2, JButton btnMoveP1, JButton btnMoveP2) {
		if (whichRoundP1 > 0 && whichRoundP1 + 2 > roundCount) {
			btnMoveP1.setEnabled(false);
		} else if (whichRoundP1 + 2 <= roundCount && moveEnabledP1) {
			btnMoveP1.setEnabled(true);
			whichRoundP1 = 0;
			moveEnabledP1 = false;
			MirskyTrail.getControlsOfButtons().setReturnToStartOnRoundP1(
					whichRoundP1);
			MirskyTrail.getControlsOfButtons()
					.setMoveEnabledAfterReturnToStartP1(moveEnabledP1);
		}
		if (whichRoundP2 > 0 && whichRoundP2 + 2 > roundCount) {
			btnMoveP2.setEnabled(false);
		} else if (whichRoundP2 + 2 <= roundCount && moveEnabledP2) {
			btnMoveP2.setEnabled(true);
			whichRoundP2 = 0;
			moveEnabledP2 = false;
			MirskyTrail.getControlsOfButtons().setReturnToStartOnRoundP2(
					whichRoundP2);
			MirskyTrail.getControlsOfButtons()
					.setMoveEnabledAfterReturnToStartP2(moveEnabledP2);
		}
	}
	/**
	 * 
	 * @return getter moveButtonWasClicked
	 */
	/**
	 * get move button clicked flag
	 * 
	 * @return moveButtonWasClicked
	 */
	public boolean getIsMoveButtonWasClicked() {
		return moveButtonWasClicked;
	}
	/**
	 * 
	 * @param setter moveButtonWasClicked
	 */
	/**
	 * set move button clicked flag
	 * 
	 * @param moveButtonWasClicked
	 */
	public void setMoveButtonWasClicked(boolean moveButtonWasClicked) {
		this.moveButtonWasClicked = moveButtonWasClicked;
	}
	/**
	 * 
	 * @return getter isFinishTurnButtonWasClicked
	 */
	/**
	 * get finish turn button clicked flag
	 * 
	 * @return finishTurnButtonWasClicked
	 */
	public boolean getIsFinishTurnButtonWasClicked() {
		return finishTurnButtonWasClicked;
	}
	/**
	 * 
	 * @param setter finishTurnButtonWasClicked
	 */
	/**
	 * set finish turn button flag
	 * 
	 * @param finishTurnButtonWasClicked
	 */
	public void setFinishTurnButtonWasClicked(boolean finishTurnButtonWasClicked) {
		this.finishTurnButtonWasClicked = finishTurnButtonWasClicked;
	}
	/**
	 * 
	 * @return getter isRollButtonWasClicked
	 */
	/**
	 * get roll clicked flag
	 * 
	 * @return rollButtonWasClicked
	 */
	public boolean getIsRollButtonWasClicked() {
		return rollButtonWasClicked;
	}
	/**
	 * 
	 * @param setter rollButtonWasClicked
	 */
	/**
	 * set roll button clicked flag
	 * 
	 * @param rollButtonWasClicked
	 */
	public void setRollButtonWasClicked(boolean rollButtonWasClicked) {
		this.rollButtonWasClicked = rollButtonWasClicked;
	}
	/**
	 * 
	 * @return getter isStealButtonWasClicked
	 */
	/**
	 * get steal button clicked flag
	 * 
	 * @return stealButtonWasClicked
	 */
	public boolean getIsStealButtonWasClicked() {
		return stealButtonWasClicked;
	}
	/**
	 * 
	 * @param stealButtonWasClicked
	 */
	/**
	 * set flag if steal button was clicked
	 * 
	 * @param stealButtonWasClicked
	 */
	public void setStealButtonWasClicked(boolean stealButtonWasClicked) {
		this.stealButtonWasClicked = stealButtonWasClicked;
	}
	/**
	 * 
	 * @return getter isRefillButtonClicked
	 */
	/**
	 * get flag for refill button clicked
	 * 
	 * @return refillButtonWasClicked
	 */
	public boolean getIsRefillButtonWasClicked() {
		return refillButtonWasClicked;
	}
	/**
	 * 
	 * @param setter refillButtonWasClicked
	 */
	/**
	 * set the refill button was clicked
	 * 
	 * @param refillButtonWasClicked
	 */
	public void setRefillButtonWasClicked(boolean refillButtonWasClicked) {
		this.refillButtonWasClicked = refillButtonWasClicked;
	}
	/**
	 * 
	 * @return getter isStartSquare
	 */
	/**
	 * get the flag for start square P1
	 * 
	 * @return startSquareP1
	 */
	public boolean getIsStartSquareP1() {
		return startSquareP1;
	}
	/**
	 * 
	 * @param setter startSquareP1
	 */
	/**
	 * set the start square flag for P1
	 * 
	 * @param startSquareP1
	 */
	public void setStartSquareP1(boolean startSquareP1) {
		this.startSquareP1 = startSquareP1;
	}
	/**
	 * 
	 * @return getter isStartSquare
	 */
	/**
	 * get the flag for start square P2
	 * 
	 * @return startSquareP2
	 */
	public boolean getIsStartSquareP2() {
		return startSquareP2;
	}
	/**
	 * 
	 * @param startSquareP2
	 */
	/**
	 * set the start square for P2
	 * 
	 * @param startSquareP2
	 */
	public void setStartSquareP2(boolean startSquareP2) {
		this.startSquareP2 = startSquareP2;
	}
	/**
	 * 
	 * @return
	 */
	/**
	 * get flag if move enabled after start for P1
	 * 
	 * @return isMoveEnabledAfterReturnToStartP1
	 */
	public boolean getIsMoveEnabledAfterReturnToStartP1() {
		return isMoveEnabledAfterReturnToStartP1;
	}
	/**
	 * 
	 * @param isMoveEnabledAfterReturnToStartP1
	 */
	/**
	 * set the move flag if enabled for P1
	 * 
	 * @param isMoveEnabledAfterReturnToStartP1
	 */
	public void setMoveEnabledAfterReturnToStartP1(
			boolean isMoveEnabledAfterReturnToStartP1) {
		this.isMoveEnabledAfterReturnToStartP1 = isMoveEnabledAfterReturnToStartP1;
	}
	/**
	 * 
	 * @return
	 */
	/**
	 * get the flag if move is enabled for P2
	 * 
	 * @return
	 */
	public boolean getIsMoveEnabledAfterReturnToStartP2() {
		return isMoveEnabledAfterReturnToStartP2;
	}
	/**
	 * 
	 * @param isMoveEnabledAfterReturnToStartP2
	 */
	/**
	 * set the flag if move is enabled for P2
	 * 
	 * @param isMoveEnabledAfterReturnToStartP2
	 */
	public void setMoveEnabledAfterReturnToStartP2(
			boolean isMoveEnabledAfterReturnToStartP2) {
		this.isMoveEnabledAfterReturnToStartP2 = isMoveEnabledAfterReturnToStartP2;
	}
	/**
	 * 
	 * @return
	 */
	/**
	 * get the round when P1 returned to start
	 * 
	 * @return returnToStartOnRoundP1
	 */
	public double getReturnToStartOnRoundP1() {
		return returnToStartOnRoundP1;
	}
	/**
	 * 
	 * @param returnToStartOnRoundP1
	 */
	/**
	 * set the return to start on round for P1
	 * 
	 * @param returnToStartOnRoundP1
	 */
	public void setReturnToStartOnRoundP1(double returnToStartOnRoundP1) {
		this.returnToStartOnRoundP1 = returnToStartOnRoundP1;
	}
	/**
	 * 
	 * @return
	 */
	/**
	 * get the round when P2 returned to start
	 * 
	 * @return returnToStartOnRoundP2
	 */
	public double getReturnToStartOnRoundP2() {
		return returnToStartOnRoundP2;
	}
	/**
	 * 
	 * @param returnToStartOnRoundP2
	 */
	/**
	 * set the return to start on round for P2
	 * 
	 * @param returnToStartOnRoundP2
	 */
	public void setReturnToStartOnRoundP2(double returnToStartOnRoundP2) {
		this.returnToStartOnRoundP2 = returnToStartOnRoundP2;
	}
}
