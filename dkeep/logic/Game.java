package dkeep.logic;

import dkeep.logic.maps.DungeonMap;
import dkeep.logic.maps.GameMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by João on 23/02/2017.
 */
public class Game {

    private GameMap map;
    private ArrayList<MovingAgent> agents = new ArrayList<>();
    private Key key = new Key(new Point(0, 0));
    private boolean keyTaken;

    public Game() {
        map = new DungeonMap();
        agents = map.getAgents();
        key.setCoord(new Point(3, 1));
        keyTaken = false;
    }
    
    private void changeAllDoorsToStairs(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'I') {
                    map[y][x] = 'S';
                }
            }
        }
    }

    public char[][] getMap() {
        char[][] mapChar = map.getMap().clone();
        
        for (int i = 0; i < map.getMap().length; i++) {
            mapChar[i] = map.getMap()[i].clone();
        }
        if (!keyTaken){
            mapChar[key.getCoord().y][key.getCoord().x] = 'k';
        }else{
            changeAllDoorsToStairs(mapChar);
        }
        
        for (int i = 0; i < agents.size(); i++) {
            int agentCoordY = agents.get(i).getAgentCoords().y;
            int agentCoordX = agents.get(i).getAgentCoords().x;
            char symbol = agents.get(i).getSymbol();
            mapChar[agentCoordY][agentCoordX] = symbol;
        }
        return mapChar;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void update() {
        for (int i = 0; i < agents.size(); i++) {
            int lastPositionX = agents.get(i).getAgentCoords().x;
            int lastPositionY = agents.get(i).getAgentCoords().y;
            agents.get(i).nextMove();
            int isFreeResponse = map.isFree(agents.get(i).getAgentCoords());
            switch (isFreeResponse) {
                case 0:
                    agents.get(i).setAgentCoords(new Point(lastPositionX, lastPositionY));
                    break;
                case 1:

        if (key.getCoord().x == agents.get(i).getAgentCoords().x && key.getCoord().y == agents.get(i).getAgentCoords().y) {
                        agents.get(i).setKey(true);
                        if (agents.get(i).symbol == 'H'){
                            keyTaken = true;
                        }
                    } else {
                        agents.get(i).setKey(false);
                    }
                    break;
                case 2:
                    this.map = map.nextMap();
                    keyTaken = false;
                    break;
            }
        }
    }

    public boolean isGameOver() {
        boolean isOver = false;
        for (int i = 1; i < agents.size(); i++) {
            if (agents.get(0).getAgentCoords().distance(agents.get(i).getAgentCoords()) <= 1) {
                if (!agents.get(i).isSleeping) {
                    return true;
                }
            }
        }
        return isOver;
    }
}
