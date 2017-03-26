/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.logic.maps;

/**
 * Derived class from GameMap;
 * This GameMap have a different board for test the game with unity test;
 */
public class Task1TestMap extends GameMap{
    /* CONSTRUCTOR */

    /** Constructor of Task1TestMap;
     *  Set board to the specify board;
     */
    public Task1TestMap(){
        map = new char[][] {
            {'X','X','X','X','X'},
            {'X',' ',' ',' ','X'},
            {'I',' ',' ',' ','X'},
            {'I',' ',' ',' ','X'},
            {'X','X','X','X','X'}};
    }

    /** Function to get the next GameMap;
     *
     * @return GameMap - correspond the next GameMap in relation of this;
     */
    @Override
    public GameMap nextMap() {
        return new DungeonMap();
    }
}
