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
    private int numberOfOgres = 1;
    private int guardPersonality = 0;
    private Point heroStartPoint;
    private Point guardStartPoint;
    private Point keyStartPoint;

    private int level = 0;
    private ArrayList<MovingAgent> agents = new ArrayList<>();
    private GameMap map;
    private Key key;

    public Configs(int startLevel) {
        level = startLevel;
    }

    public void decreaseLevel() {
        level--;
    }

    public int getLevel() {
        return level;
    }

    public void setNumberOfOgres(int nOgres) {
        this.numberOfOgres = nOgres;
    }

    public void setGuardPersonality(int gPersonality) {
        this.guardPersonality = gPersonality;
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

    public void prepareNextLevel() {
        heroStartPoint = new Point(1, 1);
        guardStartPoint = new Point(3, 1);
        keyStartPoint = new Point(1, 3);
        agents.clear();
        switch (level) {
            case 0:
                prepareTestLevel();
                return;
            case 1:
                prepareLevelOne();
                return;
            case 2:
                prepareLevelTwo();
                return;
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
        map = new DungeonMap();

        agents.add(new Hero(heroStartPoint));
        switch (guardPersonality) {
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

        map = new KeepMap();

        Hero newHero = new Hero(heroStartPoint, 'A', '/');
        agents.add(newHero);
        for (int i = 0; i < numberOfOgres; i++) {
            int rnX = ThreadLocalRandom.current().nextInt(3, 7);
            int rnY = ThreadLocalRandom.current().nextInt(1, 7);
            agents.add(new Ogre(new Point(rnX, rnY)));
        }

        level = 3;
    }
}
