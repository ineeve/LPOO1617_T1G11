package dkeep.logic.maps;


import java.awt.*;
import java.security.InvalidParameterException;


public abstract class GameMap {
    char[][] map;

    public char[][] getMap() {
        return map;
    }

    /**
     * @param coord Agent coordinates
     * @return 0-Out of map; 1 - ' '; 2 - 'S',3-'I'; 4-'O';
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

    public void changeAllDoorsToStairs() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'I') {
                    map[y][x] = 'S';
                }
            }
        }
    }

    public void editPos(int x, int y, char n) {
        if (y > map.length || y < 0 || x < 0 || x > map[0].length) {
            throw new InvalidParameterException();
        }
        map[x][y] = n;
    }

    public void resize(int newXSize, int newYSize) {
        if (newXSize < 5 || newYSize < 5 || newXSize > 60 || newYSize > 60) {
            throw new InvalidParameterException();
        }
        map = new char[newYSize][newXSize];
        for (int i = 0; i < newYSize; i++) {
            for (int j = 0; j < newXSize; j++) {
                if (i == 0 || i == newYSize - 1 || j == 0 || j == newXSize - 1) {
                    map[i][j] = 'X';
                } else {
                    map[i][j] = ' ';
                }
            }
        }
    }
}

