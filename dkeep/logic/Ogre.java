package dkeep.logic;

import java.awt.*;

public class Ogre extends MovingAgent {
    protected int roundsStunned;
    
    public Ogre() {
        symbol = 'O';
        roundsStunned = 0;
    }

    public Ogre(Point coord) {
        symbol = 'O';
        agentCoords = coord;
        weapon = new Weapon('*',agentCoords);
        weapon.setCoords((Point) agentCoords.clone());
        weapon.getCoords().x--;
        roundsStunned = 0;
    }

    @Override
    void nextMove() {
        char nextChar = movement.randomMovement();
        super.nextPos(nextChar);
    }
    
        void recoverFromStun(){
        if (roundsStunned > 0){
            roundsStunned--;
        }
        else{
            symbol= 'O';
        }
    }
    boolean isStunned(){
        return roundsStunned > 0;
    }
    
    void setStunned(){
        roundsStunned = 2;
        symbol = '8';
    }
    
}
