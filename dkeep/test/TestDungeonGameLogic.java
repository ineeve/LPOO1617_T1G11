/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.test;

import dkeep.logic.Game;
import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.Task1TestMap;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDungeonGameLogic {
    char map[][] = {{'X','X','X','X','X'},
                    {'X',' ',' ',' ','X'},
                    {'I',' ',' ',' ','X'},
                    {'I',' ',' ',' ','X'},
                    {'X','X','X','X','X'}};
    
    @Test
    public void testMoveHeroIntoFreeCell(){
        GameMap gameMap = new Task1TestMap(map);
        Game game = new Game(gameMap);
        assertEquals(new Point(1,1),game.getHeroPos());
        game.update();
        assertEquals(new Point(1,2), game.getHeroPos());
    }
}
