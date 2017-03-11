package dkeep.logic;

import java.awt.*;

public class Guard extends MovingAgent {
    
    int currentDirection;
    
    Guard() {
        symbol = 'G';
        isSleeping = false;
        currentDirection = 1;
        weapon = new Weapon (' ',agentCoords);
    }
    
    public Guard(Point coord) {
        symbol = 'G';
        agentCoords = coord;
        isSleeping = false;
        currentDirection = 1;
        weapon = new Weapon (' ',agentCoords);
    }


	@Override
	char getNextDirection() {
		return movement.pathMovement(1);
	}
}
