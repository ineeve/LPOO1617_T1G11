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
    }
    
    @Override
    public GameMap nextMap() {
        return null;
    }
}
