package dkeep.logic;

import java.awt.*;

public class Hero extends MovingAgent {

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
	public char getNextDirection() {
		return movement.userMovement();
	}
	
	public char getRandomDirection(){
		return movement.randomMovement();
	}
}
