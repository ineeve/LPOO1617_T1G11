package dkeep.logic;

import dkeep.cli.MovementStrategy;

import java.awt.*;

public abstract class MovingAgent {
    Point agentCoords; // [x,y] current coordinates
    final MovementStrategy movement = new MovementStrategy();
    char symbol;
    boolean isSleeping;
    boolean key;
    
    public Weapon weapon;
    
    public void setKey(boolean key) {
        this.key = key;
    }
    
    public Point getAgentCoords(){
        return agentCoords;
    }
    
    public void setAgentCoords(Point agentCoords) {
        this.agentCoords = agentCoords;
    }

    abstract char getNextDirection();
    
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

    public char getSymbol() {
        return symbol;
    }
    public void setSymbol(char newSymbol){
        symbol = newSymbol;
    }
    

}
