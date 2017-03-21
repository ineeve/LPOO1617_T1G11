package dkeep.logic;

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
	public static status gameStatus;

	public Game(){}

	public Game(int startLevel) {
		config = new Configs(startLevel);
		map = config.getMap();
		agents = config.getAgents();
		key = config.getKey();
		keyTaken = false;
		gameStatus = status.PLAYING;
	}

	public void setConfigs(Configs config){
		this.config = config;
	}
	public Configs getConfig(){
		return config;
	}
	
	public void resetLevel(){
		this.config = new Configs(config.level);
		config.prepareNextLevel();
		map = config.getMap();
		agents = config.getAgents();
		key = config.getKey();
		keyTaken = false;
		gameStatus = status.PLAYING;
	}
	
	public void setMap(GameMap map) {
		this.map = map;
	}

	public void setAgents(ArrayList<MovingAgent> agentsArray){
		agents = agentsArray;
	}

	public void setKey(Key newKey){
		key = newKey;
	}

	public void setKeyTaken(boolean key){
		keyTaken = key;
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

	public Hero getHero(){
		return (Hero) agents.get(0);
	}

	public Ogre getFirstOgre(){
		for (MovingAgent agent: agents){
			if (agent instanceof Ogre){
				return (Ogre) agent;
			}
		}
		return null;
	}

	public status getGameStatus(){
		return gameStatus;
	}

	public int moveHero(char direction){
		MovingAgent actualAgent = agents.get(0);
		return moveAgent(actualAgent,direction);
	}

	public void moveOgres(){
		for (int i = 1; i < agents.size(); i++){
			MovingAgent actualAgent = agents.get(i);
			if (actualAgent instanceof Ogre){
				moveAgent(actualAgent, actualAgent.getNextDirection());
			}
		}
	}

	public int moveAllAgents(){
		int returnValue = moveAgent(agents.get(0),agents.get(0).getNextDirection());
		if (returnValue != 0){
			return returnValue;
		}
		return moveBots();

	}
	public int moveBots(){
		for (int i = 1; i< agents.size(); i++){
			MovingAgent actualAgent = agents.get(i);
			char nextDirection = actualAgent.getNextDirection();
			int returnValue = moveAgent(actualAgent,nextDirection);
			if(returnValue != 0){
				return returnValue;
			}
		}
		return 0;
	}

    private void ogreStunned(MovingAgent actualAgent){
        if (actualAgent instanceof Ogre){ /* Verify if can be stunned */
            if (actualAgent.getAgentCoords().distance(agents.get(0).weapon.getCoords()) <= 1){
                ((Ogre) actualAgent).setStunned();
            }
        }
    }

	private int ogreHandler(MovingAgent actualAgent){
		if (((Ogre) actualAgent).isStunned()){
			((Ogre) actualAgent).recoverFromStun();
			actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
			actualAgent.weapon.nextMove();
			while (map.isFree(actualAgent.weapon.getCoords()) != 1){
				actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
				actualAgent.weapon.nextMove();
			}
			return 1;
		}
		return 0;
	}

	private void keyHandler(MovingAgent actualAgent){
        if (key.getCoord().distance(actualAgent.getAgentCoords()) == 0) {
		    actualAgent.setKey(true);
		    if (actualAgent instanceof Hero){
			    keyTaken = true;
			    if (!(map instanceof KeepMap)){
                    map.changeAllDoorsToStairs();
			    }else{
				    actualAgent.setSymbol('K');
			    }
		    }
        }
        else {
            actualAgent.setKey(false);
        }
	}

	private int responseHandler(MovingAgent actualAgent, int isFreeResponse, Point lastPosition){
		switch (isFreeResponse) {
			case 0:
				actualAgent.setAgentCoords(lastPosition);
				break;
			case 1:
			    keyHandler(actualAgent);
			    ogreStunned(actualAgent);
				break;
			case 2:
				if(map.nextMap() == null){
					gameStatus = status.VICTORY;
					return 2;
				}
				return 1;
			case 3:
				if (actualAgent instanceof Hero){
					if (keyTaken){
						if (map instanceof KeepMap){
							map.changeAllDoorsToStairs();
						}
					}
				}
				actualAgent.setAgentCoords(lastPosition);
				break;
		}
		return 0;
	}

	private void moveWeapon(MovingAgent actualAgent){
		actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
		actualAgent.weapon.nextMove();
		while (map.isFree(actualAgent.weapon.getCoords()) != 1 && map.isFree(actualAgent.weapon.getCoords()) != 4 ){
			actualAgent.weapon.setCoords((Point) actualAgent.getAgentCoords().clone());
			actualAgent.weapon.nextMove();
		}
	}

	/**
	 * 
	 * @param actualAgent theAgent to move
	 * @param direction the direction
	 * @return 0-Nothing, 1-ChangeToNextMap 2-Victory
	 */
	public int moveAgent(MovingAgent actualAgent, char direction){
		Point lastPosition = (Point) actualAgent.getAgentCoords().clone();

		if (actualAgent instanceof Ogre){
			if(ogreHandler(actualAgent) != 0) return 0; /* Verify if ogre is stun */
		}

		actualAgent.nextPos(direction);
		int isFreeResponse = map.isFree(actualAgent.getAgentCoords()); /* Verify if next position is free */
		int handlerResponse = responseHandler(actualAgent, isFreeResponse, lastPosition);/* Handler for next position of agent */
		if (handlerResponse != 0){
			return handlerResponse;
		}

		if(actualAgent.weapon.getSymbol() != ' ') {
			moveWeapon(actualAgent);
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
