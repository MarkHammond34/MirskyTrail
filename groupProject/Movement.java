package groupProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Movement {

	/**
	 * Checks if piece can be moved to new location
	 * 
	 * @param board
	 *            current game board
	 * @param currentXPos
	 *            the current x position of the player on the board
	 * @param currentYPos
	 *            the current y position of the player on the board
	 * @param desiredXPos
	 *            the x position that the player wants to move to on the board
	 * @param desiredYPos
	 *            the y position that the player wants to move to on the board
	 * @param roll
	 *            the result of the dice roll
	 * @param crewMembers
	 *            the amount of crew members the player trying to move has
	 * @param supplies
	 *            the amount of supplies that player trying to move has
	 * @param tracks
	 *            the tracks that the player currently has
	 * @return true if the player has selected a valid move, false if not
	 */
	public boolean checkIfValidMove(Board board, int currentXPos,
			int currentYPos, int desiredXPos, int desiredYPos, int roll,
			int crewMembers, int supplies, LinkedList<Tile> tracks,
			int playerNumber) {
		int xDistance = desiredXPos - currentXPos;
		int yDistance = desiredYPos - currentYPos;

		// If the player is trying to move more squares than they rolled for,
		// return false.
		if (Math.abs(xDistance) + Math.abs(yDistance) > roll) {
			return false;
		}

		// Check that the player is not trying to move on a square they cannot
		// (supply squares)
		if ((desiredXPos == 0 || desiredXPos == 15 || desiredXPos == 28)
				&& desiredYPos == 10)
			return false;

		if (desiredXPos == 13 && (desiredYPos == 6 || desiredYPos == 14))
			return false;

		if ((desiredXPos == 22 || desiredXPos == 35)
				&& (desiredYPos == 2 || desiredYPos == 19))
			return false;

		if (desiredXPos == 30 && (desiredYPos == 7 || desiredYPos == 13))
			return false;

		// Checking that the players do try to move on each other's start square
		if (playerNumber == 2 && desiredXPos == 0 && desiredYPos == 9) {
			return false;
		}

		if (playerNumber == 1 && desiredXPos == 0 && desiredYPos == 11) {
			return false;
		}

		// Retrieve the optimal path
		Tile[] optimalPath = getOptimalPath(board, currentXPos, currentYPos,
				desiredXPos, desiredYPos, tracks, playerNumber);

		/**
		 * This loops through the optimal path, and simulates the player
		 * actually moving through the path by subtracting their supplies and
		 * crew. If the path would cause either the supply count or the crew
		 * count of the player to drop below 0, then false will be returned.
		 * This also takes the player's own tracks into consideration. This does
		 * not actually subtract the player's crew or supplies.
		 */
		for (Tile t : optimalPath) {

			if (!trackContains(tracks, t, playerNumber)) {
				supplies--;
			}

			String tType = t.getTileType();
			if (!trackContains(tracks, t, playerNumber)) {
				if (tType.equals(Tile.PLAINS))
					crewMembers--;
				else if (tType.equals(Tile.FOREST))
					crewMembers -= 3;
				else if (tType.equals(Tile.WATER))
					crewMembers -= 4;
				else if (tType.equals(Tile.MOUNTAINS))
					crewMembers -= 5;
			}
		}

		if (supplies < 0 || crewMembers < 0) {
			return false;
		}

		return true;
	}

	/**
	 * @author Mark Hammond
	 * 
	 *         Method takes in players information, and makes move if it is
	 *         valid
	 * 
	 * @param board
	 *            current game board
	 * @param tracks
	 *            linked list containing the tiles that the player laid tracks
	 *            on
	 * @param roll
	 *            number representing the die roll
	 * @param desiredXPos
	 *            x position player wants to move piece too
	 * @param desiredYPos
	 *            y position player wants to move piece too
	 * @param currentXPos
	 *            current x position of players piece
	 * @param currentYPos
	 *            current y position of players piece
	 * @param crewMembers
	 *            amount of players crew members
	 * @param supplies
	 *            amount of players supplies
	 * @return true if piece was moved, false if piece was not moved
	 */
	public boolean makeMove(Player player, Board board, int roll,
			int desiredXPos, int desiredYPos) {
		// Create variables to pass into checkIfValidMove( ) so it can use them,
		// but not alter them
		int currentXPos = player.getCurrentXPos();
		int currentYPos = player.getCurrentYPos();
		int crewCount = player.getCrewCount();
		int supplyCount = player.getSupplyCount();

		// If valid move, move piece
		if (checkIfValidMove(board, currentXPos, currentYPos, desiredXPos,
				desiredYPos, roll, crewCount, supplyCount, player.getTracks(),
				player.getPlayerNumber())) {
			// Adds tiles from the path to players tracks
			Tile[] newTiles = getOptimalPath(board, player.getCurrentXPos(),
					player.getCurrentYPos(), desiredXPos, desiredYPos,
					player.getTracks(), player.getPlayerNumber());
			if (newTiles != null) {
				for (int i = 0; i < newTiles.length; i++) {
					// Checks each tile to see if its already in tracks
					if (!trackContains(player.getTracks(), newTiles[i],
							player.getPlayerNumber())) {
						// Add tile to tracks
						player.getTracks().add(newTiles[i]);
						// Player looses one of their supplies and some crew
						player.updateResourcesAfterMove(newTiles[i]);
					}
					// Roll gets decremented for every new tile
				}
				player.resetRoll();
			}
			return true;
		}
		return false;
	}

	/**
	 * @author Mark Hammond
	 * 
	 *         Method checks current tracks to see if the tile passed in is
	 *         already there
	 * 
	 * @param tracks
	 *            list of tiles
	 * @param tile
	 *            tile being searched for in tracks
	 * @return true if tracks contains tile, false if it doens't
	 */
	public boolean trackContains(LinkedList<Tile> tracks, Tile tile,
			int playerNumber) {
		for (int i = 0; i < tracks.size(); i++) {
			if (tracks.get(i).getxPos() == tile.getxPos()
					&& tracks.get(i).getyPos() == tile.getyPos()) {
				return true;
			} else if ((tile.getxPos() == 0 && tile.getyPos() == 9)) {
				if (playerNumber == 1) {
					MirskyTrail.getCurrentPlayerUp().setBalance(75);
					MirskyTrail.getControlsOfButtons()
							.setReturnToStartOnRoundP1(
									MirskyTrail.getRoundCount());
					MirskyTrail.getControlsOfButtons()
							.setMoveEnabledAfterReturnToStartP1(true);
				} else {
					return true;
				}
				return true;
			} else if ((tile.getxPos() == 0 && tile.getyPos() == 11)) {
				if (playerNumber == 2) {
					MirskyTrail.getCurrentPlayerUp().setBalance(75);
					MirskyTrail.getControlsOfButtons()
							.setReturnToStartOnRoundP2(
									MirskyTrail.getRoundCount());
					MirskyTrail.getControlsOfButtons()
							.setMoveEnabledAfterReturnToStartP2(true);
				} else {
					return true;
				}
				return true;
			}
		}
		return false;
	}

	/**
<<<<<<< .mine
=======
	 * Finds the tiles crossed when the player moves
	 * 
	 * @param oldXPos
	 *            old x coordinate
	 * @param oldYPos
	 *            old y coordinate
	 * @param newXPos
	 *            new x coordinate
	 * @param newYPos
	 *            new y coordinate
	 * @return tilePath array of tiles crossed to new location
	 */
	public Tile[] getPath(Board board, int oldXPos, int oldYPos, int newXPos,
			int newYPos) {
		int distance;
		Tile[] tilePath = null;
		// Piece was moved vertically
		if (oldXPos == newXPos && oldYPos != newYPos) {
			// Distance traveled
			distance = newYPos - oldYPos;
			tilePath = new Tile[Math.abs(distance)];
			// If piece was moved backwards
			if (distance < 0) {
				for (int i = 1; i < Math.abs(distance) + 1; i++) {
					tilePath[i - 1] = board.getBoard()[oldXPos][oldYPos - i];
				}
				// If piece was moved forward
			} else {
				for (int i = 1; i < Math.abs(distance) + 1; i++) {
					tilePath[i - 1] = board.getBoard()[oldXPos][oldYPos + i];
				}
			}
			// Piece was moved horizontally
		} else if (oldXPos != newXPos && oldYPos == newYPos) {
			// Distance traveled
			distance = newXPos - oldXPos;
			tilePath = new Tile[Math.abs(distance)];
			// If piece was moved backwards
			if (distance < 0) {
				for (int i = 1; i < Math.abs(distance) + 1; i++) {
					tilePath[i - 1] = board.getBoard()[oldXPos - i][oldYPos];
				}
				// If piece was moved forward
			} else {
				for (int i = 1; i < Math.abs(distance) + 1; i++) {
					tilePath[i - 1] = board.getBoard()[oldXPos + i][oldYPos];
				}
			}
			// Piece was not moved
		} else {
			distance = 0;
		}
		return tilePath;
	}

	/**
>>>>>>> .r321
	 * Updates the buttons with the current state of the board.
	 * 
	 * @param playerNumber
	 *            number id for the player
	 * @param tracks
	 *            linked list of tiles in the players tracks
	 */
	public void updateGUIAfterMove(int playerNumber, LinkedList<Tile> tracks,
			Board board) {

		for (int i = 0; i < tracks.size(); i++) {
			int x = tracks.get(i).getxPos();
			int y = tracks.get(i).getyPos();
			board.getBoard()[x][y].setOccuppied(true);

			// If it's player 1's tracks
			if (playerNumber == 1) {
				if (tracks.get(i).getTileType() == Tile.START) {
					MirskyTrail.getControlsOfButtons().setStartSquareP1(true);
				} else {
					MirskyTrail.getControlsOfButtons().setStartSquareP1(false);
				}
				// Sets the tracks to the track icon
				if (i < tracks.size() - 1) {
					if (tracks.get(i).getTileType() != Tile.START) {
						// If Both Players Have Tracks On Space
						if (trackContains(MirskyTrail.getCurrentPlayerNotUp()
								.getTracks(), tracks.get(i), playerNumber)) {
							GUI.getMap()[x][y].setIcon(GUI.getTrackIcons()[2]);
						} else {
							GUI.getMap()[x][y].setIcon(GUI.getTrackIcons()[0]);
						}
					}
					// Sets last new track to piece icon
				} else {
					GUI.getMap()[x][y].setIcon(GUI.getPieceIcons()[0]);
				}
				// If it's player 2's tracks
			} else {

				if (tracks.get(i).getTileType() == Tile.START) {
					MirskyTrail.getControlsOfButtons().setStartSquareP2(true);
				} else {
					MirskyTrail.getControlsOfButtons().setStartSquareP2(false);
				}
				// Sets the tracks to the track icon
				if (i < tracks.size() - 1) {
					if (tracks.get(i).getTileType() != Tile.START) {
						// If Both Players Have Tracks On Space
						if (trackContains(MirskyTrail.getCurrentPlayerNotUp()
								.getTracks(), tracks.get(i), playerNumber)) {
							GUI.getMap()[x][y].setIcon(GUI.getTrackIcons()[2]);
						} else {
							GUI.getMap()[x][y].setIcon(GUI.getTrackIcons()[1]);
						}
					}
					// Sets last new track to piece icon
				} else {
					GUI.getMap()[x][y].setIcon(GUI.getPieceIcons()[1]);
				}
			}
		}
	}

	/**
	 * Gets the optimal path to the player's desired destination from their
	 * current position.
	 * 
	 * @param board
	 *            The tiles of the game
	 * @param currentXPos
	 *            The player's current x position
	 * @param currentYPos
	 *            The player's current y position
	 * @param desiredXPos
	 *            The player's desired x position
	 * @param desiredYPos
	 *            The player's desired y position
	 * @param tracks
	 *            The tracks that the player has already laid down
	 * @param playerNum
	 *            The current player's number
	 * @return The optimal path to the player's desired location
	 */
	private Tile[] getOptimalPath(Board board, int currentXPos,
			int currentYPos, int desiredXPos, int desiredYPos,
			LinkedList<Tile> tracks, int playerNum) {

		ArrayList<Tile[]> allPaths = getAllPaths(board, currentXPos,
				currentYPos, desiredXPos, desiredYPos);

		Tile[] optimalPath = allPaths.get(0);
		int crewReq = 100;

		/**
		 * This loop goes through every possible path and sees which path will
		 * require the least amount of crew based on the tiles that are being
		 * passed through.
		 */
		for (Tile[] path : allPaths) {

			int tempReq = 0;

			for (int i = 0; i < path.length; i++) {

				String tType = path[i].getTileType();
				if (!trackContains(tracks, path[i], playerNum)) {
					if (tType.equals(Tile.PLAINS))
						tempReq++;
					else if (tType.equals(Tile.FOREST))
						tempReq += 3;
					else if (tType.equals(Tile.WATER))
						tempReq += 4;
					else if (tType.equals(Tile.MOUNTAINS))
						tempReq += 5;
					else if (tType.equals(Tile.SUPPLY))
						// This ensures that the optimal path will never include
						// a supply square
						tempReq += 10000;
				}
			}

			if (tempReq < crewReq) {
				optimalPath = path;
				crewReq = tempReq;
			}
		}

		return optimalPath;
	}

	/**
	 * This method creates all the possible paths from the player's current
	 * position to their desired position
	 * 
	 * @param board
	 *            The game board
	 * @param currentXPos
	 *            The player's current x position
	 * @param currentYPos
	 *            The player's current y position
	 * @param desiredXPos
	 *            The player's desired x position
	 * @param desiredYPos
	 *            The player's desired y position
	 * @return An array list of all the paths that can be taken to reach the
	 *         destination, for use by the getOptimalPath() method.
	 */
	private ArrayList<Tile[]> getAllPaths(Board board, int currentXPos,
			int currentYPos, int desiredXPos, int desiredYPos) {

		/**
		 * This method works by figuring out how many times the player will need
		 * to move in the up, right, down, and left directions. Then, it makes a
		 * string based off of those directions and gets all of the permutations
		 * of that string. Finally, it uses the permutations of the original
		 * string to actually create the paths that the strings represent.
		 */

		int xDistance = desiredXPos - currentXPos;
		int yDistance = desiredYPos - currentYPos;

		String directions = "";

		// Using the x distance to figure out how many times the player will
		// need to move left or right in total, then adding the directions to
		// the directions string.
		if (xDistance < 0) {
			char[] lefts = new char[xDistance * -1];
			Arrays.fill(lefts, 'L');
			directions += new String(lefts);
		} else if (xDistance > 0) {
			char[] rights = new char[xDistance];
			Arrays.fill(rights, 'R');
			directions += new String(rights);
		}

		// Using the y distance to figure out how many times the player will
		// need to move up or down in total, then adding the directions to the
		// directions string.
		if (yDistance < 0) {
			char[] ups = new char[yDistance * -1];
			Arrays.fill(ups, 'U');
			directions += new String(ups);
		} else if (yDistance > 0) {
			char[] downs = new char[yDistance];
			Arrays.fill(downs, 'D');
			directions += new String(downs);
		}

		// The variable to store permutations.
		ArrayList<String> stringPaths = new ArrayList<String>();

		/**
		 * A local class to get all of the permutations of a string
		 * 
		 * @author Robert
		 */
		class PermutationGetter {

			private ArrayList<String> stringPaths = new ArrayList<String>();

			/**
			 * Gets all the permutations of a string
			 * 
			 * @param string
			 *            The string to get permutations of
			 * @return All of the permutations of the string
			 */
			public ArrayList<String> getPermutations(String string) {
				permute("", string);
				return stringPaths;
			}

			/**
			 * A recursive method to help get permutations of a string
			 * 
			 * @param pre
			 *            The new permutation being made
			 * @param string
			 *            The remaining letters in the original string
			 */
			private void permute(String pre, String string) {
				int n = string.length();
				if (n == 0)
					stringPaths.add(pre);
				else {
					for (int i = 0; i < n; i++) {
						permute(pre + string.charAt(i), string.substring(0, i)
								+ string.substring(i + 1, n));
					}
				}
			}
		}

		PermutationGetter pGetter = new PermutationGetter();
		stringPaths = pGetter.getPermutations(directions);

		Tile[][] tiles = board.getBoard();
		int totalDistance = Math.abs(xDistance) + Math.abs(yDistance);
		ArrayList<Tile[]> tilePaths = new ArrayList<Tile[]>();

		// Creating the actual tile paths based off of the string paths
		for (String s : stringPaths) {

			Tile[] path = new Tile[totalDistance];
			int tempXPos = currentXPos;
			int tempYPos = currentYPos;

			for (int i = 0; i < path.length; i++) {
				if (s.charAt(i) == 'L') {
					path[i] = tiles[--tempXPos][tempYPos];
				} else if (s.charAt(i) == 'R') {
					path[i] = tiles[++tempXPos][tempYPos];
				} else if (s.charAt(i) == 'U') {
					path[i] = tiles[tempXPos][--tempYPos];
				} else {
					path[i] = tiles[tempXPos][++tempYPos];
				}
			}
			tilePaths.add(path);
		}

		return tilePaths;
	}
}