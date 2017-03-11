/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.test;

import dkeep.logic.Game;
import dkeep.logic.Game.status;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;


public class DungeonGameLogicTest {
    
    public DungeonGameLogicTest() {
    }

    @Test
    public void testMoveHeroToFreeCell(){
        System.out.println("Testing Move Hero to Free Cell");
        Game game = new Game(0);
        assertEquals(new Point(1,1), game.getHeroPos());
        game.moveHero('s');
        assertEquals(new Point(1,2), game.getHeroPos());
    }
    
    @Test
    public void testHeroIsCapturedByGuard(){
    	System.out.println("HeroIsCapturedByGuard");
    Game game = new Game(0);
    assertFalse(game.isGameOver());
    game.moveHero('d');
    assertTrue(game.isGameOver());
    assertEquals(status.DEFEAT, game.getGameStatus());
    }
}
