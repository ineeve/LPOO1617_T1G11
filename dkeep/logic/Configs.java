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
    /*  CONSTANTS  */
    private static Point KEYSTARTPOS;
    private static Point HEROSTARTPOS;
    private static Point GUARDSTARTPOS;
    private static final GameMap STARTMAP = new DungeonMap();
    public static int NUMBEROFOGRES;
    public static int GUARDPERSONALITY;

    static int level = 0;
    ArrayList<MovingAgent> agents = new ArrayList<>();
    GameMap map;
    Key key;

    public Configs(int startLevel){
        level = startLevel;
    }

    public ArrayList<MovingAgent> getAgents(){
        return agents;
    }

    public Key getKey(){
        return key;
    }

    public GameMap getMap(){
        return map;
    }

    public int prepareNextLevel(){
        agents.clear();
        switch (level){
            case 0:
                HEROSTARTPOS = new Point(1,1);
                GUARDSTARTPOS =  new Point(3,1);
                KEYSTARTPOS = new Point(1,3);
                if (key == null){
                	key = new Key(KEYSTARTPOS);
                }else{
                	key.setCoord(KEYSTARTPOS);
                }
                
                map = new Task1TestMap();

                agents.add(new Hero(HEROSTARTPOS));
                agents.add(new Rookie(GUARDSTARTPOS));

                level = 1;
                return 0;
            case 1:
                HEROSTARTPOS = new Point(1,1);
                GUARDSTARTPOS =  new Point(8,1);
                KEYSTARTPOS  = new Point(3,1);

                key = new Key(new Point(KEYSTARTPOS));
                map = STARTMAP;

                agents.add(new Hero(HEROSTARTPOS));
                switch (GUARDPERSONALITY){
                    case 0:
                    	agents.add(new Drunken(GUARDSTARTPOS));
                        break;
                        
                    case 1:
                    	agents.add(new Rookie(GUARDSTARTPOS));
                        break;
                    case 2:
                        agents.add(new Suspicious(GUARDSTARTPOS));
                        break;
                }

                level = 2;
                return 0;
            case 2:
                HEROSTARTPOS = new Point(1,7);
                KEYSTARTPOS = new Point(7,1);
                if (key != null){
                    key.setCoord(KEYSTARTPOS);
                }
                else{
                	key = new Key(KEYSTARTPOS);
                }
                if(map == null) {
                    map = new KeepMap();
                }
                else{
                    map = map.nextMap();
                }
                Hero newHero = new Hero(HEROSTARTPOS,'A','/');
                agents.add(newHero);
                for (int i = 0; i < NUMBEROFOGRES; i++){
                    int rnX = ThreadLocalRandom.current().nextInt(3, 7);
                    int rnY = ThreadLocalRandom.current().nextInt(1, 7);
                    agents.add(new Ogre(new Point(rnX,rnY)));
                }

                level = 3;
                return 0;
            default:
                return 1;
        }
    }
}
