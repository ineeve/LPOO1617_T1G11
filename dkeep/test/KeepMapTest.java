/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.test;

import dkeep.logic.Game;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ineeve
 */
public class KeepMapTest {
    
    @Test
    public void testHeroIsKilledByOgre(){
        Game game = new Game(2);
        game.moveHero('d');
        assertFalse(game.isGameOver());
        game.moveHero('w');
        assertTrue(game.isGameOver());
        assertEquals(Game.status.DEFEAT,game.getGameStatus());
    
    }
    
}
