package dkeep.logic.maps;


public class KeepMap extends GameMap{
    private static char[][] map = new char[][] {
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
            { 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
            { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

    public KeepMap() {
        super.map = map;
    }

    @Override
    public GameMap nextMap() {
        return null;
    }
    
}
