package groupProject;

import java.awt.Panel;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * @author jacob_trumpis Creating the player class for the game
 */
public class Player implements PlayerAction {
	private int currentBalance;
	private int supplyCount;
	private int crewCount;
	private int securityCount;
	public int currentXPos;
	public int currentYPos;
	private LinkedList<Tile> tracks = new LinkedList<Tile>();
	private int currentRoll = 0;
	private int playerNumber;
	private int moveCount = 0;
	private int stealingPlayersRoll;
	private int playerBeingStolenFromRoll;
	private boolean isStealRollTie;
	private Logger logger = new Logger();

	/** Instance of movement */
	Movement movement = new Movement();

	/**
	 * Creates the player.
	 * 
	 * @param currentXPos
	 *            The current x coordinate for the player on the board.
	 * @param currentYPos
	 *            The current y coordinate for the player on the board.
	 * @param playerNumber
	 *            The indicator for whether it is player 1 or player 2.
	 */
	public Player(int currentXPos, int currentYPos, int playerNumber) {
		currentBalance = 75;
		this.currentXPos = currentXPos;
		this.currentYPos = currentYPos;
		this.playerNumber = playerNumber;
		this.supplyCount = 0;
		this.crewCount = 0;
		this.securityCount = 0;
	}

	/**
	 * Takes in current game board and the desired tile, moves piece to new spot
	 * if its valid, adds new tiles to tracks, and updates the GUI map
	 * 
	 * @param board
	 *            current game board
	 * @param desiredXPos
	 *            x position of desired tile
	 * @param desiredYPos
	 *            y position of desired tile
	 * @return true if piece was moved, false if piece was not moved
	 */
	@Override
	public boolean movePiece(Board board, int desiredXPos, int desiredYPos) {
		if (movement.makeMove(Player.this, board, currentRoll, desiredXPos,
				desiredYPos)) {
			tracks.add(board.getBoard()[desiredXPos][desiredYPos]);
			movement.updateGUIAfterMove(playerNumber, tracks, board);
			currentXPos = desiredXPos;
			currentYPos = desiredYPos;
			moveCount++;
			logger.logEvent("Player " + playerNumber
					+ " moved to the position (" + currentXPos + ","
					+ currentYPos + ").");
			return true;
		}
		logger.logEvent("Player " + playerNumber
				+ " attempted to move to the position (" + desiredXPos + ","
				+ desiredYPos + "), but could not.");
		return false;
	}

	/**
	 * Generates a random number between 1 and 6 and sets it equal to
	 * currentRoll
	 */
	@Override
	public void rollDice() {
		Random r = new Random();
		currentRoll = (r.nextInt(6) + 1);
		logger.logEvent("Player " + playerNumber + " rolled a " + currentRoll
				+ ".");
	}

	/**
	 * Resets the roll
	 */
	public void resetRoll() {
		currentRoll = 0;
	}

	/**
	 * Buys a single supply, then subtracts the supply cost from the player's
	 * balance
	 */
	@Override
	public void buySupplies() {
		if (currentBalance >= 1) {
			supplyCount++;
			currentBalance--;
			logger.logEvent("Player " + playerNumber + " bought a supply.");
		}
	}

	/**
	 * Buys a single crew member, then subtracts the crew member cost from the
	 * player's balance
	 */
	@Override
	public void buyCrew() {
		if (currentBalance >= 4) {
			crewCount++;
			currentBalance -= 4;
			logger.logEvent("Player " + playerNumber + " bought a crew member.");
		}
	}

	/**
	 * Buys a single security guard, then subtracts the guard cost from the
	 * player's balance
	 */
	@Override
	public void buySecurity() {
		if (currentBalance >= 2) {
			securityCount++;
			currentBalance -= 2;
			logger.logEvent("Player " + playerNumber
					+ " bought a security guard.");
		}
	}

	/**
	 * Refill method refills twice as much based on current roll
	 */
	@Override
	public void refillSupplies() {
		if (MirskyTrail.getCurrentPlayerUp().refillIsValid()) {
			MirskyTrail.getCurrentPlayerUp().rollDice();
			int x = MirskyTrail.getCurrentPlayerUp().getPlayerNumber();
			if (x == 1) {
				GUI.getSuppliesBoxPlayer1().setText(
						MirskyTrail.getCurrentPlayerUp().getSupplyCount()
								+ (2 * currentRoll) + "");
			} else {
				GUI.getSuppliesBoxPlayer2().setText(
						MirskyTrail.getCurrentPlayerUp().getSupplyCount()
								+ (2 * currentRoll) + "");
			}
			JOptionPane.showMessageDialog(null, "Rolling dice...");
			JOptionPane.showMessageDialog(null, "Rolled a "
					+ MirskyTrail.getCurrentPlayerUp().getCurrentDiceRoll()
					+ ", you've gained "
					+ (2 * MirskyTrail.getCurrentPlayerUp()
							.getCurrentDiceRoll()) + " supplies!");
			logger.logEvent("Player " + playerNumber + " refilled supplies.");

		}
	}

	/**
	 * refillIsValid checks if it is a valid refill (adjacent to refill station)
	 * 
	 * @return true if it is a valid refill, false if not adjacent to supply
	 *         station
	 */
	@Override
	public boolean refillIsValid() {
		if (currentXPos == 12) {
			if (currentYPos == 6 || currentYPos == 14)
				return true;
		}
		if (currentXPos == 13) {
			if (currentYPos == 5 || currentYPos == 7 || currentYPos == 13
					|| currentYPos == 15)
				return true;
		}
		if (currentXPos == 14) {
			if (currentYPos == 6 || currentYPos == 10 || currentYPos == 14)
				return true;
		}
		if (currentXPos == 15) {
			if (currentYPos == 9 || currentYPos == 11)
				return true;
		}
		if (currentXPos == 16) {
			if (currentYPos == 10)
				return true;
		}
		if (currentXPos == 21) {
			if (currentYPos == 1 || currentYPos == 19)
				return true;
		}
		if (currentXPos == 22) {
			if (currentYPos == 0 || currentYPos == 2 || currentYPos == 18
					|| currentYPos == 20)
				return true;
		}
		if (currentXPos == 23) {
			if (currentYPos == 1 || currentYPos == 19)
				return true;
		}
		if (currentXPos == 27) {
			if (currentYPos == 10)
				return true;
		}
		if (currentXPos == 28) {
			if (currentYPos == 9 || currentYPos == 11)
				return true;
		}
		if (currentXPos == 29) {
			if (currentYPos == 7 || currentYPos == 10 || currentYPos == 13)
				return true;
		}
		if (currentXPos == 30) {
			if (currentYPos == 6 || currentYPos == 8 || currentYPos == 12
					|| currentYPos == 14)
				return true;
		}
		if (currentXPos == 31) {
			if (currentYPos == 7 || currentYPos == 13)
				return true;
		}
		if (currentXPos == 34) {
			if (currentYPos == 1 || currentYPos == 19)
				return true;
		}
		if (currentXPos == 35) {
			if (currentYPos == 0 || currentYPos == 2 || currentYPos == 18
					|| currentYPos == 20)
				return true;
		}
		if (currentXPos == 36) {
			if (currentYPos == 1 || currentYPos == 19)
				return true;
		}
		return false;
	}

	/**
	 * Used to update resources after the player moves
	 * 
	 * @param newTile
	 *            tile player moved too
	 */
	@Override
	public void updateResourcesAfterMove(Tile newTile) {
		if (newTile.getTileType() == Tile.PLAINS) {
			supplyCount--;
		} else if (newTile.getTileType() == Tile.FOREST) {
			supplyCount--;
		} else if (newTile.getTileType() == Tile.WATER) {
			supplyCount--;
		} else if (newTile.getTileType() == Tile.MOUNTAINS) {
			supplyCount--;
		}
	}

	/**
	 * steal was clicked and will determine if player wins, loses, or ties
	 * 
	 * @param playersRolls
	 *            the roll that will be compared for both players, only used for
	 *            roll purpose
	 * @param stealingPlayer
	 *            to check for current sec guards
	 * @param opposingPlayer
	 *            to check for current sec guards
	 */
	public void attemptSteal(Player playersRolls, Player stealingPlayer,
			Player opposingPlayer) {

		rollDice();
		playersRolls.setStealingPlayersRoll(playersRolls.getCurrentDiceRoll());
		rollDice();
		playersRolls.setPlayerBeingStolenFromRoll(playersRolls
				.getCurrentDiceRoll());

		if ((playersRolls.getStealingPlayersRoll() * stealingPlayer
				.getSecurityCount()) < (playersRolls
				.getPlayerBeingStolenFromRoll() * opposingPlayer
				.getSecurityCount())) {
			JOptionPane.showMessageDialog(null,
					"You tried but...you lost a security guard!");
			logger.logEvent("Player " + playersRolls.getPlayerNumber()
					+ " tried to steal supplies, but lost a security guard.");
			stealingPlayer.securityCount--;
			playersRolls.currentRoll = 0;
			stealingPlayer.setStealRollTie(false);
		} else if ((playersRolls.getStealingPlayersRoll() * stealingPlayer
				.getSecurityCount()) > (opposingPlayer
				.getPlayerBeingStolenFromRoll() * opposingPlayer
				.getSecurityCount())) {
			rollDice();
			playersRolls.setStealingPlayersRoll(playersRolls
					.getCurrentDiceRoll());
			playersRolls.setStealingPlayersRoll((int) Math.ceil((playersRolls
					.getCurrentDiceRoll() * 0.05)));
			stealingPlayer.supplyCount += playersRolls.getStealingPlayersRoll();
			opposingPlayer.supplyCount -= playersRolls.getStealingPlayersRoll();
			JOptionPane.showMessageDialog(null,
					"You gained " + playersRolls.getStealingPlayersRoll()
							+ " supply!");
			playersRolls.currentRoll = 0;
			logger.logEvent("Player " + playersRolls.getPlayerNumber()
					+ " has successfully completed a steal operation.");
			stealingPlayer.setStealRollTie(false);
		} else {
			JOptionPane.showMessageDialog(null,
					"You tied, click steal and try again!");
			logger.logEvent("The two players tied in the steal check!");
			isStealRollTie = true;
			playersRolls.currentRoll = 0;
		}
	}

	/**
	 * Checks if stealing is a valid option for a player.
	 * 
	 * @param currentPlayer
	 *            is the player who is attempting the steal.
	 * @param victim
	 *            is the person whose supplies are being stolen.
	 * @return true if the steal is a valid option.
	 */
	@Override
	public boolean stealIsValid(Player currentPlayer, Player victim) {

		if (MirskyTrail.getRoundCount() >= 2
				&& currentPlayer.getSecurityCount() > 0
				&& victim.getSupplyCount() > 0) {
			/** Enable the steal button if it is a valid option */
			return true;
		}

		/** Disable the steal button if stealing is not a valid option */
		return false;
	}

	/**
	 * Controls the natural disasters and good fortune of each player at the
	 * beginning of their turn.
	 * 
	 * @param eventPanels
	 *            is the Panel array of images that pops up based on what the
	 *            method returns.
	 * @return the int value of the event that occurred.
	 */
	@Override
	public int randomEvents(Panel[] eventPanels) {

		int eventNumber = -1;

		// Current Player's whose turn it is.
		Player currentPlayer = MirskyTrail.getCurrentPlayerUp();

		// Random number generator.
		Random chance = new Random();

		if (MirskyTrail.getRoundCount() >= 2 && chance.nextInt(10) == 0) {

			// Options for the event.
			switch (chance.nextInt(12)) {

			/** Disasters */
			case 0:
				eventNumber = 0;
				if (currentPlayer.supplyCount >= 10) {
					currentPlayer.supplyCount -= 10;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 0) and lost 10 supplies.");
				} else {
					currentPlayer.supplyCount = 0;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 0), but had less than 10 supplies. Player now has 0 supplies.");
				}
				break;

			case 1:
				eventNumber = 1;
				if (currentPlayer.securityCount >= 5) {
					currentPlayer.securityCount -= 5;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 1) and lost 5 security guards.");
				} else {
					currentPlayer.securityCount = 0;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 1), but had less than 5 security guards. Player now has 0 guards.");
				}
				break;

			case 2:
				eventNumber = 2;
				currentPlayer.securityCount = 0;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced a disaster (event number 2) and lost all of their security guards.");
				break;
			case 3:
				eventNumber = 3;
				if (currentPlayer.supplyCount >= 5) {
					currentPlayer.supplyCount -= 5;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 3) and lost 5 supplies.");
				} else {
					currentPlayer.supplyCount = 0;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 3), but had less than 5 supplies. Player now has 0 supplies.");
				}
				break;

			case 4:
				eventNumber = 4;
				if (currentPlayer.crewCount >= 10) {
					currentPlayer.crewCount -= 10;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 4) and lost 10 crew members.");
				} else {
					currentPlayer.crewCount = 0;
					logger.logEvent("Player "
							+ playerNumber
							+ " experienced a disaster (event number 4), but had less than 10 crew members. Player now has 0 crew members.");
				}
				break;

			case 5:
				eventNumber = 5;
				currentPlayer.crewCount = 0;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced a disaster (event number 5) and lost all crew members.");
				break;

			/** Good fortune */
			case 6:
				eventNumber = 6;
				currentPlayer.supplyCount += 5;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced good fortune (event number 6) and gained 5 supply.");
				break;

			case 7:
				eventNumber = 7;
				currentPlayer.supplyCount += 2;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced good fortune (event number 7) and gained 2 supply.");
				break;

			case 8:
				eventNumber = 8;
				currentPlayer.supplyCount += 1;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced good fortune (event number 8) and gained 1 supply.");
				break;

			case 9:
				eventNumber = 9;
				currentPlayer.currentBalance = 75;
				currentPlayer.supplyCount += 25;
				currentPlayer.securityCount += 3;
				currentPlayer.crewCount += 3;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced good fortune (event number 9) and gained: 75 dollars, 25 supply, 3 security guards, and 3 crew members.");
				break;

			case 10:
				eventNumber = 10;
				currentPlayer.securityCount += 5;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced good fortune (event number 10) and gained 5 security guards.");
				break;

			case 11:
				eventNumber = 11;
				currentPlayer.crewCount += 2;
				logger.logEvent("Player "
						+ playerNumber
						+ " experienced good fortune (event number 11) and gained 2 crew members.");
				break;

			default:
				break;
			}
		}

		if (eventNumber > -1) {
			eventPanels[eventNumber].setVisible(true);
		}
		return eventNumber;
	}

	/**
	 * Returns the current x position of the players piece
	 * 
	 * @return currentXPos current x position of the players piece
	 */
	public int getCurrentXPos() {
		return currentXPos;
	}

	/**
	 * Sets the current x position of the players piece
	 * 
	 * @param x
	 *            current x position of the players piece
	 */
	public void setCurrentXPos(int x) {
		currentXPos = x;
	}

	/**
	 * Returns the current y position of the players piece
	 * 
	 * @return currentYPos current y position of the players piece
	 */
	public int getCurrentYPos() {
		return currentYPos;
	}

	/**
	 * Sets the current y position of the players piece
	 * 
	 * @param y
	 *            current y position of the players piece
	 */
	public void setCurrentYPos(int y) {
		currentYPos = y;

	}

	/**
	 * Returns player number
	 * 
	 * @return playerNumber 1 or 2 depending on which player it is
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * Returns the current player balance
	 * 
	 * @return currentBalance current player balance
	 */
	public int getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * Sets the balance of the player
	 * 
	 * @param balance
	 *            the new balance for the player
	 */
	public void setBalance(int balance) {
		currentBalance = balance;
	}

	/**
	 * Returns the current supply count
	 * 
	 * @return supplyCount current count of supplies
	 */
	public int getSupplyCount() {
		return supplyCount;
	}

	/**
	 * Returns the current crew count
	 * 
	 * @return crewCount current crew count
	 */
	public int getCrewCount() {
		return crewCount;
	}

	/**
	 * Returns the current security count
	 * 
	 * @return securityCount current security count
	 */
	public int getSecurityCount() {
		return securityCount;
	}

	/**
	 * Returns the number of moves made by player
	 * 
	 * @return moveCount number of moves made
	 */
	public int getMoveCount() {
		return moveCount;
	}

	/**
	 * Returns the current state of the tracks
	 * 
	 * @return tracks LinkedList of tracks
	 */
	public LinkedList<Tile> getTracks() {
		return tracks;
	}

	/**
	 * Returns the current roll
	 * 
	 * @return currentRoll current roll
	 */
	public int getCurrentDiceRoll() {
		return currentRoll;

	}

	/**
	 * get the stealing players roll
	 * 
	 * @return stealingPlayersRoll
	 */
	public int getStealingPlayersRoll() {
		return stealingPlayersRoll;
	}

	/**
	 * set the stealing players roll
	 * 
	 * @param stealingPlayersRoll
	 */
	public void setStealingPlayersRoll(int stealingPlayersRoll) {
		this.stealingPlayersRoll = stealingPlayersRoll;
	}

	/**
	 * gets the player being stolen from roll
	 * 
	 * @return playerBeingStolenFromRoll
	 */
	public int getPlayerBeingStolenFromRoll() {
		return playerBeingStolenFromRoll;
	}

	/**
	 * sets the player being stolen from's roll
	 * 
	 * @param playerBeingStolenFromRoll
	 */
	public void setPlayerBeingStolenFromRoll(int playerBeingStolenFromRoll) {
		this.playerBeingStolenFromRoll = playerBeingStolenFromRoll;
	}

	/**
	 * gets if steal roll tied
	 * 
	 * @return isStealRollTie
	 */
	public boolean getIsStealRollTie() {
		return isStealRollTie;
	}

	/**
	 * sets the steal rolls
	 * 
	 * @param isStealRollTie
	 */
	public void setStealRollTie(boolean isStealRollTie) {
		this.isStealRollTie = isStealRollTie;
	}
}