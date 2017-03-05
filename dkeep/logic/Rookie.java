package dkeep.logic;

import java.awt.*;

public class Rookie extends Guard {
    public Rookie() {
        symbol = 'R';
    }

    public Rookie(Point coord) {
        symbol = 'R';
        agentCoords = coord;
    }


}
