package dkeep.logic.maps;

/**
 * Derived class from GameMap;
 * This GameMap have a different board;
 */
public class DungeonMap extends GameMap {
    /**
     * Static map that contains information of the map of DungeonMap
     */
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

    /* CONSTRUCTOR */

    /** Constructor of DungeonMap
     *  Set map of super equal to the map of this GameMap;
     */
    public DungeonMap() {
        super.map = DungeonMap.map;
    }

    /* GETTERS */

    /** Function to get the next GameMap;
     *
     * @return GameMap - correspond the next GameMap in relation of this;
     */
    @Override
    public GameMap nextMap() {
        return new KeepMap();
    }
}
