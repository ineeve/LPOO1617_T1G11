package dkeep.logic;

import java.awt.*;

public class Guard extends MovingAgent {
    
    int currentDirection;
    
    Guard() {
        symbol = 'G';
        isSleeping = false;
        currentDirection = 1;
        weapon.setSymbol(' ');
    }
    
    public Guard(Point coord) {
        symbol = 'G';
        agentCoords = coord;
        currentDirection = 1;
    }

    void nextMove() {
        char nextChar = movement.pathMovement(1);
        super.nextPos(nextChar);
        if(weapon.getSymbol() != ' ') {
            weapon.setCoords(agentCoords);
            nextChar = movement.randomMovement();
            weapon.nextMove(nextChar);
        }
    }
}
