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

    @Override
    void nextMove() {
        movement.userMovement(agentCoords);
        if(weapon.getSymbol() != ' ') {
            weapon.setCoords(agentCoords);
            movement.randomMovement(weapon.getCoords());
        }
    }
}
