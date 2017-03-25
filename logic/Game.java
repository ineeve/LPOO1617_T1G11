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
	private Lever lever;
	private boolean keyTaken;
	private boolean leverPressed;
	public enum status {DEFEAT,PLAYING,VICTORY}
	public status gameStatus;

	public Game(Configs c){
		config = c;
	}

	public void resetLevel(){
		config.prepareNextLevel();
		map = config.getMap();
		agents = config.getAgents();
		key = config.getKey();
		lever = config.getLever();
		keyTaken = false;
		leverPressed=false;
		gameStatus = status.PLAYING;
	}

	public void setConfig(Configs config) {
		this.config = config;
	}

	private char[][] getArrayMap(){
		return map.getMap();
	}

	public char[][] getMap() {
		char[][] mapChar = getArrayMap().clone();

		for (int i = 0; i < getArrayMap().length; i++)
			mapChar[i] = getArrayMap()[i].clone();

		if (key != null){
			if (!keyTaken)
				mapChar[key.getCoord().y][key.getCoord().x] = 'k';
		}
		else if (lever!=null){
			if (leverPressed) mapChar[lever.getCoord().y][lever.getCoord().x] = 'K';
			else mapChar[lever.getCoord().y][lever.getCoord().x] = 'k';
		}

		for (MovingAgent agent : agents) {
			int agentCoordY = agent.getAgentCoords().y;
			int agentCoordX = agent.getAgentCoords().x;
			char symbol = agent.getSymbol();
			mapChar[agentCoordY][agentCoordX] = symbol;
			if (agent.weapon.getSymbol() != ' ') {
				int weaponCoordY = agent.weapon.getCoords().y;
				int weaponCoordX = agent.weapon.getCoords().x;
				symbol = agent.weapon.getSymbol();
				mapChar[weaponCoordY][weaponCoordX] = symbol;
			}
		}
		return mapChar;
	}

	public Hero getHero(){
		return (Hero) agents.get(0);
	}

	public Ogre getFirstOgre(){
		for (MovingAgent agent: agents)
			if (agent instanceof Ogre)
				return (Ogre) agent;
		return null;
	}

	public int moveHero(char direction){
		MovingAgent actualAgent = agents.get(0);
		return moveAgent(actualAgent,direction);
	}

	public int moveAllAgents(char heroNextDirection){
		int returnValue = moveHero(heroNextDirection);
		if (returnValue != 0){
			return returnValue; //nextlevel & victory
		}
		moveBots();
		if(isGameOver()){
			return 3; //defeat
		}
		return 0; //nextround
	}

	public void moveBots(){
		for (int i = 1; i< agents.size(); i++){
			MovingAgent actualAgent = agents.get(i);
			char nextDirection = actualAgent.getNextDirection();
			moveAgent(actualAgent,nextDirection);
		}
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

	private void leverHandler(MovingAgent actualAgent){
		if (lever!=null){
			if (lever.getCoord().distance(actualAgent.getAgentCoords()) == 0) {
				if (actualAgent instanceof Hero){
					leverPressed = true;
					changeDoorsToStairs(lever.getDoors());
				}
			}
		}
	}

	private void keyHandler(MovingAgent actualAgent){
		if (key!= null){
			if (key.getCoord().distance(actualAgent.getAgentCoords()) == 0) {
				if (actualAgent instanceof Hero){
					keyTaken = true;
					actualAgent.setSymbol('K');
				}
			}
		}
	}

	private int responseHandler(MovingAgent actualAgent, int isFreeResponse, Point lastPosition){
		switch (isFreeResponse) {
		case 0:
			actualAgent.setAgentCoords(lastPosition);
			break;
		case 1:
			if (key!=null){
				keyHandler(actualAgent);
			}
			if (lever != null){
				leverHandler(actualAgent);
			}
			ogreStunned(actualAgent);
			break;
		case 2:
			if(actualAgent instanceof Hero) {
				if (map.nextMap() == null) {
					gameStatus = status.VICTORY;
					return 2;
				}
				return 1;
			}
			else{
				actualAgent.setAgentCoords(lastPosition);
			}
			break;
		case 3:
			if (actualAgent instanceof Hero && keyTaken){
				changeDoorsToStairs(new Point[]{key.getDoorPos()});
			}
			actualAgent.setAgentCoords(lastPosition);
			break;
		}
		return 0;
	}

	private void changeDoorsToStairs(Point[] doors){
		map.changeDoorsToStairs(doors);
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

		if(actualAgent.weapon.getSymbol() != ' ') {
			moveWeapon(actualAgent);
		}

		if (handlerResponse != 0){
			return handlerResponse;
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

	public int getLevel(){
		return config.getLevel();
	}
}
