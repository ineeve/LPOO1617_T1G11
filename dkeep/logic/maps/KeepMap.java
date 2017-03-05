package dkeep.logic.maps;


import dkeep.cli.UserInput;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;



/**
 * Created by João on 23/02/2017.
 */
public class KeepMap extends GameMap{
    private static final Point heroPos = new Point(1,7);
    public KeepMap() {
        map = new char[][] {
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
        key.setCoord(new Point(7,1));
        agents.add(new Hero(heroPos));
        System.out.println("How many Ogres do you wish to fight?");
        int numOgres = UserInput.getInt();
        for (int i = 0; i < numOgres; i++){
            int rnX = ThreadLocalRandom.current().nextInt(3, 7);
            int rnY = ThreadLocalRandom.current().nextInt(1, 7);
            agents.add(new Ogre(new Point(rnX,rnY)));
        }
    }
    
    @Override
    public GameMap nextMap() {
        return null;
    }
}
