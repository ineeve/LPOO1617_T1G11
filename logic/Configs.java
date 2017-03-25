package dkeep.logic;

import dkeep.logic.maps.DungeonMap;
import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.KeepMap;
import dkeep.logic.maps.Task1TestMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class responsible for configuration of all levels;
 */
public class Configs {
    /**Contains information of number of ogres how will play on keep level;*/
    public int NUMBEROFOGRES = 1;
    /**Contains information of that is guard personalty;*/
    public int GUARDPERSONALITY = 0;
    /**Contains information of start position of hero for every level;*/
    private Point heroStartPoint;
    /**Contains information of start position of guard for dungeon level;*/
    private Point guardStartPoint;
    /**Contains information of start position of key for every level;*/
    private Point keyStartPoint;
    /**Contains information of start position of lever for dungeon level;*/
    private Point leverStartPoint;
    /**Contains information about current level;*/
    private int level = 0;
    /**Contains information of all present movingAgents in current level;*/
    private ArrayList<MovingAgent> agents = new ArrayList<>();
    /**Contains the current GameMap;*/
    private GameMap map;
    /**Contains information about the key in current level, if level doesn't have one, lever is null;*/
    private Key key;
    /**Contains information about the lever in current level, if level doesn't have one, lever is null;*/
    private Lever lever;

    /* CONSTRUCTOR */
    /** Constructor of Class Configs;
     *
     * @param startLevel - int to define start level (0 - For test level; 1 - Dungeon Level; 2 - Keep Level;);
     */
    public Configs(int startLevel) {
        level = startLevel;
    }


    /* GETTER's */
    /** Function to get current level configured;
     *
     * @return Int value that correspond to the current level (0 - For test level; 1 - Dungeon Level; 2 - Keep Level;);
     */
    public int getLevel(){
        return level;
    }

    /** Function to get the MovingAgents for the current level configured;
     *
     * @return ArrayList<MovingAgent> that contains all MovingAgent to the current level configured;
     */
    public ArrayList<MovingAgent> getAgents() {
        return agents;
    }

    /** Function to get the GameMap for rhe current level configured;
     *
     * @return GameMap that contains the current GameMap configured;
     */
    public GameMap getMap() {
        return map;
    }

    /** Function to get the Key for the current level configured;
     *
     * @return Key that contains the current key configured;
     */
    public Key getKey() {
        return key;
    }

    /** Function to get the Lever for the current level configured;
     *
     * @return Lever that contains the current lever configured;
     */
    public Lever getLever(){
        return lever;
    }


    /* SETTERS'S */

    /** Function to set level for next configuration;
     *
     * @param newLevel int - that correspond the next level to be configured (0 - For test level; 1 - Dungeon Level; 2 - Keep Level;);
     */
    public void setLevel(int newLevel) {
        level = newLevel;
    }

    /* OTHERS METHODS */

    /** Function to decrease level;
     *  This is necessary when the player dies and next level to be configured needs to be the same that was played previously;
     */
    public void decreaseLevel() {
        level--;
    }

    /** Function to prepare next level to be played;
     *  This function initialize all components necessarily to play the level;
     *  Creates all MovingsAgents with your's start conditions;
     *      For Guard depend of GUARDPERSONALITY (0 - Rookie; 1 - Drunken; 2 - Suspicious);
     *      For Ogres depend of NUMBEROFOGRES that define how many ogres are created;
     *  And set next level to be configured;
     */
    public void prepareNextLevel() {
        agents.clear();
        switch (level) {
            case 0:
                prepareTestLevel();
                break;
            case 1:
                prepareLevelOne();
                break;
            case 2:
                prepareLevelTwo();
                break;
        }
    }

    /** Function to prepare Test Level;
     *  This function initialize all components necessarily to play the Test Level;
     */
    private void prepareTestLevel() {
    	key = null;
    	heroStartPoint = new Point(1, 1);
        guardStartPoint = new Point(3, 1);
        leverStartPoint = new Point(1,3);
        Point[] openableDoors = new Point[]{new Point(0,2),new Point(0,3)};
        lever = new Lever(leverStartPoint,openableDoors);
        map = new Task1TestMap();
        agents.add(new Hero(heroStartPoint));
        agents.add(new Rookie(guardStartPoint));
        level = 1;
    }

    /** Function to prepare Dungeon Level;
     *  This function initialize all components necessarily to play the Dungeon Level;
     */
    private void prepareLevelOne() {
    	key = null;
    	Point[] openableDoors = new Point[]{new Point(0,5),new Point(0,6)};
        heroStartPoint = new Point(1, 1);
        guardStartPoint = new Point(8, 1);
        leverStartPoint = new Point(7,8);
        lever = new Lever(leverStartPoint,openableDoors);
        map = new DungeonMap();
        agents.add(new Hero(heroStartPoint));
        switch (GUARDPERSONALITY) {
            case 0:
                agents.add(new Rookie(guardStartPoint));
                break;
            case 1:
                agents.add(new Drunken(guardStartPoint));
                break;
            case 2:
                agents.add(new Suspicious(guardStartPoint));
                break;
        }
        level = 2;
    }

    /** Function to prepare Keep Level
     *  This function initialize all components necessarily to play the Keep Level;
     */
    private void prepareLevelTwo() {
    	lever = null;
        heroStartPoint = new Point(1, 7);
        keyStartPoint = new Point(7, 1);
        key = new Key(keyStartPoint,new Point(0,1));
        map = new KeepMap();
        Hero newHero = new Hero(heroStartPoint, 'A', '/');
        agents.add(newHero);
        for (int i = 0; i < NUMBEROFOGRES; i++) {
           Point ogrePoint = new Point(ThreadLocalRandom.current().nextInt(1,map.getMap()[0].length-1),ThreadLocalRandom.current().nextInt(1, map.getMap().length-1));
           if (ogrePoint.distance(heroStartPoint) > 3){agents.add(new Ogre(ogrePoint));}
        }
        level = 3;
    }
}
