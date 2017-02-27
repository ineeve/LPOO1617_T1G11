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

    public Game() {
        map = new DungeonMap();
        agents.add(new Hero(new Point(1, 1)));
        //agents.add(new Guard(new Point(8,1)));
        //agents.add(new Drunken(new Point(8, 1)));
        //agents.add( new Rookie(new Point(8,1)));
        agents.add( new Suspicious(new Point(8,1)));
        key.setCoord(new Point(7, 8));
    }

    public char[][] getMap() {
        char[][] mapChar = map.getMap().clone();
        for (int i = 0; i < map.getMap().length; i++) {
            mapChar[i] = map.getMap()[i].clone();
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
                    if (key.getCoord() == agents.get(i).getAgentCoords()) {
                        agents.get(i).setKey(true);
                    } else {
                        agents.get(i).setKey(false);
                    }
                    break;
                case 2:
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
