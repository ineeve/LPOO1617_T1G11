package dkeep.logic;

import java.awt.*;

class Suspicious extends Guard {

    public Suspicious(Point coord) {
        symbol = 'G';
        agentCoords = coord;
    }

    @Override
	char getNextDirection() {
    	double random = Math.random();
        if (random < 0.2) {
            if (currentDirection == 1) {
                currentDirection = 0;
            } else
                currentDirection = 1;
        }
        return movement.pathMovement(currentDirection);
		
	}

}
