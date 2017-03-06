package dkeep.logic;

import dkeep.cli.UserInput;
import dkeep.logic.maps.DungeonMap;
import dkeep.logic.maps.GameMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by João on 23/02/2017.
 */
public class Game {
    private Configs config;
    private GameMap map;
    private ArrayList<MovingAgent> agents = new ArrayList<>();
    private Key key;
    private boolean keyTaken;
    
    public Game(int startLevel) {
        config = new Configs(startLevel);
        map = config.getNextMap();
        agents = config.getAgents();
        key = config.getKey();
        keyTaken = false;
    }
    
    public Point getHeroPos(){
        return agents.get(0).getAgentCoords();
    }
    public void moveHero(char direction){
        agents.get(0).nextMove();
    }
    
    public char[][] getMap() {
        char[][] mapChar = map.getMap().clone();
        
        for (int i = 0; i < map.getMap().length; i++) {
            mapChar[i] = map.getMap()[i].clone();
        }
        if (!keyTaken){
            mapChar[key.getCoord().y][key.getCoord().x] = 'k';
        }
        
        for (int i = 0; i < agents.size(); i++) {
            int agentCoordY = agents.get(i).getAgentCoords().y;
            int agentCoordX = agents.get(i).getAgentCoords().x;
            char symbol = agents.get(i).getSymbol();
            mapChar[agentCoordY][agentCoordX] = symbol;
            if(agents.get(i).weapon.getSymbol() != ' '){
                int weaponCoordY = agents.get(i).weapon.getCoords().y;
                int weaponCoordX = agents.get(i).weapon.getCoords().x;
                symbol = agents.get(i).weapon.getSymbol();
                mapChar[weaponCoordY][weaponCoordX] = symbol;
            }
        }
        return mapChar;
    }
    
    public void setMap(GameMap map) {
        this.map = map;
    }
    
    public int update() {
        for (int i = 0; i < agents.size(); i++) {
            MovingAgent actualAgent = agents.get(i);
            int lastPositionX = actualAgent.getAgentCoords().x;
            int lastPositionY = actualAgent.getAgentCoords().y;
            actualAgent.nextMove();
            int isFreeResponse = map.isFree(actualAgent.getAgentCoords());
            switch (isFreeResponse) {
                case 0:
                    actualAgent.setAgentCoords(new Point(lastPositionX, lastPositionY));
                    break;
                case 1:
                    if (key.getCoord().x == actualAgent.getAgentCoords().x && key.getCoord().y == actualAgent.getAgentCoords().y) {
                        actualAgent.setKey(true);
                        if (actualAgent instanceof Hero){
                            keyTaken = true;
                            map.changeAllDoorsToStairs();
                        }
                    } else {
                        actualAgent.setKey(false);
                    }
                    break;
                case 2:
                    if(config.prepareNextLevel() != 0){
                        return 1;
                    }
                    map = config.getNextMap();
                    agents = config.getAgents();
                    keyTaken = false;
                    return 0;
            }
            if(actualAgent.weapon.getSymbol() != ' ') {
                actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
                actualAgent.weapon.nextMove();
                while (map.isFree(actualAgent.weapon.getCoords()) != 1){
                    actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
                    actualAgent.weapon.nextMove();
                }
            }
        }
        return 0;
    }
    
    public boolean isGameOver() {
        for (int i = 1; i < agents.size(); i++) {
            if (agents.get(0).getAgentCoords().distance(agents.get(i).getAgentCoords()) <= 1) {
                if (!agents.get(i).isSleeping) {
                    return true;
                }
            }
        }
        return false;
    }
}
