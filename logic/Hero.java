package dkeep.logic;

import java.awt.*;

/**
 * Derived class from MovingAgent;
 * The objective of this MovingAgent is scape form the Guard's;
 */
public class Hero extends MovingAgent {
    /**Constant char that contains the symbol of Hero*/
    private final char HEROSYMBOL = 'H';

    /* CONSTRUCTOR */

    /** Constructor of Hero;
     * Initialize Hero and set your's start position;
     *
     * @param coord - Point to set start position;
     */
    public Hero(Point coord) {
        symbol = HEROSYMBOL;
        agentCoords = coord;
        weapon = new Weapon(' ',new Point(0,0));
    }

    /** Construcot of Hero;
     * Initialize Hero, set your's start position, symbol and weaponSymbol;
     *
     * @param coord - Point to set start position;
     * @param symbol - char to set your's symbol;
     * @param weaponSymbol - char to set weapon symbol;
     */
    public Hero(Point coord, char symbol, char weaponSymbol){
        this.symbol = symbol;
        agentCoords = coord;
        weapon = new Weapon(weaponSymbol,new Point(0,0));
    }

    /* GETTERS */

    /** Function to get next direction to move;
     *  This function returns one char that correspond to next direction;
     *  This is only call for test, usually the Hero next direction is define by user;
     *
     * @Override MovingAgent::getNextDirection();
     *
     * @return char - that contains next direction to move
     */
	@Override
	public char getNextDirection() {
		return movement.randomMovement();
	}

}
