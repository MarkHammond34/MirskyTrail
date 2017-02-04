package groupProject;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Interface for controllin the buttons.
 */
public interface ButtonController {

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
	abstract public void setTileColor(int i, int j, JButton button);

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
	abstract public boolean importGameImages(ImageIcon[] dieIcons, ImageIcon[] trackIcons, ImageIcon[] pieceIcons,
			ImageIcon[] eventImages);

	/**
	 * This method will enable/disable buttons for the respective player based
	 * on who's turn it is
	 * 
	 * @param panel
	 *            of buttons for the respective player
	 * @param isEnabled
	 *            true or false
	 */
	abstract public void changeTurns(JPanel panel, boolean isEnabled);

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
	abstract public void enableDisablePurchaseButtons(boolean startSquare, boolean moveButtonClicked, Player player,
			JButton supplies, JButton crew, JButton secGuards);

	/**
	 * This method will enable/disable the move button when round 2 begins
	 * 
	 * @param moveButton
	 *            is the move button
	 * @param roundCount
	 *            the amount of rounds that have passed
	 */
	abstract public void enableDisableMoveButton(JButton moveButton, double roundCount);

	/**
	 * This method will enable/disable steal button if it's valid
	 * 
	 * @param currentPlayer
	 *            the current player up
	 * @param victim
	 *            the player that is not up
	 * @param stealButton
	 *            the steal button
	 */
	abstract public void enableDisableStealButton(Player currentPlayer, Player victim, JButton stealButton);

	/**
	 * This method will enable/disable refill button if it's valid
	 * 
	 * @param currentPlayer
	 *            the current player up
	 * @param refillButton
	 *            is the refill button
	 */
	abstract public void enableDisableRefillButton(Player currentPlayer, JButton refillButton);

	/**
	 * This method will set the player's name and show to users
	 * 
	 * @param playerName
	 *            is the name of the player
	 * @param label
	 *            is the label being adjusted
	 * @param playerNumber
	 *            is the number of the player whose name should be assigned.
	 * @return true if the name was given correctly, false otherwise.
	 */
	abstract public boolean setPlayerName(String playerName, JLabel label, int playerNumber);

}
