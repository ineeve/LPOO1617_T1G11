package dkeep.logic;

/**
 * Derived class from MovingAgent;
 * The objective of this MovingAgent is capture the Hero;
 */
class Guard extends MovingAgent {
    /**Constant char that contains the symbol of Guard*/
    private final char GUARDSYMBOL = 'G';
    /**Value that contains inforamtion about if the guard is moving forward or backward relative to the path movement*/
    int currentDirection;

    /* CONSTRUCTOR */

    /** Constructor of Guard;
     *  Initialize Guard and set weapon start position;
     *
     */
    Guard() {
        symbol = GUARDSYMBOL;
        isSleeping = false;
        currentDirection = 1;
        weapon = new Weapon (' ',agentCoords);
    }

    /* GETTERS */

    /** Function to get next direction to move;
     *  This function returns one char that correspond to next direction;
     *
     * @return char - that contains next direction to move
     */
	@Override
	char getNextDirection() {
		return movement.pathMovement(1);
	}

}
