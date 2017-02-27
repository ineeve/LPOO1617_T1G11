package dkeep.logic.guards;

import java.awt.Point;

import dkeep.logic.Guard;


public class Drunken extends Guard {

    public Drunken() {
        symbol = 'D';
    }

    public Drunken(Point coord) {
        symbol = 'D';
        agentCoords = coord;
    }

    protected void nextMove() {
        double random = Math.random();
        if (symbol == 'd'){
            if (random < 0.3){
                movement.pathMovement(agentCoords, 1);
            }
            else if (random > 0.4){
                movement.pathMovement(agentCoords, 0);
           }
        }
        else if (random < 0.2){
            symbol = 'd';
        }
        else{
            movement.pathMovement(agentCoords, 1);
        }
    }
}
