package dkeep.logic;

import java.awt.*;

/**
 * Created by João on 24/02/2017.
 */
public class Key {
    private Point coord;
    
    public Key(Point coord) {
        this.coord = coord;
    }

    public Point getCoord() {
        return coord;
    }
    public void setCoords(Point coords){
    	coord = coords;
    }
    
}
