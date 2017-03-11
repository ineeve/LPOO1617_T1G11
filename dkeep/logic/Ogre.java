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
        roundsStunned = 0;
    }
    

            
    void recoverFromStun(){
        if (roundsStunned > 0){
            roundsStunned--;
            }
        if (roundsStunned == 0){
            symbol = 'O';
        }
    }
    boolean isStunned(){
        return roundsStunned > 0;
    }
    /**
     * roundsStunned = 3 = (2+1) because roundsStunned is decremented in the round that it is set
     */        
    void setStunned(){
        roundsStunned = 3;
        symbol = '8';
    }

	@Override
	public char getNextDirection() {
		return  movement.randomMovement();
	}
            
}
