package dkeep.logic;

import java.awt.*;

/**
 * Derived class from MovingAgent;
 * The objective of this MovingAgent is capture the Hero;
 */
public class Ogre extends MovingAgent {
    /**Value that contains the number of rounds that remains to the ogre to get out of stun*/
    private int roundsStunned;

    /* CONSTRUCTOR */

    /** Constructor of Ogre;
     *
     * @param coord - Point to set the start position of Ogre;
     */
    public Ogre(Point coord) {
        symbol = 'O';
        agentCoords = coord;
        weapon = new Weapon('*',agentCoords);
        weapon.setCoords((Point) agentCoords.clone());
        roundsStunned = 0;
    }

    /* GETTERS */

    /** Function to get next direction of movement of the Ogre;
     *
     * @return char - that correspond to the next direction of move;
     */
    @Override
    public char getNextDirection() {
        return  movement.randomMovement();
    }

    /** Function to get status of stun;
     *
     * @return boolean - True if Ogre is stunned; False if Ogre isn´t stunned;
     */
    boolean isStunned(){
        return roundsStunned > 0;
    }

    /* SETTERS */

    /** Function to set Ogre stunned and change the symbol of ogre;
     * roundsStunned = 3 = (2+1) because roundsStunned is decremented in the round that it is set
     */
    void setStunned(){
        roundsStunned = 3;
        symbol = '8';
    }

    /** Function to change status of stun;
     *  It will decrease number of left rounds to be stunned every time that is called;
     *  Change the symbol of Ogre is he recover totally of stun;
     */
    void recoverFromStun(){
        if (roundsStunned > 0){
            roundsStunned--;
            }
        if (roundsStunned == 0){
            symbol = 'O';
        }
    }

}
