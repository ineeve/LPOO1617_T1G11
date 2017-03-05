package dkeep.logic;

import java.awt.*;

public class Suspicious extends Guard {

    public Suspicious() {
        symbol = 'S';
    }

    public Suspicious(Point coord) {
        symbol = 'S';
        agentCoords = coord;
    }

    void nextMove() {
        double random = Math.random();
        if (random < 0.2) {
            if (currentDirection == 1) {
                currentDirection = 0;
            } else
                currentDirection = 1;
        }
        movement.pathMovement(agentCoords, currentDirection);
    }


}
