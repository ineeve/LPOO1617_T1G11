package dkeep.logic.maps;


import java.awt.*;


public abstract class GameMap {
    char[][] map;

    public char[][] getMap() {
        return map;
    }

    /**
     * @param coord Agent coordinates
     * @return 0-Out of map; 1 - ' '; 2 - 'S',3-'I';
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

    public GameMap nextMap() {
        return new Task1TestMap();
    }

    public void changeDoorsToStairs(Point[] doors) {
        for (int i = 0; i < doors.length;i++){
        	map[doors[i].y][doors[i].x] = 'S';
        }
    }

    
   
    
}

