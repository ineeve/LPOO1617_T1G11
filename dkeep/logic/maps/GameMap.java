package dkeep.logic.maps;


import java.awt.*;


public abstract class GameMap {
    protected char[][] map;
    
    public char[][] getMap() {
        return map;
    }
    
    public void setMap(char[][] map) {
        this.map = map;
    }
    /**
     * 
     * @param coord Agent coordinates
     * @return 0-Out of map; 1 - ' '; 2 - 'S',3-'I'; 4-'O';
     */
    public int isFree(Point coord){
        if(coord.y >= map.length || coord.y < 0){
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
        } else if ('O' == map[coord.y][coord.x]){
            return 4;
        } else {
            return 0;
        }
    }
    
    public abstract GameMap nextMap();
    
    public void changeAllDoorsToStairs() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'I') {
                    map[y][x] = 'S';
                }
            }
        }
    }
    
    
}
