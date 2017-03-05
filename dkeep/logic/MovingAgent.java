package dkeep.logic;

import dkeep.cli.MovementStrategy;

import java.awt.*;

public abstract class MovingAgent {
    Point agentCoords; // [x,y] current coordinates
    final MovementStrategy movement = new MovementStrategy();
    char symbol;
    boolean isSleeping;
    private boolean key;
    Weapon weapon = new Weapon();
    
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

    public char getSymbol() {
        return symbol;
    }
    public void setSymbol(char newSymbol){
        symbol = newSymbol;
    }
}
