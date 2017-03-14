package dkeep.logic;

import java.awt.*;

public class Drunken extends Guard {

    public Drunken() {
        symbol = 'D';
        isSleeping = false;
    }

    public Drunken(Point coord) {
        symbol = 'D';
        agentCoords = coord;
        isSleeping = false;
    }

    @Override
	char getNextDirection() { //returns '0' if should stay in same position
    	double random = Math.random();
        char nextChar = '0';
        if (symbol == 'd') {
            if (random < 0.2) {
                symbol = 'D';
                isSleeping = false;
                currentDirection = 1;
                nextChar = movement.pathMovement(currentDirection);
            } else if (random > 0.5) {
                symbol = 'D';
                isSleeping = false;
                currentDirection = 0;
                nextChar =movement.pathMovement(currentDirection);
            }
        } else if (random < 0.5) {
            symbol = 'd';
            isSleeping = true;
        } else {
            nextChar = movement.pathMovement(currentDirection);
            }
        return nextChar;
		
	}
}
