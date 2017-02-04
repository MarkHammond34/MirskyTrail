package groupProject;

import java.awt.Panel;

/**
 * Interface for the actions of the player.
 */
public interface PlayerAction {

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
	abstract public boolean movePiece(Board board, int desiredXPos, int desiredYPos);

	/**
	 * Generates a random number between 1 and 6 and sets it equal to
	 * currentRoll
	 */
	abstract public void rollDice();

	/**
	 * Buys a single supply, then subtracts the supply cost from the player's
	 * balance
	 */
	abstract public void buySupplies();

	/**
	 * Buys a single crew member, then subtracts the crew member cost from the
	 * player's balance
	 */
	abstract public void buyCrew();

	/**
	 * Buys a single security guard, then subtracts the guard cost from the
	 * player's balance
	 */
	abstract public void buySecurity();

	/**
	 * Refills twice as much as current roll
	 */
	abstract public void refillSupplies();

	/**
	 * Checks if it is a valid refill (adjacent to refill station)
	 * 
	 * @return true if it is a valid refill, false if not adjacent to supply
	 *         station
	 */
	abstract public boolean refillIsValid();

	/**
	 * Used to update resources after the player moves
	 * 
	 * @param newTile
	 *            tile player moved too
	 */
	abstract public void updateResourcesAfterMove(Tile newTile);

	/**
	 * steal was clicked and will determine if player wins, loses, or ties
	 * 
	 * @param playersRolls
	 *            the roll that will be compared for both players
	 * @param stealingPlayer
	 *            to check for current sec guards
	 * @param opposingPlayer
	 *            to check for current sec guards
	 */
	abstract public void attemptSteal(Player playersRolls, Player stealingPlayer, Player opposingPlayer);

	/**
	 * Checks if stealing is a valid option for a player.
	 * 
	 * @param currentPlayer
	 *            is the player who is attempting the steal.
	 * @param victim
	 *            is the person whose supplies are being stolen.
	 * @return true if the steal is a valid option.
	 */
	abstract public boolean stealIsValid(Player currentPlayer, Player victim);

	/**
	 * Controls the natural disasters and good fortune of each player at the
	 * beginning of their turn.
	 * 
	 * @param eventPanels
	 *            is the Panel array of images that pops up based on what the
	 *            method returns.
	 * @return the int value of the event that occurred.
	 */
	abstract public int randomEvents(Panel[] eventPanels);
}
