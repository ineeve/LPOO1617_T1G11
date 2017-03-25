package dkeep.logic;

import java.awt.*;

/**
 * Created by João on 24/02/2017.
 */
public class Key {
    private Point coord;
    private Point doorPos;
    
    public Key(Point coord,Point doorPos) {
        this.coord = coord;
        this.doorPos = doorPos;
    }

    public Point getCoord() {
        return coord;
    }

	public Point getDoorPos() {
		return doorPos;
	}
    
}
