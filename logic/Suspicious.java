package dkeep.logic;

import java.awt.*;

/**
 * Derived class from Guard
 * This guard moves in a different style
 */
public class Suspicious extends Guard {
    /**Constant char that contains the symbol of Guard*/
    private final char SUSPICIOUSSYMBOL = 'G';
    /**Constant value that correspond if Suspicious is going forward*/
    private final int FORWARD = 1;
    /**Constant value that correspond if Suspicious is going backward*/
    private final int BACKWARD = 0;
    /**Constant value that correspond to the probability of Suspicious change direction*/
    private final double CHANGEDIRECTIONPROB = 0.2;

    /* CONSTRUCTOR */

    /** Constructor of Suspicious;
     *  Initialize Drunken and set your's start position;
     *
     * @param coord - Point to set your's start position;
     */
    public Suspicious(Point coord) {
        symbol = SUSPICIOUSSYMBOL;
        agentCoords = coord;
    }

    /* GETTERS */

    /** Function to get next direction to move;
     *  This function returns one char that correspond to next direction;
     *  But because this MovingAgent is a Suspicious, he does not follow the movement of the original path;
     *  Sometimes the Suspicious may go backward to check if there is something there.
     *
     * @return char - that contains next direction to move
     */
    @Override
	public char getNextDirection() {
    	double random = Math.random();
        if (random < CHANGEDIRECTIONPROB) {
            if (currentDirection == FORWARD) {
                currentDirection = BACKWARD;
            } else
                currentDirection = FORWARD;
        }
        return movement.pathMovement(currentDirection);
	}

}
