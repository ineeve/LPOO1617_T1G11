package dkeep.logic;

import java.awt.*;

/**
 * Abstract class that is responsible of MovingAgent;
 */
public abstract class MovingAgent {
    /**Constant object that contains all possible movements*/
    final MovementStrategy movement = new MovementStrategy();
    /**Contains the position of MovingAgent*/
    Point agentCoords; // [x,y] current coordinates
    /**Contains the symbol of MovingAgent*/
    char symbol;
    /**Contains information id MovingAgent is sleeping or not;
     * This is used just in case of Drunken
     */
    boolean isSleeping;
    /**Constains the weapon of MovingAgent*/
    public Weapon weapon;


    /* GETTERS */

    /** Function to get position of MovingAgent;
     *
     * @return Point - that correspond the position of MovingAgent;
     */
    public Point getAgentCoords(){
        return agentCoords;
    }

    /** Abstract function to get next direction to move of MovingAgent;
     *
     * @return char - that correspond the next direction of Move;
     */
    abstract char getNextDirection();

    /** Function to get the symbol of MovingAgent;
     *
     * @return char - that correspond to the symbol of MovingAgent;
     */
    public char getSymbol() {
        return symbol;
    }

    /* SETTERS */

    /** Function to set position of MovingAgent;
     *
     * @param agentCoords - Point to set the position of MovingAgent;
     */
    public void setAgentCoords(Point agentCoords) {
        this.agentCoords = agentCoords;
    }

    /** Function to set symbol of MovingAgent;
     *
     * @param newSymbol - char that correspond to new symbol of MovingAgent;
     */
    public void setSymbol(char newSymbol){
        symbol = newSymbol;
    }

    /** Function how change the position of MovingAgent;
     *
     * @param nextChar - char that correspond the direction of move;
     */
    public void nextPos(char nextChar){
        switch (nextChar) {
            case 'a':
                agentCoords.x--;
                break;
            case 'd':
                agentCoords.x++;
                break;
            case 's':
                agentCoords.y++;
                break;
            case 'w':
                agentCoords.y--;
                break;
        }
    }

}
