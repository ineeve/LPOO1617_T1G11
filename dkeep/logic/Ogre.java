package dkeep.logic;
import java.awt.*;

public class Ogre extends MovingAgent {
	private Point club;

	public Ogre() {
	    symbol = 'O';
	}

    public Ogre(Point coord) {
        symbol = 'O';
        agentCoords = coord;
    }

    public Point getClub() {
        return club;
    }

    @Override
	public void setAgentCoords(Point agentCoords) {
		super.setAgentCoords(agentCoords);
	}

    @Override
    void nextMove() {
        movement.randomMovement(agentCoords);
        club = agentCoords;
        movement.randomMovement(club);
    }
}
