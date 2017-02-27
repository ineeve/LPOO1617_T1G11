package dkeep.logic;

import java.awt.Point;

public class Drunken extends Guard {

    public Drunken() {
        symbol = 'D';
        isSleeping = false;
    }

    public Drunken(Point coord) {
        symbol = 'D';
        agentCoords = coord;
        isSleeping = false;
    }
   
    void nextMove() {
        double random = Math.random();
        if (symbol == 'd'){
            if (random < 0.2){
                symbol = 'D';
                isSleeping = false;
                movement.pathMovement(agentCoords, 1);
            }
            else if (random > 0.8){
                symbol = 'D';
                isSleeping = false;
                movement.pathMovement(agentCoords, 0);
           }
        }
        else if (random < 0.2){
            symbol = 'd';
            isSleeping = true;
        }
        else{
            movement.pathMovement(agentCoords, 1);
        }
    }
}
