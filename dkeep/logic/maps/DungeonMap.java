package dkeep.logic.maps;

import dkeep.cli.UserInput;
import dkeep.logic.Hero;
import dkeep.logic.Rookie;
import java.awt.Point;

/**
 * Created by João on 23/02/2017.
 */
public class DungeonMap extends GameMap {
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
    }

    @Override
    public GameMap nextMap() {
       
        return new KeepMap();
    }
}
