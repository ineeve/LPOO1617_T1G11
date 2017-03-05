package dkeep.logic;

import dkeep.cli.UserInput;
import java.awt.*;

public class Hero extends MovingAgent {
    public Hero() {
        symbol = 'H';
    }

    public Hero(Point coord) {
        symbol = 'H';
        agentCoords = coord;
        weapon.setSymbol(' ');
    }

    void nextMove() {
        char nextChar =  movement.userMovement();
        super.nextPos(nextChar);
        if(weapon.getSymbol() != ' ') {
            weapon.setCoords(agentCoords);
            nextChar = movement.randomMovement();
            weapon.nextMove(nextChar);
        }
    }
}
