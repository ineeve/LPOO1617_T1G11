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
        weapon.setCoords((Point) agentCoords.clone());
        weapon.getCoords().x--;
    }

    @Override
    void nextMove() {
        char nextChar = movement.randomMovement();
        super.nextPos(nextChar);
    }
}
