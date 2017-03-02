package dkeep.logic;

import java.awt.*;

public class Guard extends MovingAgent {
    
    int currentDirection;
    
    Guard() {
        symbol = 'G';
        isSleeping = false;
        currentDirection = 1;
    }
    
    public Guard(Point coord) {
        symbol = 'G';
        agentCoords = coord;
        currentDirection = 1;
    }
    
    @Override
    void nextMove() {
        movement.pathMovement(agentCoords, 1);
        weapon.setCoords(agentCoords);
        movement.randomMovement(weapon.getCoords());
    }
}
