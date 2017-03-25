package dkeep.logic;

import java.awt.Point;

public class Lever {
	/**Constant value that contains the symbol of lever not activated*/
	private final char NOTACTIVATEDSYMBOL = 'k';
	/**Constant value that contains the symbol of lever activated*/
	private final char ACTIVATEDSYMBOL = 'K';
	/**Constains the position of lever*/
	private Point coords;
	/**Contains all position of doors that the lever opens*/
	private Point[] doors;

	/* CONSTRUCTOR */

	/** Constructor of Lever
	 *
	 * @param coords - Point to set the position of Lever;
	 * @param doors - Point[] - that contains all position of doors that the lever opens;
	 */
	public Lever(Point coords, Point[] doors) {
		super();
		this.coords = coords;
		this.doors = doors;
	}

	/* GETTERS */

	/** Function to get position of Lever;
	 *
	 * @return Point - that correspond the position of Lever;
	 */
	public Point getCoord() {
		return coords;
	}

	/** Function to get the position of Doors that Lever opens;
	 *
	 * @return Point[] - that contains all position of doors that the lever opens;
	 */
	public Point[] getDoors() {
		return doors;
	}

	/** Function to get symbol of lever when is not activated;
	 *
	 * @return char - that correspond to the lever symbol;
	 */
	public char getNotActivatedSymbol(){
		return NOTACTIVATEDSYMBOL;
	}

	/** Function to get symbol of lever when is activated;
	 *
	 * @return char - that correspond to the lever symbol;
	 */
	public char getActivatedSymbol() {
		return ACTIVATEDSYMBOL;
	}

}
