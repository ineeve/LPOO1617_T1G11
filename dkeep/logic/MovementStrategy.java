package dkeep.logic;

import java.awt.*;
import java.util.Random;

import static dkeep.cli.UserInput.*;

/**
 * Created by João on 23/02/2017.
 */
class MovementStrategy {

    private final char[] path = new char[]{'a', 's', 's', 's', 's', 'a', 'a', 'a', 'a', 'a', 'a', 's', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'w', 'w', 'w', 'w', 'w'};
    private int pathIterator = 0;

    public void userMovement(Point coord) {
        char nextChar = getChar();
        switch (nextChar) {
            case 'a':
                coord.x--;
                break;
            case 'd':
                coord.x++;
                break;
            case 's':
                coord.y++;
                break;
            case 'w':
                coord.y--;
                break;
        }
    }

    public void randomMovement(Point coord) {
        Random r = new Random();
        int randVal;
        randVal = r.nextInt(4);
        switch (randVal) {
            case 0:
                coord.x--;
                break;
            case 1:
                coord.x++;
                break;
            case 2:
                coord.y++;
                break;
            case 3:
                coord.y--;
                break;
        }
    }

    /**
     * @param coord coords of movingagent
     * @param direction 1-Forward, 0-Backwards
     */
    public void pathMovement(Point coord, int direction) {
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
            nextChar = path[--pathIterator];
            if (pathIterator <= 0) {
                pathIterator = path.length;
            }
            nextChar = reverseDirection(nextChar);
        }

        switch (nextChar) {
            case 'a':
                coord.x--;
                break;
            case 'd':
                coord.x++;
                break;
            case 's':
                coord.y++;
                break;
            case 'w':
                coord.y--;
                break;
        }
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
