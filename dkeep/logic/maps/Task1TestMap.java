/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.logic.maps;


public class Task1TestMap extends GameMap{
    public Task1TestMap(){
        map = new char[][] {
            {'X','X','X','X','X'},
            {'X',' ',' ',' ','X'},
            {'I',' ',' ',' ','X'},
            {'I',' ',' ',' ','X'},
            {'X','X','X','X','X'}};
    }

    @Override
    public GameMap nextMap() {
        return new DungeonMap();
    }
}
