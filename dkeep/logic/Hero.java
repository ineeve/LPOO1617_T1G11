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
        weapon = new Weapon(' ',new Point(0,0));
        key = false;
    }
    public Hero(Point coord, char symbol, char weaponSymbol){
        this.symbol = symbol;
        agentCoords = coord;
        weapon = new Weapon(weaponSymbol,new Point(0,0));
        key = false;
    }

    @Override
    void nextMove() {
        char nextChar =  movement.userMovement();
        super.nextPos(nextChar);
    }
}
