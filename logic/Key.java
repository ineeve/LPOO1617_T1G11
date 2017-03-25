package dkeep.logic;

import java.awt.*;

public class Key {
    private Point coord;
    private Point doorPos;

    /* CONSTRUCTOR */

    /** Constructor of Key;
     * Initialize Key, set your's start position and how door opens;
     *
     * @param coord - Point to set start position;
     * @param doorPos - Point to set that doors opens this key
     */
    public Key(Point coord,Point doorPos) {
        this.coord = coord;
        this.doorPos = doorPos;
    }

    /* GETTERS */

    /** Function to get key position;
     *
     * @return Point - that correspond to key position;
     */
    public Point getCoord() {
        return coord;
    }

    /** Function to get position of the door that key opens;
     *
     * @return Point - that correspond to position of door that key opens;
     */
	public Point getDoorPos() {
		return doorPos;
	}

}
