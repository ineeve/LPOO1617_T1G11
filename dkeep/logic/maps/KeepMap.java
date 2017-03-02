package dkeep.logic.maps;

import dkeep.logic.Hero;
import java.awt.Point;

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
        
        agents.add(new Hero(heroPos));
    }

    @Override
    public GameMap nextMap() {
        return null;
    }
}
