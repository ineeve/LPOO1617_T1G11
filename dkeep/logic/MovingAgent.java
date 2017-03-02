package dkeep.logic;

import java.awt.*;

public abstract class MovingAgent {
    protected Point agentCoords; // [x,y] current coordinates
    protected MovementStrategy movement = new MovementStrategy();
    protected char symbol;
    protected boolean isSleeping;
<<<<<<< Updated upstream
    
    public boolean isKey() {
        return key;
    }
    
    public void setKey(boolean key) {
        this.key = key;
    }
    
    public Point getAgentCoords(){
        return agentCoords;
    }
    
    public void setInitialPos(int x, int y){
        agentCoords.x = x;
        agentCoords.y = y;
    }
    
    public void setAgentCoords(Point agentCoords) {
        this.agentCoords = agentCoords;
    }
    
    abstract void nextMove();
    
=======
    private boolean key;

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public Point getAgentCoords() {
        return agentCoords;
    }

    public void setAgentCoords(Point agentCoords) {
        this.agentCoords = agentCoords;
    }

    public void setInitialPos(int x, int y) {
        agentCoords.x = x;
        agentCoords.y = y;
    }

    abstract void nextMove();

>>>>>>> Stashed changes
    public char getSymbol() {
        return symbol;
    }
}
