package dkeep.logic;

import java.awt.*;

/**
 * Created by João on 02/03/2017.
 */
public class Weapon {
    private Point coords;
    private char symbol;

    public Weapon() {
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
    public void nextMove(char nextChar){
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
}
