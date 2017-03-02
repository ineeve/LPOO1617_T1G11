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
                currentDirection = 1;
                movement.pathMovement(agentCoords, currentDirection);
                
            }
            else if (random > 0.5){
                symbol = 'D';
                isSleeping = false;
                currentDirection = 0;
                movement.pathMovement(agentCoords, currentDirection);
                
           }
        }
        else if (random < 0.5){
            symbol = 'd';
            isSleeping = true;
        }
        else{
            movement.pathMovement(agentCoords, currentDirection);
        }
    }
}
