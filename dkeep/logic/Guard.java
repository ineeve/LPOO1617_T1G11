package dkeep.logic;

import java.awt.*;

public class Guard extends MovingAgent {
    public Guard() {
        symbol = 'G';
    }

    public Guard(Point coord) {
        symbol = 'G';
        agentCoords = coord;
    }

    @Override
    void nextMove() {
        movement.pathMovement(agentCoords);
    }
}