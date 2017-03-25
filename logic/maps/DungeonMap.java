package dkeep.logic.maps;



/**
 * Created by João on 23/02/2017.
 */
public class DungeonMap extends GameMap {
    private static char[][] map = new char[][]{
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'X', ' ', ' ', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
            {'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
            {'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

    public DungeonMap() {
        super.map = DungeonMap.map;
    }

    @Override
    public GameMap nextMap() {
        return new KeepMap();
    }
}
