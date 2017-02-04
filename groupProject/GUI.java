/**
 * @author Mark Hammond
 * @version 1.0
 */
package groupProject;

import java.awt.Color;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Panel;
import java.io.File;

import javax.swing.border.BevelBorder;

/**
 * User interface for the game.
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JButton[][] map = new JButton[39][21];
	private ImageIcon[] dieIcons = new ImageIcon[6];
	private static ImageIcon[] trackIcons = new ImageIcon[3];
	private static ImageIcon[] pieceIcons = new ImageIcon[2];
	private static ImageIcon[] eventImages = new ImageIcon[12];
	private static ImageIcon logoIcon = new ImageIcon("src/res/Logo.gif");
	private Panel[] eventPanels = new Panel[12];
	private Panel quitPromptPanel = new Panel();

	private JLabel lblPlayer1;
	private JLabel lblPlayer2;
	public static JLabel suppliesBoxPlayer1 = new JLabel("0");
	public static JLabel suppliesBoxPlayer2 = new JLabel("0");
	private JLabel crewBoxPlayer1 = new JLabel("0");
	private JLabel crewBoxPlayer2 = new JLabel("0");
	private JLabel securityGuardsBoxPlayer1 = new JLabel("0");
	private JLabel securityGuardsBoxPlayer2 = new JLabel("0");
	private JLabel moneyBoxPlayer1 = new JLabel("$75");
	private JLabel moneyBoxPlayer2 = new JLabel("$75");
	private JButton btnStartGame = new JButton("Start Game");
	private JPanel Player1PurchaseButtons = new JPanel();
	private JPanel Player2PurchaseButtons = new JPanel();
	private JLabel dieImage = new JLabel();
	private final JButton btnRoll = new JButton("Roll");
	private JLabel lblPlayersTurn = new JLabel("");
	private final JButton btnP2FinishedTurn = new JButton("Finish Turn");
	private final JButton btnP1FinishedTurn = new JButton("Finish Turn");
	private final JButton btnP1HireCrew = new JButton("Hire Crew Member");
	private final JButton btnP1BuySupplies = new JButton("Buy Supplies");
	private final JButton btnP1HireSecGuard = new JButton("Hire Security Guard");
	private final JButton btnP2BuySupplies = new JButton("Buy Supplies");
	private final JButton btnP2HireCrew = new JButton("Hire Crew Member");
	private final JButton btnP2HireSecGuard = new JButton("Hire Security Guard");
	private final JButton btnP1MovePlayer = new JButton("Move Player");
	private final JButton btnP2MovePlayer = new JButton("Move Player");
	private final JButton btnP1Refill = new JButton("Refill Supplies");
	private final JButton btnP2Refill = new JButton("Refill Supplies");
	private final JButton btnP1Steal = new JButton("Steal");
	private final JButton btnP2Steal = new JButton("Steal");
	private JPanel gameOpButtons = new JPanel();
	private final JButton resetGame = new JButton("Restart Game");
	private final JButton quitGame = new JButton("Quit Game");
	private boolean isPlayer1Turn = false;
	private boolean isPlayer2Turn = false;

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		setTitle("Mirsky Trail");
		setIconImage(logoIcon.getImage());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		MirskyTrail.getControlsOfButtons().importGameImages(dieIcons, trackIcons, pieceIcons, eventImages);
		createEventPanels();
		createGameOpPanels();

		// Allows for the Game to look correct on different platforms.
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/** Creating the Map */
		for (int i = 0; i < 39; i++) {
			for (int j = 0; j < 21; j++) {
				final JButton button = new JButton("");
				button.setOpaque(true);
				final int x = i;
				final int y = j;
				MirskyTrail.getControlsOfButtons().setTileColor(i, j, button);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						MirskyTrail.moveCurrentPlayer(x, y);
					}
				});
				button.setBounds(i * 18, j * 18, 18, 18);
				map[i][j] = button;
				contentPane.add(button);
			}
		}

		/** Create the Key */
		addKey();

		/** Create Player 1 Box */
		addPlayer1Box();

		/** Create Player 2 Box */
		addPlayer2Box();

		/** Create Current Player Up */
		lblPlayersTurn.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPlayersTurn.setBounds(871, 213, 125, 20);
		contentPane.add(lblPlayersTurn);

		/** Create Player 1 Purchasing Buttons */
		Player1PurchaseButtons.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Player1PurchaseButtons.setBounds(200, 388, 160, 225);
		contentPane.add(Player1PurchaseButtons);

		btnP1BuySupplies.setName("btnP1BuySupplies");
		btnP1BuySupplies.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getCurrentPlayerUp().buySupplies();
				setP1Supplies(MirskyTrail.getCurrentPlayerUp().getSupplyCount());
				setP1Money(MirskyTrail.getCurrentPlayerUp().getCurrentBalance());
				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP1(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP1BuySupplies, btnP1HireCrew, btnP1HireSecGuard);
				MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerNotUp(), btnP1Steal);
			}
		});
		Player1PurchaseButtons.add(btnP1BuySupplies);

		btnP1HireCrew.setName("btnP1BuyCrew");
		btnP1HireCrew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getCurrentPlayerUp().buyCrew();
				setP1Crew(MirskyTrail.getCurrentPlayerUp().getCrewCount());
				setP1Money(MirskyTrail.getCurrentPlayerUp().getCurrentBalance());
				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP1(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP1BuySupplies, btnP1HireCrew, btnP1HireSecGuard);
				MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerNotUp(), btnP1Steal);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP1Refill);

			}
		});
		Player1PurchaseButtons.add(btnP1HireCrew);

		btnP1HireSecGuard.setName("btnP1BuySecGuard");
		btnP1HireSecGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getCurrentPlayerUp().buySecurity();
				setP1SecurityGuards(MirskyTrail.getCurrentPlayerUp().getSecurityCount());
				setP1Money(MirskyTrail.getCurrentPlayerUp().getCurrentBalance());
				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP1(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP1BuySupplies, btnP1HireCrew, btnP1HireSecGuard);
				MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerNotUp(), btnP1Steal);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP1Refill);

			}
		});
		Player1PurchaseButtons.add(btnP1HireSecGuard);

		btnP1MovePlayer.setName("btnP1MovePlayer");
		btnP1MovePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getControlsOfButtons().setMoveButtonWasClicked(true);
				btnRoll.setEnabled(true);
				MirskyTrail.getControlsOfButtons().turnMustEnd(btnP1BuySupplies, btnP1HireCrew, btnP1HireSecGuard,
						btnP1Steal, btnP1MovePlayer, btnP1Refill, btnP1FinishedTurn);
			}
		});
		Player1PurchaseButtons.add(btnP1MovePlayer);

		/** CREATING THE STEAL BUTTON FOR PLAYER 1 */
		btnP1Steal.setName("Steal");
		btnP1Steal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getControlsOfButtons().setStealButtonWasClicked(false);
				MirskyTrail.getControlsOfButtons().turnMustEnd(btnP1BuySupplies, btnP1HireCrew, btnP1HireSecGuard,
						btnP1Steal, btnP1MovePlayer, btnP1Refill, btnP1FinishedTurn);
				MirskyTrail.getCurrentPlayerUp().attemptSteal(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerUp(), MirskyTrail.getCurrentPlayerNotUp());
				MirskyTrail.getControlsOfButtons().stealTied(btnP1Steal,
						MirskyTrail.getCurrentPlayerUp().getIsStealRollTie());
				updatePlayerTextBox();

				MirskyTrail.getControlsOfButtons().setStealButtonWasClicked(true);
			}
		});
		Player1PurchaseButtons.add(btnP1Steal);

		/** Create Refill Supplies Button for Player 1 */
		btnP1Refill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getCurrentPlayerUp().refillSupplies();
				btnP1Refill.setEnabled(true);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP1Refill);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP2Refill);

				btnP1Refill.setEnabled(false);
				btnP2Refill.setEnabled(false);
			}
		});
		Player1PurchaseButtons.add(btnP1Refill);

		/** Create Finish Turn Button for player 1 */
		btnP1FinishedTurn.setName("btnP1FinishedTurn");
		btnP1FinishedTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MirskyTrail.incrementRoundCount();

				MirskyTrail.getControlsOfButtons().changeTurns(Player1PurchaseButtons, false);
				MirskyTrail.getControlsOfButtons().changeTurns(Player2PurchaseButtons, true);

				MirskyTrail.getControlsOfButtons().setMoveButtonWasClicked(false);
				MirskyTrail.getControlsOfButtons().setRollButtonWasClicked(false);
				MirskyTrail.getControlsOfButtons().setStealButtonWasClicked(false);

				isPlayer1Turn = false;
				isPlayer2Turn = true;

				MirskyTrail.getControlsOfButtons().displayPlayersTurn(lblPlayersTurn, btnP1FinishedTurn,
						btnP2FinishedTurn);

				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP2(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP2BuySupplies, btnP2HireCrew, btnP2HireSecGuard);

				/** Good Fortune/Natural Disasters */
				MirskyTrail.getCurrentPlayerUp().randomEvents(eventPanels);
				updatePlayerTextBox();

				MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerNotUp(), btnP2Steal);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP2Refill);
				MirskyTrail.getControlsOfButtons().enableDisableMoveButton(btnP2MovePlayer,
						MirskyTrail.getRoundCount());
				MirskyTrail.getControlsOfButtons().moveEnabledAfterReturnToStart(
						MirskyTrail.getControlsOfButtons().getReturnToStartOnRoundP1(),
						MirskyTrail.getControlsOfButtons().getReturnToStartOnRoundP2(), MirskyTrail.getRoundCount(),
						MirskyTrail.getControlsOfButtons().getIsMoveEnabledAfterReturnToStartP1(),
						MirskyTrail.getControlsOfButtons().getIsMoveEnabledAfterReturnToStartP2(), btnP1MovePlayer,
						btnP2MovePlayer);
				btnRoll.setEnabled(false);
			}
		});
		Player1PurchaseButtons.add(btnP1FinishedTurn);

		// Disable all buttons before start of game
		MirskyTrail.getControlsOfButtons().changeTurns(Player1PurchaseButtons, false);

		/** Create Player 2 Purchasing Buttons */
		Player2PurchaseButtons.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Player2PurchaseButtons.setBounds(525, 388, 160, 225);
		contentPane.add(Player2PurchaseButtons);

		btnP2BuySupplies.setName("btnP2BuySupplies");
		btnP2BuySupplies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getCurrentPlayerUp().buySupplies();
				setP2Supplies(MirskyTrail.getCurrentPlayerUp().getSupplyCount());
				setP2Money(MirskyTrail.getCurrentPlayerUp().getCurrentBalance());

				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP2(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP2BuySupplies, btnP2HireCrew, btnP2HireSecGuard);
				MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerNotUp(), btnP2Steal);
			}
		});
		Player2PurchaseButtons.add(btnP2BuySupplies);

		btnP2HireCrew.setName("btnP2BuyCrew");
		btnP2HireCrew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getCurrentPlayerUp().buyCrew();
				setP2Crew(MirskyTrail.getCurrentPlayerUp().getCrewCount());
				setP2Money(MirskyTrail.getCurrentPlayerUp().getCurrentBalance());

				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP2(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP2BuySupplies, btnP2HireCrew, btnP2HireSecGuard);
			}
		});
		Player2PurchaseButtons.add(btnP2HireCrew);

		btnP2HireSecGuard.setName("btnP2BuySecGuard");
		btnP2HireSecGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getCurrentPlayerUp().buySecurity();
				setP2SecurityGuards(MirskyTrail.getCurrentPlayerUp().getSecurityCount());
				setP2Money(MirskyTrail.getCurrentPlayerUp().getCurrentBalance());

				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP2(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP2BuySupplies, btnP2HireCrew, btnP2HireSecGuard);
				MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerNotUp(), btnP2Steal);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP2Refill);

			}
		});
		Player2PurchaseButtons.add(btnP2HireSecGuard);

		btnP2MovePlayer.setName("btnP2MovePlayer");
		btnP2MovePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.getControlsOfButtons().setMoveButtonWasClicked(true);
				btnRoll.setEnabled(true);
				MirskyTrail.getControlsOfButtons().turnMustEnd(btnP2BuySupplies, btnP2HireCrew, btnP2HireSecGuard,
						btnP2Steal, btnP2MovePlayer, btnP2Refill, btnP2FinishedTurn);
			}
		});
		Player2PurchaseButtons.add(btnP2MovePlayer);

		/** CREATING THE STEAL BUTTON FOR PLAYER 2 */
		btnP2Steal.setName("Steal");
		btnP2Steal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Needs adjustments.
				/** NEED TO MOVE THIS LOGIC */
				MirskyTrail.getControlsOfButtons().turnMustEnd(btnP2BuySupplies, btnP2HireCrew, btnP2HireSecGuard,
						btnP2Steal, btnP2MovePlayer, btnP2Refill, btnP2FinishedTurn);
				MirskyTrail.getControlsOfButtons().setStealButtonWasClicked(false);
				MirskyTrail.getCurrentPlayerUp().attemptSteal(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerUp(), MirskyTrail.getCurrentPlayerNotUp());
				if (MirskyTrail.getCurrentPlayerUp().getIsStealRollTie()) {
					MirskyTrail.getControlsOfButtons().stealTied(btnP2Steal,
							MirskyTrail.getCurrentPlayerUp().getIsStealRollTie());
				}
				updatePlayerTextBox();

				MirskyTrail.getControlsOfButtons().setStealButtonWasClicked(true);
			}
		});
		Player2PurchaseButtons.add(btnP2Steal);

		/** Create Refill Supplies Button for Player 2 */
		btnP2Refill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MirskyTrail.getCurrentPlayerUp().refillSupplies();
				btnP1Refill.setEnabled(true);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP1Refill);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP2Refill);

				btnP1Refill.setEnabled(false);
				btnP2Refill.setEnabled(false);
			}
		});
		Player2PurchaseButtons.add(btnP2Refill);

		/** Create Finish Turn Button for player 2 */
		btnP2FinishedTurn.setName("btnP2FinishedTurn");
		btnP2FinishedTurn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				MirskyTrail.incrementRoundCount();

				MirskyTrail.getControlsOfButtons().changeTurns(Player1PurchaseButtons, true);
				MirskyTrail.getControlsOfButtons().changeTurns(Player2PurchaseButtons, false);

				MirskyTrail.getControlsOfButtons().setMoveButtonWasClicked(false);
				MirskyTrail.getControlsOfButtons().setRollButtonWasClicked(false);
				MirskyTrail.getControlsOfButtons().setStealButtonWasClicked(false);

				isPlayer1Turn = true;
				isPlayer2Turn = false;

				MirskyTrail.getControlsOfButtons().displayPlayersTurn(lblPlayersTurn, btnP1FinishedTurn,
						btnP2FinishedTurn);

				MirskyTrail.getControlsOfButtons().enableDisablePurchaseButtons(
						MirskyTrail.getControlsOfButtons().getIsStartSquareP1(),
						MirskyTrail.getControlsOfButtons().getIsMoveButtonWasClicked(),
						MirskyTrail.getCurrentPlayerUp(), btnP1BuySupplies, btnP1HireCrew, btnP1HireSecGuard);

				/** Good Fortune/Natural Disasters */
				MirskyTrail.getCurrentPlayerUp().randomEvents(eventPanels);

				updatePlayerTextBox();

				MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
						MirskyTrail.getCurrentPlayerNotUp(), btnP1Steal);
				MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
						btnP1Refill);
				MirskyTrail.getControlsOfButtons().enableDisableMoveButton(btnP1MovePlayer,
						MirskyTrail.getRoundCount());
				MirskyTrail.getControlsOfButtons().moveEnabledAfterReturnToStart(
						MirskyTrail.getControlsOfButtons().getReturnToStartOnRoundP1(),
						MirskyTrail.getControlsOfButtons().getReturnToStartOnRoundP2(), MirskyTrail.getRoundCount(),
						MirskyTrail.getControlsOfButtons().getIsMoveEnabledAfterReturnToStartP1(),
						MirskyTrail.getControlsOfButtons().getIsMoveEnabledAfterReturnToStartP2(), btnP1MovePlayer,
						btnP2MovePlayer);
				btnRoll.setEnabled(false);
			}
		});
		Player2PurchaseButtons.add(btnP2FinishedTurn);

		// Disable all buttons before start of game
		MirskyTrail.getControlsOfButtons().changeTurns(Player2PurchaseButtons, false);

		/** Create Roll Button and Output Label */
		btnRoll.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRoll.setBounds(806, 278, 97, 36);
		btnRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!MirskyTrail.getControlsOfButtons().getIsRollButtonWasClicked()) {
					MirskyTrail.getCurrentPlayerUp().rollDice();
					setRoll(MirskyTrail.getCurrentPlayerUp().getCurrentDiceRoll());
					MirskyTrail.getControlsOfButtons().setRollButtonWasClicked(true);
				}
			}
			/** NEED TO MOVE THIS LOGIC ABOVE THIS */
		});
		contentPane.add(btnRoll);
		btnRoll.setEnabled(false);
		btnRoll.setVisible(false);

		/** Space for die images to show up */
		dieImage.setBounds(945, 264, 64, 64);
		dieImage.setIcon(logoIcon);
		contentPane.add(dieImage);
		dieImage.setVisible(false);

		/** Add Frog GIF */
		Icon icon = new ImageIcon("src/res/frog.gif");
		final JLabel label = new JLabel(icon);
		label.setBounds(659, 320, 550, 400);
		label.setVisible(true);
		getContentPane().add(label);

		/** Create Start Game Button */
		btnStartGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStartGame.setBounds(856, 264, 150, 36);
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				/** ADD IN CHECK FOR REFILL AND MOVE BUTTON ENABLE/DISABLE */

				int firstPlayerUp = MirskyTrail.getControlsOfButtons().firstTurn(Player1PurchaseButtons,
						Player2PurchaseButtons);
				if (firstPlayerUp == 1) {
					isPlayer1Turn = true;
					isPlayer2Turn = false;
					MirskyTrail.getControlsOfButtons().enableDisableMoveButton(btnP1MovePlayer,
							MirskyTrail.getRoundCount());
					MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
							MirskyTrail.getCurrentPlayerNotUp(), btnP1Steal);
					MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
							btnP1Refill);

				} else {
					isPlayer2Turn = true;
					isPlayer1Turn = false;
					MirskyTrail.getControlsOfButtons().enableDisableMoveButton(btnP2MovePlayer,
							MirskyTrail.getRoundCount());
					MirskyTrail.getControlsOfButtons().enableDisableStealButton(MirskyTrail.getCurrentPlayerUp(),
							MirskyTrail.getCurrentPlayerNotUp(), btnP2Steal);
					MirskyTrail.getControlsOfButtons().enableDisableRefillButton(MirskyTrail.getCurrentPlayerUp(),
							btnP2Refill);

				}

				// Check which players buttons are enabled, show who's turn
				MirskyTrail.getControlsOfButtons().displayPlayersTurn(lblPlayersTurn, btnP1FinishedTurn,
						btnP2FinishedTurn);
				// once clicked, gone
				btnStartGame.setVisible(false);
				label.setVisible(false);

				// Make Quit/Restart Buttons visible
				gameOpButtons.setVisible(true);

				// Now we can play
				btnRoll.setVisible(true);
				dieImage.setVisible(true);
			}
		});
		contentPane.add(btnStartGame);

		gameOpButtons.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		gameOpButtons.setBounds(856, 400, 175, 90);
		gameOpButtons.setVisible(false);
		contentPane.add(gameOpButtons);

		/** Add Quit Game Button */
		quitGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		quitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				quitPromptPanel.setVisible(true);
			}
		});
		gameOpButtons.add(quitGame);

		/** Add Restart Game Button */
		resetGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		resetGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.resetGame();
			}
		});
		gameOpButtons.add(resetGame);
	}

	/**
	 * Sets text in roll label in GUI
	 * 
	 * @param x
	 *            roll
	 */
	public void setRoll(int x) {
		dieImage.setIcon(dieIcons[x - 1]);
	}

	/**
	 * Sets player 1's supplies in GUI
	 * 
	 * @param x
	 *            new supplies amount
	 */
	public void setP1Supplies(int x) {
		getSuppliesBoxPlayer1().setText("" + x);
	}

	/**
	 * Sets player 1's crew in GUI
	 */
	public void setP1Crew(int x) {
		crewBoxPlayer1.setText("" + x);
	}

	/**
	 * Sets player 1's security guards in GUI
	 * 
	 * @param x
	 *            new security guard amount
	 */
	public void setP1SecurityGuards(int x) {
		securityGuardsBoxPlayer1.setText("" + x);
	}

	/**
	 * Sets player 1's balance in GUI
	 * 
	 * @param x
	 *            new balance
	 */
	public void setP1Money(int x) {
		moneyBoxPlayer1.setText("$" + x);
	}

	/**
	 * Sets player 2's supplies in GUI
	 * 
	 * @param x
	 *            new supplies amount
	 */
	public void setP2Supplies(int x) {
		suppliesBoxPlayer2.setText("" + x);
	}

	/**
	 * Sets player 2's crew in GUI
	 * 
	 * @param x
	 *            new crew amount
	 */
	public void setP2Crew(int x) {
		crewBoxPlayer2.setText("" + x);
	}

	/**
	 * Sets player 2's security guards in GUI
	 * 
	 * @param x
	 *            new security guard amount
	 */
	public void setP2SecurityGuards(int x) {
		securityGuardsBoxPlayer2.setText("" + x);
	}

	/**
	 * Sets player 2's balance in GUI
	 * 
	 * @param x
	 *            new balance
	 */
	public void setP2Money(int x) {
		moneyBoxPlayer2.setText("$" + x);
	}

	/**
	 * Gets the map of JButtons
	 * 
	 * @return the map of JButtons.
	 */
	public static JButton[][] getMap() {
		return map;
	}

	/**
	 * Gets Track Icons
	 * 
	 * @return trackIcons image icons of the players tracks
	 */
	public static ImageIcon[] getTrackIcons() {
		return trackIcons;
	}

	/**
	 * Gets Piece Icons
	 * 
	 * @return pieceIcons image icons of the players pieces
	 */
	public static ImageIcon[] getPieceIcons() {
		return pieceIcons;
	}

	/**
	 * Returns true if it's player 1's turn
	 * 
	 * @return isPlayer1Turn boolean representing if it's player 1's turn
	 */
	public boolean isPlayer1Turn() {
		return isPlayer1Turn;
	}

	/**
	 * Returns true if it's player 2's turn
	 * 
	 * @return isPlayer2Turn boolean representing if it's player 2's turn
	 */
	public boolean isPlayer2Turn() {
		return isPlayer2Turn;
	}

	/**
	 * Returns the JButton for starting the game
	 * 
	 * @return btnStartGame button to start the game
	 */
	public JButton getStartButton() {
		return btnStartGame;
	}

	public JButton getBtnP1HireCrew() {
		return btnP1HireCrew;
	}

	public JButton getBtnP1BuySupplies() {
		return btnP1BuySupplies;
	}

	public JButton getBtnP1HireSecGuard() {
		return btnP1HireSecGuard;
	}

	public JButton getBtnP2BuySupplies() {
		return btnP2BuySupplies;
	}

	public JButton getBtnP2HireCrew() {
		return btnP2HireCrew;
	}

	public JButton getBtnP2HireSecGuard() {
		return btnP2HireSecGuard;
	}

	/**
	 * Adds Key Section to the GUI
	 */
	public void addKey() {

		JLabel labelKey = new JLabel("Key");
		labelKey.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelKey.setBounds(725, 30, 40, 20);
		contentPane.add(labelKey);

		JTextPane forestBox = new JTextPane();
		forestBox.setBackground(Color.GREEN);
		forestBox.setBounds(725, 61, 15, 15);
		contentPane.add(forestBox);

		JLabel lblForest = new JLabel("Forest");
		lblForest.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblForest.setBounds(746, 62, 46, 14);
		contentPane.add(lblForest);

		JTextPane mountainBox = new JTextPane();
		mountainBox.setBackground(Color.GRAY);
		mountainBox.setBounds(725, 92, 15, 15);
		contentPane.add(mountainBox);

		JLabel lblMountains = new JLabel("Mountains");
		lblMountains.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMountains.setBounds(746, 92, 75, 14);
		contentPane.add(lblMountains);

		JTextPane waterBox = new JTextPane();
		waterBox.setBackground(Color.CYAN);
		waterBox.setBounds(725, 123, 15, 15);
		contentPane.add(waterBox);

		JLabel lblWater = new JLabel("Water");
		lblWater.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWater.setBounds(746, 124, 75, 14);
		contentPane.add(lblWater);

		JTextPane plainBox = new JTextPane();
		plainBox.setBackground(Color.LIGHT_GRAY);
		plainBox.setBounds(725, 154, 15, 15);
		contentPane.add(plainBox);

		JLabel lblPlain = new JLabel("Plains");
		lblPlain.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlain.setBounds(746, 155, 75, 14);
		contentPane.add(lblPlain);

		JTextPane supplyStationBox = new JTextPane();
		supplyStationBox.setBackground(Color.MAGENTA);
		supplyStationBox.setBounds(725, 185, 15, 15);
		contentPane.add(supplyStationBox);

		JLabel lblSupplyStation = new JLabel("Supply Station");
		lblSupplyStation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSupplyStation.setBounds(746, 186, 75, 14);
		contentPane.add(lblSupplyStation);
	}

	/**
	 * Adds Player 1's Section to the GUI
	 */
	public void addPlayer1Box() {

		lblPlayer1 = new JLabel("Player 1");
		lblPlayer1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPlayer1.setBounds(50, 390, 84, 20);
		contentPane.add(lblPlayer1);

		JLabel lblPlayer1Supplies = new JLabel("Supplies");
		lblPlayer1Supplies.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer1Supplies.setBounds(50, 415, 57, 20);
		contentPane.add(lblPlayer1Supplies);

		getSuppliesBoxPlayer1().setFont(new Font("Tahoma", Font.PLAIN, 14));
		getSuppliesBoxPlayer1().setBounds(160, 415, 57, 20);
		contentPane.add(getSuppliesBoxPlayer1());

		JLabel lblPlayer1Crew = new JLabel("Crew");
		lblPlayer1Crew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer1Crew.setBounds(50, 440, 57, 20);
		contentPane.add(lblPlayer1Crew);

		crewBoxPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		crewBoxPlayer1.setBounds(160, 440, 57, 20);
		contentPane.add(crewBoxPlayer1);

		JLabel lblPlayer1SecurityGuards = new JLabel("Security Guards");
		lblPlayer1SecurityGuards.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer1SecurityGuards.setBounds(50, 465, 103, 20);
		contentPane.add(lblPlayer1SecurityGuards);

		securityGuardsBoxPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		securityGuardsBoxPlayer1.setBounds(160, 465, 57, 20);
		contentPane.add(securityGuardsBoxPlayer1);

		JLabel lblPlayer1Money = new JLabel("Money");
		lblPlayer1Money.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer1Money.setBounds(50, 490, 103, 20);
		contentPane.add(lblPlayer1Money);

		moneyBoxPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		moneyBoxPlayer1.setBounds(160, 490, 57, 20);
		contentPane.add(moneyBoxPlayer1);
	}

	/**
	 * Adds Player 2's Section to the GUI
	 */
	public void addPlayer2Box() {

		lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPlayer2.setBounds(375, 390, 84, 20);
		contentPane.add(lblPlayer2);

		JLabel lblPlayer2Supplies = new JLabel("Supplies");
		lblPlayer2Supplies.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer2Supplies.setBounds(375, 415, 57, 20);
		contentPane.add(lblPlayer2Supplies);

		suppliesBoxPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		suppliesBoxPlayer2.setBounds(485, 415, 57, 20);
		contentPane.add(suppliesBoxPlayer2);

		JLabel lblPlayer2Crew = new JLabel("Crew");
		lblPlayer2Crew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer2Crew.setBounds(375, 440, 57, 20);
		contentPane.add(lblPlayer2Crew);

		crewBoxPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		crewBoxPlayer2.setBounds(485, 440, 57, 20);
		contentPane.add(crewBoxPlayer2);

		JLabel lblPlayer2SecurityGuards = new JLabel("Security Guards");
		lblPlayer2SecurityGuards.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer2SecurityGuards.setBounds(375, 465, 103, 20);
		contentPane.add(lblPlayer2SecurityGuards);

		securityGuardsBoxPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		securityGuardsBoxPlayer2.setBounds(485, 465, 57, 20);
		contentPane.add(securityGuardsBoxPlayer2);

		JLabel lblPlayer2Money = new JLabel("Money");
		lblPlayer2Money.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlayer2Money.setBounds(375, 490, 103, 20);
		contentPane.add(lblPlayer2Money);

		moneyBoxPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		moneyBoxPlayer2.setBounds(485, 490, 57, 20);
		contentPane.add(moneyBoxPlayer2);
	}

	/**
	 * Sets the display of each player's inventory to the correct text.
	 */
	public void updatePlayerTextBox() {
		Player currentPlayer = MirskyTrail.getCurrentPlayerUp();
		Player notCurrentPlayer = MirskyTrail.getCurrentPlayerNotUp();

		if (currentPlayer.getPlayerNumber() == 1) {
			// Update Player 1 Supplies
			setP1Supplies(currentPlayer.getSupplyCount());
			setP1Crew(currentPlayer.getCrewCount());
			setP1SecurityGuards(currentPlayer.getSecurityCount());
			setP1Money(currentPlayer.getCurrentBalance());
			// Update Player 2 Supplies
			setP2Supplies(notCurrentPlayer.getSupplyCount());
			setP2Crew(notCurrentPlayer.getCrewCount());
			setP2SecurityGuards(notCurrentPlayer.getSecurityCount());
			setP2Money(notCurrentPlayer.getCurrentBalance());
		} else {
			// Update Player 2 Supplies
			setP2Supplies(currentPlayer.getSupplyCount());
			setP2Crew(currentPlayer.getCrewCount());
			setP2SecurityGuards(currentPlayer.getSecurityCount());
			setP2Money(currentPlayer.getCurrentBalance());
			// Update Player 1 Supplies
			setP1Supplies(notCurrentPlayer.getSupplyCount());
			setP1Crew(notCurrentPlayer.getCrewCount());
			setP1SecurityGuards(notCurrentPlayer.getSecurityCount());
			setP1Money(notCurrentPlayer.getCurrentBalance());
		}
	}

	/**
	 * Creates the the game panels
	 */
	public void createGameOpPanels() {

		/** Create Quit Prompt Panel */
		quitPromptPanel.setBackground(Color.white);
		quitPromptPanel.setSize(250, 100);
		quitPromptPanel.setLocation(450, 150);

		JLabel quitQuestionTitle = new JLabel("Are You Sure?");
		quitQuestionTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		quitPromptPanel.add(quitQuestionTitle);

		JButton yesButton = new JButton("Yes");
		yesButton.setBackground(Color.lightGray);
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MirskyTrail.closeGame();
			}
		});
		quitPromptPanel.add(yesButton);

		JButton noButton = new JButton("No");
		noButton.setBackground(Color.lightGray);
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quitPromptPanel.setVisible(false);
			}
		});
		quitPromptPanel.add(noButton);
		quitPromptPanel.setVisible(false);
		contentPane.add(quitPromptPanel);
	}

	/**
	 * Creates the Panels that display when an event occurs
	 */
	public void createEventPanels() {

		// STORM (EVENT 0)
		final Panel stormPanel = new Panel();
		stormPanel.setBackground(Color.white);
		stormPanel.setSize(635, 460);
		stormPanel.setLocation(240, 80);

		JLabel stormTitle = new JLabel("     A STORM ROLLS THROUGH!    ");
		stormTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		stormPanel.add(stormTitle);

		JButton stormExitButton = new JButton("Close");
		stormExitButton.setBackground(Color.lightGray);
		stormExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stormPanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " loses 10 supplies!");
			}
		});
		stormPanel.add(stormExitButton);

		JLabel stormImage = new JLabel(eventImages[0]);
		stormPanel.add(stormImage);

		JLabel stormResult = new JLabel("LOSE 10 SUPPLIES!");
		stormResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		stormPanel.add(stormResult);

		stormPanel.setVisible(false);
		eventPanels[0] = stormPanel;
		contentPane.add(stormPanel);

		// TSUNAMI (EVENT 1)
		final Panel tsunamiPanel = new Panel();
		tsunamiPanel.setBackground(Color.white);
		tsunamiPanel.setSize(665, 475);
		tsunamiPanel.setLocation(240, 80);

		JLabel tsunamiTitle = new JLabel("       TSUNAMI!     ");
		tsunamiTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		tsunamiPanel.add(tsunamiTitle);

		JButton tsunamiExitButton = new JButton("Close");
		tsunamiExitButton.setBackground(Color.lightGray);
		tsunamiExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tsunamiPanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " loses 5 security guards!");
			}
		});
		tsunamiPanel.add(tsunamiExitButton);

		JLabel tsunamiImage = new JLabel(eventImages[1]);
		tsunamiPanel.add(tsunamiImage);

		JLabel tsunamiResult = new JLabel("5 SECURITY GUARDS KILLED!");
		tsunamiResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		tsunamiPanel.add(tsunamiResult);

		tsunamiPanel.setVisible(false);
		eventPanels[1] = tsunamiPanel;
		contentPane.add(tsunamiPanel);

		// GRASP (EVENT 2)
		final Panel graspPanel = new Panel();
		graspPanel.setBackground(Color.white);
		graspPanel.setSize(500, 630);
		graspPanel.setLocation(330, 10);

		JLabel graspTitle1 = new JLabel("CONFUSES ADVANCED CACHE OPTIMIZATIONS");
		graspTitle1.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 18));
		graspPanel.add(graspTitle1);

		JLabel graspTitle2 = new JLabel("     WITH GRASP PATTERNS!   ");
		graspTitle2.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 18));
		graspPanel.add(graspTitle2);

		JButton graspExitButton = new JButton("Close");
		graspExitButton.setBackground(Color.lightGray);
		graspExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graspPanel.setVisible(false);
				JOptionPane.showMessageDialog(null, "Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber()
						+ " loses all of their security guards!");
			}
		});
		graspPanel.add(graspExitButton);

		JLabel graspImage = new JLabel(eventImages[2]);
		graspPanel.add(graspImage);

		JLabel graspResult = new JLabel("LOSE ALL YOUR SECURITY GUARDS!");
		graspResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 20));
		graspPanel.add(graspResult);

		graspPanel.setVisible(false);
		eventPanels[2] = graspPanel;
		contentPane.add(graspPanel);

		// SNAKE (EVENT 3)
		final Panel snakePanel = new Panel();
		snakePanel.setBackground(Color.white);
		snakePanel.setSize(460, 630);
		snakePanel.setLocation(330, 10);

		JLabel snakeTitle = new JLabel("TRIPPED ON A SNAKE!");
		snakeTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		snakePanel.add(snakeTitle);

		JButton snakeExitButton = new JButton("Close");
		snakeExitButton.setBackground(Color.lightGray);
		snakeExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				snakePanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " loses 5 supplies!");
			}
		});
		snakePanel.add(snakeExitButton);

		JLabel snakeImage = new JLabel(eventImages[3]);
		snakePanel.add(snakeImage);

		JLabel snakeResult = new JLabel("LOSE 5 SUPPLIES");
		snakeResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		snakePanel.add(snakeResult);

		snakePanel.setVisible(false);
		eventPanels[3] = snakePanel;
		contentPane.add(snakePanel);

		// QUIZ (EVENT 4)
		final Panel quizPanel = new Panel();
		quizPanel.setBackground(Color.white);
		quizPanel.setSize(470, 620);
		quizPanel.setLocation(330, 10);

		JLabel quizTitle = new JLabel("       FAILED A QUIZ!     ");
		quizTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		quizPanel.add(quizTitle);

		JButton quizExitButton = new JButton("Close");
		quizExitButton.setBackground(Color.lightGray);
		quizExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quizPanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " loses 10 crew members!");
			}
		});
		quizPanel.add(quizExitButton);

		JLabel quizImage = new JLabel(eventImages[4]);
		quizPanel.add(quizImage);

		JLabel quizResult = new JLabel("LOSE 10 CREW!");
		quizResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		quizPanel.add(quizResult);

		quizPanel.setVisible(false);
		eventPanels[4] = quizPanel;
		contentPane.add(quizPanel);

		// GRAMMER (EVENT 5)
		final Panel grammerPanel = new Panel();
		grammerPanel.setBackground(Color.white);
		grammerPanel.setSize(500, 430);
		grammerPanel.setLocation(320, 80);

		JLabel grammerTitle = new JLabel("MADE A GRAMMER ERROR!   ");
		grammerTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 26));
		grammerPanel.add(grammerTitle);

		JButton grammerExitButton = new JButton("Close");
		grammerExitButton.setBackground(Color.lightGray);
		grammerExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				grammerPanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " loses all of their crew!");
			}
		});
		grammerPanel.add(grammerExitButton);

		JLabel grammerImage = new JLabel(eventImages[5]);
		grammerPanel.add(grammerImage);

		JLabel grammerResult = new JLabel("THE MIRSKY MONSTER KILLED");
		grammerResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 26));
		grammerPanel.add(grammerResult);

		JLabel grammerResult2 = new JLabel("ALL YOUR CREW!");
		grammerResult2.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 26));
		grammerPanel.add(grammerResult2);

		grammerPanel.setVisible(false);
		eventPanels[5] = grammerPanel;
		contentPane.add(grammerPanel);

		// ACE (EVENT 6)
		final Panel acePanel = new Panel();
		acePanel.setBackground(Color.white);
		acePanel.setSize(470, 620);
		acePanel.setLocation(330, 10);

		JLabel aceTitle = new JLabel("   ACED A QUIZ!     ");
		aceTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		acePanel.add(aceTitle);

		JButton aceExitButton = new JButton("Close");
		aceExitButton.setBackground(Color.lightGray);
		aceExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				acePanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " gains 5 supplies!");
			}
		});
		acePanel.add(aceExitButton);

		JLabel aceImage = new JLabel(eventImages[6]);
		acePanel.add(aceImage);

		JLabel aceResult = new JLabel("GAIN 5 SUPPLIES!");
		aceResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		acePanel.add(aceResult);

		acePanel.setVisible(false);
		eventPanels[6] = acePanel;
		contentPane.add(acePanel);

		// CAMP (EVENT 7)
		final Panel campPanel = new Panel();
		campPanel.setBackground(Color.white);
		campPanel.setSize(650, 470);
		campPanel.setLocation(280, 75);

		JLabel campTitle = new JLabel("FOUND AN ABANDONED CAMP!  ");
		campTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		campPanel.add(campTitle);

		JButton campExitButton = new JButton("Close");
		campExitButton.setBackground(Color.lightGray);
		campExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				campPanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " gains 2 supplies!");
			}
		});
		campPanel.add(campExitButton);

		JLabel campImage = new JLabel(eventImages[7]);
		campPanel.add(campImage);

		JLabel campResult = new JLabel("GAIN 2 SUPPLIES!");
		campResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		campPanel.add(campResult);

		campPanel.setVisible(false);
		eventPanels[7] = campPanel;
		contentPane.add(campPanel);

		// GAME (EVENT 8)
		final Panel gamePanel = new Panel();
		gamePanel.setBackground(Color.white);
		gamePanel.setSize(650, 550);
		gamePanel.setLocation(280, 25);

		JLabel gameTitle = new JLabel("WON A GAME OF ROCK PAPER SCISSORS!");
		gameTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 26));
		gamePanel.add(gameTitle);

		JButton gameExitButton = new JButton("Close");
		gameExitButton.setBackground(Color.lightGray);
		gameExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamePanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " gains 1 piece of supplies!");
			}
		});
		gamePanel.add(gameExitButton);

		JLabel gameImage = new JLabel(eventImages[8]);
		gamePanel.add(gameImage);

		JLabel gameResult = new JLabel("     GAIN 1 SUPPLY!      ");
		gameResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 26));
		gamePanel.add(gameResult);

		gamePanel.setVisible(false);
		eventPanels[8] = gamePanel;
		contentPane.add(gamePanel);
		//

		// LOTTERY (EVENT 9)
		final Panel lotteryPanel = new Panel();
		lotteryPanel.setBackground(Color.white);
		lotteryPanel.setSize(650, 550);
		lotteryPanel.setLocation(280, 25);

		JLabel lotteryTitle = new JLabel("WON THE LOTTERY! ");
		lotteryTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 30));
		lotteryPanel.add(lotteryTitle);

		JButton lotteryExitButton = new JButton("Close");
		lotteryExitButton.setBackground(Color.lightGray);
		lotteryExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lotteryPanel.setVisible(false);
				JOptionPane.showMessageDialog(null, "Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber()
						+ "'s money is reset to $75, gains 25 supplies, 3 security guards, and 3 crew members!");
			}
		});
		lotteryPanel.add(lotteryExitButton);

		JLabel lotteryImage = new JLabel(eventImages[9]);
		lotteryPanel.add(lotteryImage);

		lotteryPanel.setVisible(false);
		eventPanels[9] = lotteryPanel;
		contentPane.add(lotteryPanel);

		// TEAM (EVENT 10)
		final Panel teamPanel = new Panel();
		teamPanel.setBackground(Color.white);
		teamPanel.setSize(670, 550);
		teamPanel.setLocation(280, 25);

		JLabel teamTitle = new JLabel("TEAM GETS CHECKPOINT DONE ON TIME!");
		teamTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 28));
		teamPanel.add(teamTitle);

		JButton teamExitButton = new JButton("Close");
		teamExitButton.setBackground(Color.lightGray);
		teamExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				teamPanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " gains 5 security guards!");
			}
		});
		teamPanel.add(teamExitButton);

		JLabel teamImage = new JLabel(eventImages[10]);
		teamPanel.add(teamImage);

		JLabel teamResult = new JLabel("GAIN 5 SECURITY GUARDS!");
		teamResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 28));
		teamPanel.add(teamResult);

		teamPanel.setVisible(false);
		eventPanels[10] = teamPanel;
		contentPane.add(teamPanel);
		//

		// TOY (EVENT 11)
		final Panel toyPanel = new Panel();
		toyPanel.setBackground(Color.white);
		toyPanel.setSize(670, 510);
		toyPanel.setLocation(280, 25);

		JLabel toyTitle = new JLabel("FOUND A TOY IN A CEREAL BOX!");
		toyTitle.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 28));
		toyPanel.add(toyTitle);

		JButton toyExitButton = new JButton("Close");
		toyExitButton.setBackground(Color.lightGray);
		toyExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toyPanel.setVisible(false);
				JOptionPane.showMessageDialog(null,
						"Player " + MirskyTrail.getCurrentPlayerUp().getPlayerNumber() + " gains 2 crew members!");
			}
		});
		toyPanel.add(toyExitButton);

		JLabel toyImage = new JLabel(eventImages[11]);
		toyPanel.add(toyImage);

		JLabel toyResult = new JLabel("GAIN 2 CREW MEMBERS!");
		toyResult.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 28));
		toyPanel.add(toyResult);

		toyPanel.setVisible(false);
		eventPanels[11] = toyPanel;
		contentPane.add(toyPanel);
		//

	}

	/**
	 * Plays the train sound
	 * 
	 * @param sound is the sound returned
	 */
	public void playTrainSound() {
		File trainSound = new File("src/res/TrainSound.wav");
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(trainSound));
			clip.start();

			Thread.sleep(clip.getMicrosecondLength() / 1000000);
		} catch (Exception e) {

		}
	}

	/**
	 * @return the suppliesBoxPlayer1
	 */
	public static JLabel getSuppliesBoxPlayer1() {
		return suppliesBoxPlayer1;
	}

	/**
	 * @return the suppliesBoxPlayer2
	 */
	public static JLabel getSuppliesBoxPlayer2() {
		return suppliesBoxPlayer2;
	}

	/**
	 * @param suppliesBoxPlayer1
	 *            the suppliesBoxPlayer1 to set
	 */
	public static void setSuppliesBoxPlayer1(JLabel suppliesBoxPlayer1) {
		GUI.suppliesBoxPlayer1 = suppliesBoxPlayer1;
	}

	/**
	 * @param suppliesBoxPlayer2
	 *            the suppliesBoxPlayer2 to set
	 */
	public static void setSuppliesBoxPlayer2(JLabel suppliesBoxPlayer2) {
		GUI.suppliesBoxPlayer2 = suppliesBoxPlayer2;
	}

	/**
	 * Gets the panel
	 * 
	 * @return the panel
	 */
	public Panel[] getEventPanels() {
		return eventPanels;
	}
}