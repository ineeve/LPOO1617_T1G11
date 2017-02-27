package dkeep.logic;

import java.awt.Point;

public class Suspicious extends Guard{

    public Suspicious() {
        symbol = 'S';
    }

    public Suspicious(Point coord) {
        symbol = 'S';
        agentCoords = coord;
    }

}
