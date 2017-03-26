package dkeep.logic;

import java.awt.*;

/**
 * Class that enables Hero to open some Doors to scape;
 */
public class Key {
    private Point coord;

    /* CONSTRUCTOR */

    /** Constructor of Key;
     * Initialize Key, set your's start position and how door opens;
     *
     * @param coord - Point to set start position;
     //* @param doorPos - Point to set that doors opens this key
     */
    public Key(Point coord) {
        this.coord = coord;
    }

    /* GETTERS */

    /** Function to get key position;
     *
     * @return Point - that correspond to key position;
     */
    public Point getCoord() {
        return coord;
    }

    /* SETTERS */

    /**Function to set the position of the key;
     *
     * @param coords Point - that correspond the nes position of key;
     */
    public void setCoords(Point coords){
    	coord = coords;
    }
    
}
