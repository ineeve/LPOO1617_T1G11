/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.test;

import dkeep.logic.Game;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ineeve
 */
public class testDungeonGameLogic {
    
    public testDungeonGameLogic() {
    }

    @Test
    public void testMoveHeroToFreeCell(){
        Game game = new Game(0);
        assertEquals(new Point(1,1), game.getHeroPos());
        game.moveHero('s');
        assertEquals(new Point(1,2), game.getHeroPos());
    }
    
    @Test
    public void teHeroIsCapturedByGuard(){
    Game game = new Game(0);
    assertFalse(game.isGameOver());
    game.moveHero('d');
    assertTrue(game.isGameOver());
    }
    
}
