package dkeep.logic;

import java.awt.*;

public class Ogre extends MovingAgent {

    public Ogre() {
        symbol = 'O';
    }

    public Ogre(Point coord) {
        symbol = 'O';
        agentCoords = coord;
    }

    @Override
    void nextMove() {
        movement.randomMovement(agentCoords);
        weapon.setCoords(agentCoords);
        movement.randomMovement(weapon.getCoords());
    }
}
