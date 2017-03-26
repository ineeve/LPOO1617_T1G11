package dkeep.logic;

import java.awt.*;

/**
 * Derived class from Guard
 * This guard moves in a different style
 */
public class Drunken extends Guard {
    /**Constant char that contains the symbol of Drunken awake*/
    private final char DRUNKENSYMBOLAWAKE = 'G';
    /**Constant char that contains the symbol of Drunken sleep*/
    private final char DRUNKENSYMBOLSLEEP = 'g';
    /**Constant value that correspond if Suspicious is going forward*/
    private final int FORWARD = 1;
    /**Constant value that correspond if Suspicious is going backward*/
    private final int BACKWARD = 0;


    /* CONSTRUCTOR */

    /** Constructor of Drunken;
     *  Initialize Drunken and set your's start position;
     *
     * @param coord - Point to set your's start position;
     */
    public Drunken(Point coord) {
        symbol = DRUNKENSYMBOLAWAKE;
        agentCoords = coord;
        isSleeping = false;
    }

    /* GETTERS */

    /** Function to get next direction to move;
     *  This function returns one char that correspond to next direction;
     *  But because this MovingAgent is a Drunken, he does not follow the movement of the original path;
     *  Sometimes the Drunken may fall asleep and when he wakes up, we may go in the backward direction.
     *
     * @return char - that contains next direction to move
     */
    @Override
	public char getNextDirection() { //returns '0' if should stay in same position
    	double random = Math.random();
        char nextChar = 0;
        if (symbol == DRUNKENSYMBOLSLEEP) {
            if (random < 0.2) {
                symbol = DRUNKENSYMBOLAWAKE;
                isSleeping = false;
                currentDirection = FORWARD;
                nextChar = movement.pathMovement(currentDirection);
            } else if (random > 0.5) {
                symbol = DRUNKENSYMBOLAWAKE;
                isSleeping = false;
                currentDirection = BACKWARD;
                nextChar = movement.pathMovement(currentDirection);
            }
        } else if (random < 0.5) {
            symbol = DRUNKENSYMBOLSLEEP;
            isSleeping = true;
        } else {
            nextChar = movement.pathMovement(currentDirection);
            }
        return nextChar;
	}

}
