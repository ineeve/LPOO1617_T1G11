package dkeep.logic.maps;

import dkeep.logic.MovingAgent;
import java.awt.*;
import java.util.ArrayList;
/**
 * Created by João on 23/02/2017.
 */
public abstract class GameMap {
    protected ArrayList<MovingAgent> agents = new ArrayList();
    protected char[][] map;
    
    public char[][] getMap() {
        return map;
    }
    
    public void setMap(char[][] map) {
        this.map = map;
    }
    public ArrayList<MovingAgent> getAgents(){
        return agents;
    }
    
    public int isFree(Point coord){
        if(coord.y >= map.length && coord.y < 0){
            return 0;
        } else if (coord.x >= map[coord.y].length && coord.x < 0) {
            return 0;
        } else if ('I' == map[coord.y][coord.x]) {
            return 0;
        } else if (' ' == map[coord.y][coord.x]) {
            return 1;
        } else if ('S' == map[coord.y][coord.x]) {
            return 2;
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
