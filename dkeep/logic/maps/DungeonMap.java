package dkeep.logic.maps;

import dkeep.logic.Hero;
import dkeep.logic.Rookie;
import java.awt.Point;

/**
 * Created by João on 23/02/2017.
 */
public class DungeonMap extends GameMap {
    private static final Point heroPos = new Point(1,1);
    private static Point guardPos = new Point(8,1);
    public DungeonMap() {
        map = new char[][] {
                { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
                { 'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
                { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
                { 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
                { 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
                { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
                { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
                { 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
                { 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
                { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
        
        agents.add(new Hero(heroPos));
        //agents.add(new Guard(new Point(8,1)));
        //agents.add(new Drunken(new Point(8, 1)));
        agents.add( new Rookie(new Point(8,1)));
        //agents.add( new Suspicious(guardPos));
    }

    @Override
    public GameMap nextMap() {
        return new KeepMap();
    }
}
