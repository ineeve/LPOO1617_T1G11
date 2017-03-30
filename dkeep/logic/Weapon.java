package dkeep.logic;

import java.awt.*;

/**
 * Class responsible of Weapons of MovingAgent;
 */
public class Weapon {
    /**Contains the position of Weapon*/
    private Point coords = new Point();
    /**Constains the symbol of Weapon*/
    private char symbol;
    /**Constant object that contains all possible movements*/
    private final MovementStrategy movement = new MovementStrategy();


    /* CONSTRUCTOR */

    /** Constructor of Weapon
     *  This set the start position of Weapon and he symbol;
     *
     * @param symbol - char that correspond to the symbol of Weapon;
     * @param wepCoords - Point thar correspond to the the start position of Weapon;
     */
    public Weapon(char symbol,Point wepCoords){
        this.symbol = symbol;
        coords = wepCoords;
    }


    /* GETTERS */

    /** Function to get position of Weapon;
     *
     * @return Point - that correspond to the position of Weapon;
     */
    public Point getCoords() {
        return coords;
    }

    /** Function to get the symbol of Weapon;
     *
     * @return char - that correspond to the symbol of weapon;
     */
    public char getSymbol() {
        return symbol;
    }

    /** Function to get next weapon movement;
     *  The movement of weapon is random;
     *
     * @return char - with a direction of the movement;
     */
    public char getNextDirection(){
        return movement.randomMovement();
    }


    /* SETTERS */

    /** Function to set position of Weapon;
     *
     * @param coords - Point the contains the position to set in Weapon;
     */
    public void setCoords(Point coords) {
        this.coords = coords;
    }

    /** Function to set the symbol of weapon
     *
     * @param symbol char - that correspond to the symbol to set;
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }


    /** Function to change the position of Weapon;
     *  Change the position depended of value return of getNextDirection();
     */
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

}
