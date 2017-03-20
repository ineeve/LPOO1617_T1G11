/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.test;

import dkeep.logic.Configs;
import dkeep.logic.Game;
import dkeep.logic.Game.status;
import dkeep.logic.Hero;

import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;


public class DungeonGameLogicTest {

	public DungeonGameLogicTest() {
	}

	@Test
	public void testMoveHeroToFreeCell(){
		System.out.println("Testing Move Hero to Free Cell");
		Game game = new Game();
		Configs config = new Configs(0);
		config.prepareNextLevel();
		game.setMap(config.getMap());
        game.setAgents(config.getAgents());
        game.setKey(config.getKey());
        game.setKeyTaken(false);
        game.gameStatus = Game.status.PLAYING;
		Hero actualHero = game.getHero();
		assertEquals(new Point(1,1), actualHero.getAgentCoords());
		game.moveHero('s');
		assertEquals(new Point(1,2), actualHero.getAgentCoords());
	}

	@Test
	public void testHeroIsCapturedByGuard(){
		System.out.println("HeroIsCapturedByGuard");
		Game game = new Game();
		Configs config = new Configs(0);
		config.prepareNextLevel();
		game.setMap(config.getMap());
        game.setAgents(config.getAgents());
        game.setKey(config.getKey());
        game.setKeyTaken(false);
        game.gameStatus = Game.status.PLAYING;
		assertFalse(game.isGameOver());
		game.moveHero('d');
		assertTrue(game.isGameOver());
		assertEquals(status.DEFEAT, game.getGameStatus());
	}
	
	
	
}
