package groupProject;

/**
 * Creates the tiles for the Board class.
 * 
 * @author Corey Kuehl
 * @version 1
 */
public class Tile {

	/** Variables */

	private int xPos;
	private int yPos;
	private int height;
	private int width;
	private boolean isOccuppied;
	private String tileType;

	// Tile Types
	public static String PLAINS = "PLAINS";
	public static String FOREST = "FOREST";
	public static String WATER = "WATER";
	public static String MOUNTAINS = "MOUNTAINS";
	public static String SUPPLY = "SUPPLY";
	public static String START = "START";
	public static String GOAL = "GOAL";

	/**
	 * Creates a Tile.
	 * 
	 * @param x
	 *            x position of the tile.
	 * @param y
	 *            y position of the tile.
	 * @param width
	 *            width of the tile.
	 * @param height
	 *            height of the tile
	 * @param isOccuppied
	 *            checks if the tile is occupied.
	 * @param tileType
	 *            checks the type of the tile.
	 */
	public Tile(int x, int y, int width, int height, boolean isOccuppied, String tileType) {
		xPos = x;
		yPos = y;
		this.height = height;
		this.width = width;
		this.isOccuppied = isOccuppied;
		this.tileType = tileType;
	}

	/** Getters and setters */

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isOccuppied() {
		return isOccuppied;
	}

	public void setOccuppied(boolean isOccuppied) {
		this.isOccuppied = isOccuppied;
	}

	public String getTileType() {
		return tileType;
	}

	public void setTileType(String tileType) {
		this.tileType = tileType;
	}
}
