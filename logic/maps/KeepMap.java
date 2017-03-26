package dkeep.logic.maps;

import java.security.InvalidParameterException;

/**
 * Derived class from GameMap;
 * This GameMap have a different board;
 */
public class KeepMap extends GameMap {
    /**
     * Static map that contains information of the map of KeepMap
     */
    private static char[][] mapStatic = new char[][]{
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}};

    /* CONSTRUCOTR */

    /** Constructor of KeepMap
     *  Set map of super equal to the map of this GameMap;
     */
    public KeepMap() {
        super.map = KeepMap.mapStatic;
    }


    /** Function to get the next GameMap;
     *
     * @return GameMap - correspond the next GameMap in relation of this;
     */
    @Override
    public GameMap nextMap() {
        return null;
    }

    /** Function to create a copy of mapStatic
     *  The copy can be different if the size of tempMap if different of mapStatic;
     *
     * @param tempMap - char[][] that is the array to create a copy;
     */
    public static void copyMap(char[][] tempMap) {
        for (int i = 0; i < mapStatic.length; i++) {
            if (i >= tempMap.length) {
                break;
            }
            for (int j = 0; j < mapStatic[0].length; j++) {
                if (j >= tempMap[0].length) {
                    break;
                }
                tempMap[i][j] = mapStatic[i][j];
            }
        }
    }

    /** Function to resize the map;
     *  Will resize the map maintaining the data of the map;
     *
     * @param newXSize - int that correspond to new number of columns;
     * @param newYSize - int that correspond to new number of lines;
     */
    public static void resize(int newXSize, int newYSize) {
        if (newXSize == mapStatic[0].length && newYSize == mapStatic.length) {
            return;
        }
        char[][] tempMap = new char[newYSize][newXSize];
        copyMap(tempMap);
        for (int i = 0; i < newYSize; i++) {
            for (int j = 0; j < newXSize; j++) {
                if (tempMap[i][j] == 0) {
                    tempMap[i][j] = ' ';
                }
            }
        }
        mapStatic = tempMap;
    }

    /** Function to edit one specified cell of map;
     *
     * @param x - int that correspond to the column number of cell to change;
     * @param y - int that correspond to the line number of cell to change;
     * @param n - char that will save in the specified cell to change;
     */
    public void editPos(int x, int y, char n) {
        if (y > map.length || y < 0 || x < 0 || x > map[0].length) {
            throw new InvalidParameterException();
        }
        map[x][y] = n;
    }

}
