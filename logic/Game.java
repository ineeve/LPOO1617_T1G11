package dkeep.logic;

import dkeep.logic.maps.GameMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class responsible for Game;
 */
public class Game {
	/**Contains the configurations of the Game*/
	private Configs config;
	/**Contains the maps of the Game*/
	private GameMap map;
	/**Contains the MovingAgent of the Game*/
	private ArrayList<MovingAgent> agents = new ArrayList<>();
	/**Contains the Key of the Game*/
	public Key key;
	/**Contains the lever of the Game*/
	private Lever lever;
	/**Contains information if the key have be take*/
	private boolean keyTaken;
	/**Contains information about if lever is enable of disable*/
	private boolean leverPressed;
	/**Contains the game status*/
	public status gameStatus;

	/**
	 * enum to be easier define the status of game;
	 */
	public enum status {DEFEAT,PLAYING,VICTORY}


	/* CONSTRUCTOR */

	/** Constructor of Game;
	 * Initialize Game and set configuration;
	 *
	 * @param c - Configs to set configuration of game
	 */
	public Game(Configs c){
		config = c;
	}

	/** Function to reset completely the game;
	 * 	This first prepare the configuration to this level;
	 * 	After will get the information of the start conditions of the game on config and set all to the game;
	 */
	public void resetLevel(){
		config.prepareNextLevel();
		config.replaceStairs();
		map = config.getMap();
		agents = config.getAgents();
		key = config.getKey();
		lever = config.getLever();
		keyTaken = false;
		leverPressed = false;
		gameStatus = status.PLAYING;
	}


	/* GETTERS */

	/** Function to get current level;
	 *
	 * @return int - correspond to the current level being playing (0 - Test Level; 1 - Dungeon Level; 2 - Keep Level);
	 */
	public int getLevel(){
		return config.getLevel();
	}

	/** Function to get game board from GameMap;
	 *
	 * @return char[][] - that correspond to the current map being playing.
	 */
	private char[][] getArrayMap(){
		if(map == null){
			return null;
		}
		return map.getMap();
	}

	/** Function to get the map with all objects one board;
	 * 	The MovingAgents also are one the array of char;
	 *
	 * @return char[][] - with all object one game;
	 */
	public char[][] getMap() {
		if(map == null)
			return null;

		char[][] mapChar = getArrayMap().clone();

		for (int i = 0; i < getArrayMap().length; i++)
			mapChar[i] = getArrayMap()[i].clone();

		if (key != null){
			if (!keyTaken)
				mapChar[key.getCoord().y][key.getCoord().x] = 'k';
		}
		else if (lever!=null){
			if (leverPressed)
				mapChar[lever.getCoord().y][lever.getCoord().x] = 'K';
			else
				mapChar[lever.getCoord().y][lever.getCoord().x] = 'k';
		}

		for (MovingAgent agent : agents) {
            writeOnMapSymbol(agent, mapChar);
			if (agent.weapon.getSymbol() != ' ') {
				int weaponCoordY = agent.weapon.getCoords().y;
				int weaponCoordX = agent.weapon.getCoords().x;
				char symbol = agent.weapon.getSymbol();
				if (agent.weapon.getCoords().distance(key.getCoord()) == 0)
                    mapChar[weaponCoordY][weaponCoordX] = '$';
                else
                    mapChar[weaponCoordY][weaponCoordX] = symbol;
			}
		}
		return mapChar;
	}

    private void writeOnMapSymbol(MovingAgent agent, char[][] mapChar){
        int agentCoordY = getAgentCoord(agent).y;
        int agentCoordX = getAgentCoord(agent).x;
        if (key != null){
            if (agent instanceof Ogre && getAgentCoord(agent).distance(key.getCoord()) == 0) {
                mapChar[agentCoordY][agentCoordX] = '$';
            }
        }
        else {
            mapChar[agentCoordY][agentCoordX] = agent.getSymbol();
        }
    }

	/** Function to get the Hero one the Game;
	 *
	 * @return Hero - correspond to the Hero of game, if game don't have Hero return null;
	 */
	public Hero getHero(){
		if(agents.size() == 0){
			return null;
		}
		if(agents.get(0) instanceof Hero)
			return (Hero) agents.get(0);
		else
			return null;
	}

	/** Function to get first Ogre
	 *
	 * @return Ogre - correspond to the first Ogre of the Game, if game don't have one Ogre return null;
	 */
	public Ogre getFirstOgre(){
		for (MovingAgent agent: agents)
			if (agent instanceof Ogre)
				return (Ogre) agent;
		return null;
	}

	/** Function that verify if game is over and update the gameStatus;
	 * 	Game is over if the distance between Guards or weapon of Ogres is under 1;
	 *
	 * @return boolean - True if game is over, False if game is steel playing;
	 */
	public boolean isGameOver() {
		for (MovingAgent agent : agents) {
			if (agent instanceof Ogre) {
				if (agent.weapon.getCoords().distance(getAgentCoord(agents.get(0))) <= 1) {
					gameStatus = status.DEFEAT;
					return true;
				}
			} else if (!(agent instanceof Hero))
				if (getAgentCoord(agents.get(0)).distance(getAgentCoord(agent)) <= 1) {
					if (!agent.isSleeping) {
						gameStatus = status.DEFEAT;
						return true;
					}
				}
		}
		return false;
	}

	private Point getAgentCoord(MovingAgent agent){
		return agent.getAgentCoords();
	}


	/* SETTERS */

	/**
	 * Function to set the configuration object to the Game;
	 *
	 * @param config - Configs to be set on Game;
	 */
	public void setConfig(Configs config) {
		this.config = config;
	}


	/* MOVE FUNCTIONS */

	/** Function to move all MovingAgents of the game;
	 * 	First move Hero and second move all other MovingAgents;
	 *
	 * @param heroNextDirection - char that correspond the next direction of hero;
	 * @return 0 - next round; 1 - for next level; 2 - for victory; 3 - for defeat;
	 */
	public int moveAllAgents(char heroNextDirection){
		int returnValue = moveHero(heroNextDirection);
		if (returnValue != 0)
			return returnValue;
		moveBots();
		if(isGameOver())
			return 3;
		return 0;
	}

	/** Function to move just the Hero;
	 *
	 * @param direction - char that correspond the next direction of hero;
	 * @return 0 - move of hero occur with success; 1 - for next level; 2 - for victory;
	 */
	public int moveHero(char direction){
		MovingAgent actualAgent = agents.get(0);
		return moveAgent(actualAgent,direction);
	}

	/**
	 * Function to move all MovingAgents off game except Hero;
	 */
	public void moveBots(){
		for (int i = 1; i< agents.size(); i++){
			MovingAgent actualAgent = agents.get(i);
			char nextDirection = actualAgent.getNextDirection();
			moveAgent(actualAgent,nextDirection);
		}
	}

	/** Function to move any MovingAgent;
	 * 	This function move the MovingAgent to one cell and if can't be there is reset the position to the previously position
	 *
	 * @param actualAgent MovingAgent - that correspond the agent to move;
	 * @param direction char - thar correpond to the direction of move;
	 * @return 0 - Nothing; 1 - ChangeToNextMap; 2 - Victory;
	 */
	public int moveAgent(MovingAgent actualAgent, char direction){
		Point lastPosition = (Point) getAgentCoord(actualAgent).clone();

		if (actualAgent instanceof Ogre){
			if(ogreHandler(actualAgent) != 0) return 0; /* Verify if ogre is stun */
		}

		actualAgent.nextPos(direction);
		int isFreeResponse = map.isFree(getAgentCoord(actualAgent)); /* Verify if next position is free */
		int handlerResponse = responseHandler(actualAgent, isFreeResponse, lastPosition);/* Handler for next position of agent */

		if(actualAgent.weapon.getSymbol() != ' ') {
			if(!(actualAgent instanceof Hero))
				moveWeapon(actualAgent);
			else
				weaponHeroHandler((Hero) actualAgent);
		}

		if (handlerResponse != 0){
			return handlerResponse;
		}

		return 0;
	}

	/** Function to move Weapon of any MovingAgent;
	 *
	 * @param actualAgent - MovingAgent that needs to move your's weapon
	 */
	private void moveWeapon(MovingAgent actualAgent){
		actualAgent.weapon.setCoords((Point) getAgentCoord(actualAgent).clone());
		actualAgent.weapon.nextMove();
		while (map.isFree(actualAgent.weapon.getCoords()) != 1 && map.isFree(actualAgent.weapon.getCoords()) != 4 ){
			actualAgent.weapon.setCoords((Point) getAgentCoord(actualAgent).clone());
			actualAgent.weapon.nextMove();
		}
	}


	/* HANDLERS */

	/** Function to handle the Ogre;
	 * 	Verify if the Ogre is stunned, and if is true just move the weapon of it;
	 *
	 * @param actualAgent - Ogre to handle;
	 * @return 0 - Ogre can move; 1 - Ogre can´t move;
	 */
	private int ogreHandler(MovingAgent actualAgent){
		if (((Ogre) actualAgent).isStunned()){
			((Ogre) actualAgent).recoverFromStun();
			actualAgent.weapon.setCoords((Point) getAgentCoord(actualAgent).clone());
			actualAgent.weapon.nextMove();
			while (map.isFree(actualAgent.weapon.getCoords()) != 1){
				actualAgent.weapon.setCoords((Point) getAgentCoord(actualAgent).clone());
				actualAgent.weapon.nextMove();
			}
			return 1;
		}
		return 0;
	}

	/**Constant value that correspond to the return value of GameMap.isFree()*/
	final int OUTOFMAP = 0;
	/**Constant value that correspond to the return value of GameMap.isFree()*/
	final int FREESPACE = 1;
	/**Constant value that correspond to the return value of GameMap.isFree()*/
	final int STAIRS = 2;
	/**Constant value that correspond to the return value of GameMap.isFree()*/
	final int DOOR = 3;
	/**Constant value that correspond to the return of next level*/
	final int NEXTLEVEL = 1;
	/**Constant value that correspond to the return victory*/
	final int VICTORY = 2;

	/** Function that handle with the response of GameMap.isFree() after move one MovingAgent;
	 * 	If return of GameMap.isFree() is:
	 * 		.. 0 - Out of Map, the position of MovingAgent is reset to the previously;
	 * 		.. 1 - Free Space, verify if took the key or enables the lever and if stun the Ogre;
	 * 		.. 2 - Stairs, verify if the MovingAgent is Hero to with the game;
	 * 		.. 3 - Door, verify if the MovingAgent have the key to Open the door;
	 *
	 * @param actualAgent MovingAgent - that correspond to the agent is on move;
	 * @param isFreeResponse int - that correspond to the response of GameMap.isFree()[0-Out of map; 1 - ' '; 2 - 'S'; 3 - 'I']
	 * @param lastPosition Point - that contains the position before move the agent;
	 * @return 0 - move with success; 1 - next level; 2 - victory;
	 */
	private int responseHandler(MovingAgent actualAgent, int isFreeResponse, Point lastPosition){
		switch (isFreeResponse) {
			case OUTOFMAP:
				actualAgent.setAgentCoords(lastPosition);
				break;
			case FREESPACE:
				if (key != null)
					keyHandler(actualAgent);
				if (lever != null)
					leverHandler(actualAgent);
				ogreStunned(actualAgent);
				break;
			case STAIRS:
				if(actualAgent instanceof Hero) {
					if (map.nextMap() == null) {
						gameStatus = status.VICTORY;
						return VICTORY;
					}
					return NEXTLEVEL;
				}
				else{
					actualAgent.setAgentCoords(lastPosition);
				}
				break;
			case DOOR:
				if (actualAgent instanceof Hero && keyTaken)
					changeDoorsToStairs(new Point[]{getAgentCoord(actualAgent)});
				actualAgent.setAgentCoords(lastPosition);
				break;
		}
		return 0;
	}

	/** Function that verify if MovingAgent enables the lever;
	 *
	 * @param actualAgent - MovingAgent that needs to verify if enables the lever;
	 */
	private void leverHandler(MovingAgent actualAgent){
		if (lever!=null && lever.getCoord().distance(getAgentCoord(actualAgent)) == 0)
			if (actualAgent instanceof Hero){
				leverPressed = true;
				changeDoorsToStairs(lever.getDoors());
			}
	}

	/** Function that verify if MovingAgent took the key;
	 *
	 * @param actualAgent - MovingAgent that needs to verify if took the key;
	 */
	private void keyHandler(MovingAgent actualAgent) {
		if (key != null && key.getCoord().distance(getAgentCoord(actualAgent)) == 0)
			if (actualAgent instanceof Hero) {
				keyTaken = true;
				actualAgent.setSymbol('K');
			}
	}

	/** Function to handle with the weapon of Hero;
	 * 	Verify if Hero took the weapon;
	 *
	 * @param agent MovingAgent - that correspond to the Hero to handle;
	 */
	private void weaponHeroHandler(Hero agent){
		if(getAgentCoord(agent).distance(agent.weapon.getCoords()) == 0) {
			agent.weapon.setCoords(getAgentCoord(agent));
			agent.weapon.setSymbol(' ');
			agent.setSymbol('A');
		}
	}

	/** Function to set Ogre stunned if is the case of it;
	 * 	Just stun the Ogre if distance between weapon of Hero and Ogre is under of 1;
	 *
	 * @param actualAgent - MovingAgent that correspond to the Ogre to be set stun;
	 */
	private void ogreStunned(MovingAgent actualAgent) {
        if (actualAgent instanceof Ogre && (agents.get(0).getSymbol() == 'A')){ /* Verify if can be stunned */
            if (getAgentCoord(actualAgent).distance(getAgentCoord(agents.get(0))) <= 1){
                ((Ogre) actualAgent).setStunned();
            }
        }
        else if(actualAgent instanceof Hero && actualAgent.getSymbol() == 'A'){
            MovingAgent agent = null;
            for (int i = 1; i < agents.size(); i++)
                 agent = agents.get(i);
                if (agent instanceof Ogre && getAgentCoord(agent).distance(getAgentCoord(actualAgent)) <= 1){
                    ((Ogre) agent).setStunned();
                }
        }
	}

	/** Function that change doors to stairs;
	 * 	This call the function of GameMap to do it;
	 *
	 * @param doors - Point[] all doors to change to stairs;
	 */
	private void changeDoorsToStairs(Point[] doors){
		map.changeDoorsToStairs(doors);
	}

}
