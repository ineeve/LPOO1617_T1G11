package dkeep.logic.maps;


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
