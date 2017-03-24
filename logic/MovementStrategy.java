package dkeep.logic;

import java.util.Random;

/**
 * Created by João on 23/02/2017.
 */
public class MovementStrategy {

    private final char[] path = new char[]{'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w'};
    private int pathIterator = 0;

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

    /**
     * @param direction 1-Forward, 0-Backwards
     */
    public char pathMovement(int direction) {
        char nextChar = 0;
        if (direction == 1) {
            if (pathIterator == path.length) {
                pathIterator--;
            }
            nextChar = path[pathIterator++];
            if (pathIterator >= path.length) {
                pathIterator = 0;
            }
        } else if (direction == 0) {
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
