package dkeep.logic;

class Guard extends MovingAgent {
    
    int currentDirection;
    
    Guard() {
        symbol = 'G';
        isSleeping = false;
        currentDirection = 1;
        weapon = new Weapon (' ',agentCoords);
    }

	@Override
	char getNextDirection() {
		return movement.pathMovement(1);
	}
}
