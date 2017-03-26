package dkeep.logic.maps;


import java.awt.*;

/**
 * Abstract class that have information of differents playable maps;
 */
public abstract class GameMap {
	/** An array with arrays of chars representative of the map. Should only contain Walls('X') and Doors('I') */
	protected char[][] map;

    /* GETTERS */

    /** Function to get the map;
     *
     * @return char[][] - that correspond to the map of each GameMap;
     */
    public char[][] getMap() {
        return map;
    }

    /** Function to get the next GameMap;
     *
     * @return GameMap - correspond the next GameMap in this case the first GameMap;
     */
    public GameMap nextMap() {
        return new Task1TestMap();
    }


    /** Function that change doors to stairs;
     *
     * @param doors - Point[] that correspond all doors that is to change to stairs
     */
    public void changeDoorsToStairs(Point[] doors) {
        for (int i = 0; i < doors.length;i++){
        	map[doors[i].y][doors[i].x] = 'S';
        }
    }

    /** Function that verify the symbol is one specify position;
     *
     * @param coord - Point that correspond to the MovingAgent position;
     * @return int - 0-Out of map; 1 - ' '; 2 - 'S'; 3 - 'I';
     */
    public int isFree(Point coord) {
        if (coord.y >= map.length || coord.y < 0) {
            return 0;
        } else if (coord.x >= map[coord.y].length || coord.x < 0) {
            return 0;
        } else if ('X' == map[coord.y][coord.x]) {
            return 0;
        } else if ('I' == map[coord.y][coord.x]) {
            return 3;
        } else if (' ' == map[coord.y][coord.x]) {
            return 1;
        } else if ('S' == map[coord.y][coord.x]) {
            return 2;
        } else {
            return 0;
        }
    }
    
}

