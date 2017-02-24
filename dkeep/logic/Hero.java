package dkeep.logic;

public class Hero extends MovingAgent {
    public Hero() {
        symbol = 'H';
    }

    @Override
    void nextMove() {
        movement.userMovement(agentCoords);
    }
}
