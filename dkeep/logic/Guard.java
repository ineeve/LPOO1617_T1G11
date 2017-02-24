package dkeep.logic;

public class Guard extends MovingAgent {
    public Guard() {
        symbol = 'G';
    }

    @Override
    void nextMove() {
        movement.pathMovement(agentCoords);
    }
}
