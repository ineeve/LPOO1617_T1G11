package dkeep.logic;

import dkeep.logic.maps.DungeonMap;
import dkeep.logic.maps.GameMap;

import java.awt.*;

/**
 * Created by João on 23/02/2017.
 */
public class Game {
    private GameMap map;
    private MovingAgent[] agents;
    private Key key;

    public Game() {
        map = new DungeonMap();
        agents[0] = new Hero();
        agents[0].setAgentCoords(new Point (1,1));
        agents[1] = new Guard();
        agents[1].setAgentCoords(new Point (8,1));
        key.setCoord(new Point(7,8));
    }

    public char[][] getMap() {
        char[][] mapChar = map.getMap();
        for(int i = 0; i < agents.length; i++){
            int agentCoordY = agents[i].getAgentCoords().y;
            int agentCoordX = agents[i].getAgentCoords().x;
            char symbol = agents[i].getSymbol();
            mapChar[agentCoordY][agentCoordX] = symbol;
        }
        return mapChar;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void update() {
        for(int i = 0; i < agents.length; i++){
            Point lastPosition = agents[i].getAgentCoords();
            agents[i].nextMove();
            int isFreeResponse = map.isFree(agents[i].getAgentCoords());
            switch (isFreeResponse) {
                case 0:
                    agents[i].setAgentCoords(lastPosition);
                    break;
                case 1:
                    if(key.getCoord() == agents[i].getAgentCoords()){
                        agents[i].setKey(true);
                    }
                    else{
                        agents[i].setKey(false);
                    }
                    break;
                case 2:
                    break;
            }
        }
    }

    public boolean isGameOver(){
        boolean isOver = false;

        return isOver;
    }
}
