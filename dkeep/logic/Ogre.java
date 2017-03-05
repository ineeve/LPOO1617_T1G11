package dkeep.logic;

import java.awt.*;

public class Ogre extends MovingAgent {

    public Ogre() {
        symbol = 'O';
    }

    public Ogre(Point coord) {
        symbol = 'O';
        agentCoords = coord;
        weapon.setSymbol('*');
        weapon.setCoords(agentCoords);
    }

    void nextMove() {
        char nextChar = movement.randomMovement();
        super.nextPos(nextChar);
        if(weapon.getSymbol() != ' ') {
            weapon.setCoords(agentCoords);
            nextChar = movement.randomMovement();
            weapon.nextMove(nextChar);
        }
    }
}
