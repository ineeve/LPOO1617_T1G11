package dkeep.logic.maps;

/**
 * Created by João on 23/02/2017.
 */
public abstract class GameMap {
    protected char[][] map;

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public boolean isFree(int x, int y){
        if(y >= map.length && y < 0){
            return false;
        }
        if(x >= map[y].length && x < 0){
            return false;
        }
        if(' ' == map[y][x]){
            return true;
        }
        return false;
    }

    abstract GameMap nextMap();
}
