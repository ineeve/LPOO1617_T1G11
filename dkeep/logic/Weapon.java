package dkeep.logic;

import dkeep.cli.MovementStrategy;

import java.awt.*;

/**
 * Created by João on 02/03/2017.
 */
public class Weapon {
    private Point coords = new Point();
    private char symbol;
    private MovementStrategy movement = new MovementStrategy();

    public Weapon(char symbol,Point wepCoords){
        this.symbol = symbol;
        coords = wepCoords;
    }

    Weapon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Point getCoords() {
        return coords;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
    public void nextMove(){
        char nextChar = getNextDirection();
        switch (nextChar) {
            case 'a':
                coords.x--;
                break;
            case 'd':
                coords.x++;
                break;
            case 's':
                coords.y++;
                break;
            case 'w':
                coords.y--;
                break;
        }
    }
    public char getNextDirection(){
    	 return movement.randomMovement();
    }
    
}
