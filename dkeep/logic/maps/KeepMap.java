package dkeep.logic.maps;

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
