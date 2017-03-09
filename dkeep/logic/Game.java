package dkeep.logic;

import dkeep.logic.maps.DungeonMap;
import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.KeepMap;

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
    public enum status {DEFEAT,PLAYING,VICTORY};
    private status gameStatus;
    
    public Game(int startLevel) {
        config = new Configs(startLevel);
        map = config.getNextMap();
        agents = config.getAgents();
        key = config.getKey();
        keyTaken = false;
        gameStatus = status.PLAYING;
    }
    
    public status getGameStatus(){
        return gameStatus;
    }
    
    
    public Point getHeroPos(){
        return agents.get(0).getAgentCoords();
    }
    public void moveHero(char direction){
        agents.get(0).nextPos(direction);
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
            
            if (actualAgent instanceof Ogre){
                if (((Ogre) actualAgent).isStunned()){
                    ((Ogre) actualAgent).recoverFromStun();
                    actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
                    actualAgent.weapon.nextMove();
                    while (map.isFree(actualAgent.weapon.getCoords()) != 1){
                        actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
                        actualAgent.weapon.nextMove();
                    }
                    continue;
                }
            }
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
                            if (!(map instanceof KeepMap)){
                                map.changeAllDoorsToStairs();
                            }else{
                                actualAgent.setSymbol('K');
                            }
                        }
                    } else {
                        actualAgent.setKey(false);
                    }
                    break;
                case 2:
                    if(config.prepareNextLevel() != 0){
                        gameStatus = status.VICTORY;
                        return 1;
                    }
                    map = config.getNextMap();
                    agents = config.getAgents();
                    keyTaken = false;
                    return 0;
                case 3:
                    if (actualAgent instanceof Hero){
                        if ( keyTaken){
                            if (map instanceof KeepMap){
                                map.changeAllDoorsToStairs();
                            }
                        }
                    }
                    
                    
                    actualAgent.setAgentCoords(new Point(lastPositionX, lastPositionY));
                    break;
            }
            if(actualAgent.weapon.getSymbol() != ' ') {
                actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
                actualAgent.weapon.nextMove();
                while (map.isFree(actualAgent.weapon.getCoords()) != 1 && map.isFree(actualAgent.weapon.getCoords()) != 4 ){
                    actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
                    actualAgent.weapon.nextMove();
                }
                
            }
            
            if (actualAgent instanceof Ogre){
                if (actualAgent.getAgentCoords().distance(agents.get(0).weapon.getCoords()) <= 1){
                    ((Ogre) actualAgent).setStunned();
                }
            }
            
        }
        
        
        
        return 0;
    }
    
    public boolean isGameOver() {
        for (int i = 1; i < agents.size(); i++) {
            
            if (agents.get(i) instanceof Ogre){
                if (agents.get(i).weapon.getCoords().distance(agents.get(0).getAgentCoords()) <= 1){
                    gameStatus = status.DEFEAT;
                    return true;
                }
            }
            
            else if (agents.get(0).getAgentCoords().distance(agents.get(i).getAgentCoords()) <= 1) {
                if (!agents.get(i).isSleeping) {
                    gameStatus = status.DEFEAT;
                    return true;
                }
            }
        }
        return false;
    }
}
