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
import dkeep.logic.Ogre;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Point;


public class KeepMapTest {

	@Test
	public void testHeroIsKilledByOgre(){
		System.out.println("Testing Hero dies if it is adjacent to Ogre");
		Game game = new Game();
		Configs config = new Configs(2);
		config.prepareNextLevel();
		game.setMap(config.getMap());
        game.setAgents(config.getAgents());
        game.setKey(config.getKey());
        game.setKeyTaken(false);
        game.gameStatus = Game.status.PLAYING;
        game.getFirstOgre().setAgentCoords(new Point(4,3));
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

	@Test
	public void testHeroMovesToKeyCellAndChangesSymbolToK(){
		System.out.println("Testing Hero moves into the Keep's exit door key cell and changes its representation to 'K'");
		Game game = new Game(2);
		Hero theHero = game.getHero();
		assertTrue(theHero.getSymbol() == 'A');
		for (int i = 0 ; i <6;i++){
			game.moveHero('d');
		}
		for (int i = 0; i < 6;i++){
			game.moveHero('w');
		}

		assertTrue(theHero.getSymbol() == 'K');

	}
	
	@Test
	public void movesIntoExitDoorWithoutKeyAndFailsToOpenIt(){
		System.out.println("Testing Hero moves into the closed Keep's exit door, without the key, and fails to open it.");
		Game game = new Game(2);
		for (int i = 0; i < 6; i++){
			game.moveHero('w');
		}
		game.moveHero('a');
		char map[][] = game.getMap();
		assertTrue(map[1][0] == 'I');
	}

	@Test
	public void HeroMovesToDoorWithKeyAndOpensIt(){
		System.out.println("Hero moves into the closed Keep's exit door, with the key, and the door opens.");
		Game game = new Game(2);
		for (int i = 0 ; i <6;i++){
			game.moveHero('d');
		}
		for (int i = 0; i < 6;i++){
			game.moveHero('w');
		}
		for (int i = 0; i < 7; i++){
			game.moveHero('a');
		}
		char map[][] = game.getMap();
		assertTrue(map[1][0] == 'S');
	}
	
	@Test
	public void HeroMovesToOpenDoorAndWinsGame(){
		System.out.println("Hero moves into the open Keep's exit door and the game ends with victory.");
		Game game = new Game(2);
		for (int i = 0 ; i <6;i++){
			game.moveHero('d');
		}
		for (int i = 0; i < 6;i++){
			game.moveHero('w');
		}
		for (int i = 0; i < 8; i++){
			game.moveHero('a');
		}
		assertTrue(game.getGameStatus() == status.VICTORY);
	}
	
	
	@Test(timeout = 1000)
	public void testOgreRandomMovementBehaviour(){
		boolean threeDirectionsDifferent=false,weaponRandom=false;
		
		Game game = new Game(2);
		Ogre theOgre = game.getFirstOgre();
		char ogreDirections[] = new char [4];
		char ogreWeaponDirections[] = new char[4];
		game.moveHero('w');
		game.moveHero('d');
		while(!threeDirectionsDifferent || !weaponRandom){
			for (int i = 0; i < 4; i++){
				ogreDirections[i] = theOgre.getNextDirection();
				ogreWeaponDirections[i] = theOgre.weapon.getNextDirection();
			}
			
			if (ogreDirections[0] != ogreDirections[1] && ogreDirections[1] != ogreDirections[2] && ogreDirections[2] != ogreDirections[3]){
				threeDirectionsDifferent = true;
			}
			if (ogreWeaponDirections[0] != ogreWeaponDirections[1] && ogreWeaponDirections[1] != ogreWeaponDirections[2] && ogreWeaponDirections[2] != ogreWeaponDirections[3]){
				weaponRandom = true;
			}
			
		}
	}
	
	@Test(timeout =2000)
	public void testHeroIsEventuallyCapturedByOgre(){
		Game game = new Game(2);
		Hero theHero = game.getHero();
		game.moveHero('w');
		game.moveHero('d');
		while(!game.isGameOver()){
			game.moveAgent(theHero, theHero.getRandomDirection());
			game.moveOgres();
		}
	}
	
	@Test
	public void testMoveHeroToFreeCell(){
		System.out.println("Testing Move Hero to Free Cell");
		Game game = new Game(0);
		Hero actualHero = game.getHero();
		assertEquals(new Point(1,1), actualHero.getAgentCoords());
		game.moveHero('s');
		assertEquals(new Point(1,2), actualHero.getAgentCoords());
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
