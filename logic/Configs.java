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
    private static final GameMap STARTMAP = new DungeonMap();
    public static int NUMBEROFOGRES = 1;
    public static int GUARDPERSONALITY = 0;
    private Point heroStartPoint;
    private Point guardStartPoint;
    private Point keyStartPoint;

    static int level = 0;
    private ArrayList<MovingAgent> agents = new ArrayList<>();
    private GameMap map;
    private Key key;

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

    public GameMap getMap() {
        return map;
    }

    public void setLevel(int newLevel) {
        level = newLevel;
    }


    public int prepareNextLevel() {
        heroStartPoint = new Point(1, 1);
        guardStartPoint = new Point(3, 1);
        keyStartPoint = new Point(1, 3);
        agents.clear();
        switch (level) {
            case 0:
                prepareTestLevel();
                return 0;
            case 1:
                prepareLevelOne();
                return 0;
            case 2:
                prepareLevelTwo();
                return 0;
            default:
                return 1;
        }
    }

    private void prepareTestLevel() {
        if (key == null) {
            key = new Key(keyStartPoint);
        } else {
            key.setCoord(keyStartPoint);
        }

        map = new Task1TestMap();

        agents.add(new Hero(heroStartPoint));
        agents.add(new Rookie(guardStartPoint));

        level = 1;
    }

    private void prepareLevelOne() {
        heroStartPoint = new Point(1, 1);
        guardStartPoint = new Point(8, 1);
        keyStartPoint = new Point(3, 1);

        key = new Key(new Point(keyStartPoint));
        map = STARTMAP;

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
        heroStartPoint = new Point(1, 7);
        keyStartPoint = new Point(7, 1);
        if (key != null) {
            key.setCoord(keyStartPoint);
        } else {
            key = new Key(keyStartPoint);
        }
        if (map == null) {
            map = new KeepMap();
        } else {
            map = map.nextMap();
        }
        Hero newHero = new Hero(heroStartPoint, 'A', '/');
        agents.add(newHero);
        for (int i = 0; i < NUMBEROFOGRES; i++) {
            int rnX = ThreadLocalRandom.current().nextInt(3, 7);
            int rnY = ThreadLocalRandom.current().nextInt(1, 7);
            agents.add(new Ogre(new Point(rnX, rnY)));
        }

        level = 3;
    }
}
