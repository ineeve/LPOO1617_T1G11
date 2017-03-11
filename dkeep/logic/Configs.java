package dkeep.logic;

import dkeep.logic.maps.DungeonMap;
import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.KeepMap;
import dkeep.logic.maps.Task1TestMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by João on 05/03/2017.
 */
public class Configs {
    /*  CONSTANTS  */
    private static Point KEYSTARTPOS;
    private static Point HEROSTARTPOS;
    private static Point GUARDSTARTPOS;
    private static final GameMap STARTMAP = new DungeonMap();

    static int level = 0;
    ArrayList<MovingAgent> agents = new ArrayList<>();
    GameMap map;
    Key key;

    public Configs(int startLevel){
        level = startLevel;
        this.prepareNextLevel();
    }

    ArrayList<MovingAgent> getAgents(){
        return agents;
    }

    Key getKey(){
        return key;
    }

    GameMap getNextMap(){
        return map;
    }

    int prepareNextLevel(){
        agents.clear();
        switch (level){
            case 0:
                HEROSTARTPOS = new Point(1,1);
                GUARDSTARTPOS =  new Point(3,1);
                KEYSTARTPOS = new Point(1,3);

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
                //agents.add(new Guard(GUARDSTARTPOS));
                agents.add( new Rookie(GUARDSTARTPOS));
                //agents.add(new Guard(GUARDSTARTPOS));
                //agents.add(new Drunken(GUARDSTARTPOS));
                //agents.add( new Suspicious(GUARDSTARTPOS));;

                level = 2;
                return 0;
            case 2:
                HEROSTARTPOS = new Point(1,7);
                KEYSTARTPOS = new Point(7,1);
                
                //key.setCoord(KEYSTARTPOS);
                if(map == null) {
                    map = new KeepMap();
                }
                else{
                    map = map.nextMap();
                }
                Hero newHero = new Hero(HEROSTARTPOS,'A','/');
                
                agents.add(newHero);
                agents.add(new Ogre(new Point(4,3)));
                /* Ogres */
                System.out.println("How many Ogres do you wish to fight?");
                /*int numOgres = UserInput.getInt();
                for (int i = 0; i < numOgres; i++){
                    int rnX = ThreadLocalRandom.current().nextInt(3, 7);
                    int rnY = ThreadLocalRandom.current().nextInt(1, 7);
                    agents.add(new Ogre(new Point(rnX,rnY)));
                }*/

                level = 3;
                return 0;
            default:
                return 1;
        }
    }
}
