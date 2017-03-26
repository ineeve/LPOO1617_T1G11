package dkeep.logic;

import java.util.Random;

/**
 * Class responsible of movement of all MovingAgent's;
 * Except the movement of the Hero when user is playing;
 */
class MovementStrategy {
    /**Constant value that correspond if Suspicious is going forward*/
    private final int FORWARD = 1;
    /**Constant value that correspond if Suspicious is going backward*/
    private final int BACKWARD = 0;
    /**Constant array that contains the path how some MovingAgent's use to move*/
    private final char[] path = new char[]{'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w'};
    /**Contains value of status of movement, define if MovingAgent is moving in forward (1) or backward (0) in relation of path*/
    private int pathIterator = 0;

    /** Function of random movement;
     *  This is used when MovingAgent movement is random;
     *
     * @return char - that correspond to the direction of move;
     */
    public char randomMovement() {
        Random r = new Random();
        int randVal;
        randVal = r.nextInt(4);
        switch (randVal) {
            case 0:
                return 'a';
            case 1:
                return 'd';
            case 2:
                return 's';
            case 3:
                return 'w';
            default:
                return ' ';
        }
    }

    /**Function of path movement;
     * This is used when MovingAgent follow a specific path movement;
     *
     * @param direction - int that define if MovingAgent is moving FORWARD or BACKWARD;
     * @return char - that correspond to the direction of move;
     */
    public char pathMovement(int direction) {
        char nextChar = 0;
        if (direction == FORWARD) {
            if (pathIterator == path.length) {
                pathIterator--;
            }
            nextChar = path[pathIterator++];
            if (pathIterator >= path.length) {
                pathIterator = 0;
            }
        } else if (direction == BACKWARD) {
        	if (pathIterator == 0){
        		pathIterator=path.length;
        	}
            nextChar = path[--pathIterator];
            if (pathIterator <= 0) {
                pathIterator = path.length;
            }
            nextChar = reverseDirection(nextChar);
        }
       return nextChar;
    }

    /**Function that swap direction of movement;
     * EX: If movement is to right the return to left;
     *
     * @param nextChar - char thar correspond to next direction of move;
     * @return char - that correspond to the direction of move;
     */
    private char reverseDirection(char nextChar) {
        switch (nextChar) {
            case 'a':
                nextChar = 'd';
                break;
            case 's':
                nextChar = 'w';
                break;
            case 'd':
                nextChar = 'a';
                break;
            case 'w':
                nextChar = 's';
                break;
        }
        return nextChar;
    }

}
