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
    }

    @Override
    void nextMove() {
        movement.randomMovement(agentCoords);
        while(weapon.getSymbol() != ' ') {
            weapon.setCoords(agentCoords);
            movement.randomMovement(weapon.getCoords());
        }
    }
}
