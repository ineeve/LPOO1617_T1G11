package dkeep.logic;

import dkeep.logic.maps.DungeonMap;
import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.KeepMap;
import dkeep.logic.maps.Task1TestMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by João on 05/03/2017.
 */

public class Configs {
    public int NUMBEROFOGRES = 1;
    public int GUARDPERSONALITY = 0;
    private Point heroStartPoint;
    private Point guardStartPoint;
    private Point keyStartPoint;
    private Point leverStartPoint;

    private int level = 0;
    private ArrayList<MovingAgent> agents = new ArrayList<>();
    private GameMap map;
    private Key key;
    private Lever lever;

    public Configs(int startLevel) {
        level = startLevel;
    }

    public void decreaseLevel() {
        level--;
    }

    public ArrayList<MovingAgent> getAgents() {
        return agents;
    }

    public Key getKey() {
        return key;
    }
    public Lever getLever(){
    	return lever;
    }
    public GameMap getMap() {
        return map;
    }

    public void setLevel(int newLevel) {
        level = newLevel;
    }

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

    private void prepareTestLevel() {
    	key = null;
    	heroStartPoint = new Point(1, 1);
        guardStartPoint = new Point(3, 1);
        leverStartPoint = new Point(1,3);
        Point[] openableDoors = new Point[]{new Point(0,1),new Point(1,2)};
        lever = new Lever(leverStartPoint,openableDoors);
        map = new Task1TestMap();
        agents.add(new Hero(heroStartPoint));
        agents.add(new Rookie(guardStartPoint));
        level = 1;
    }

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
