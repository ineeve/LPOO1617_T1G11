/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.test;

import dkeep.logic.Game;
import org.junit.Test;
import static org.junit.Assert.*;


public class KeepMapTest {

	@Test
	public void testHeroIsKilledByOgre(){
		System.out.println("Test that Hero dies if it is adjacent to Ogre");
		Game game = new Game(2);
		game.moveHero('d');
		game.moveHero('d');
		game.moveHero('d');
		assertFalse(game.isGameOver());
		game.moveHero('w');
		game.moveHero('w');
		game.moveHero('w');
		assertTrue(game.isGameOver());
		assertEquals(Game.status.DEFEAT,game.getGameStatus());

	}

	public void testHeroMovesToKeyCellAndChangesSymbolToK(){
		System.out.println("Test Hero moves into the Keep's exit door key cell and changes its representation to 'k'");
		Game game = new Game(2);
		for (int i = 0 ; i <3;i++){
			game.moveHero('w');
		}

	}


}
