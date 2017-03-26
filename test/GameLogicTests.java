package dkeep.test;

import dkeep.logic.Configs;
import dkeep.logic.Game;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameLogicTests {


	@Test
	public void testHeroMovesToKeyCellAndChangesSymbolToK(){
		System.out.println("Testing Hero moves into the Keep's exit door key cell and changes its representation to 'K'");
		Configs config = new Configs(2);
		config.setKeepHeroKeyWeapon(new Point(2,2), new Point(2,3), new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
        game.resetLevel();
        assertTrue(game.getHero().getSymbol() == 'H');
        game.moveHero('s');
		assertTrue(game.getHero().getSymbol() == 'K');
	}
	
	@Test
	public void movesIntoExitDoorWithoutKeyAndFailsToOpenIt(){
		System.out.println("Testing Hero moves into the closed Keep's exit door, without the key, and fails to open it.");
		Configs config = new Configs(2);
		config.NUMBEROFOGRES = 0;
		config.setKeepHeroKeyWeapon(new Point(1,1), new Point(),new Point());
		Game game = new Game(config);
        game.resetLevel();
		char map[][] = game.getMap();
		assertTrue(map[1][0] == 'I');
		game.moveHero('a');
		map = game.getMap();
		assertTrue(map[1][0] == 'I');
	}

	@Test
	public void HeroMovesToDoorWithKeyAndOpensIt(){
		System.out.println("Hero moves into the closed Keep's exit door, with the key, and the door opens.");
		Configs config = new Configs(2);
		config.setKeepHeroKeyWeapon(new Point(2,1), new Point(1,1),new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
        game.resetLevel();
		char map[][] = game.getMap();
		assertTrue(map[1][0] == 'I');
		game.moveHero('a');
		assertTrue(game.getHero().getSymbol() == 'K');
		game.moveHero('a');
		map = game.getMap();
		assertTrue(map[1][0] == 'S');
	}

	@Test
	public void HeroMovesToOpenDoorAndWinsGame(){
		System.out.println("Hero moves into the open Keep's exit door and the game ends with victory.");
		Configs config = new Configs(2);
		config.setKeepHeroKeyWeapon(new Point(2,1), new Point(1,1),new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		char map[][] = game.getMap();
		assertTrue(map[1][0] == 'I');
		game.moveHero('a');
		assertTrue(game.getHero().getSymbol() == 'K');
		game.moveHero('a');
		map = game.getMap();
		assertTrue(map[1][0] == 'S');
		game.moveHero('a');
		assertTrue(game.gameStatus == Game.status.VICTORY);
	}
	

	@Test(timeout = 2000)
	public void testOgreRandomMovementBehaviour(){
		boolean threeDirectionsDifferent=false,weaponRandom=false;
		Configs config = new Configs(2);
		Game game = new Game(config);
        game.resetLevel();
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

	@Test
	public void testIfHeroIsCapturedByOgre(){
		Configs config = new Configs(2);
		config.setKeepHeroKeyWeapon(new Point(2,2), new Point(),new Point());
		config.setKeepOgreStartPosition(new Point(3,2));
		Game game = new Game(config);
        game.resetLevel();
		game.moveHero('d');
		assertTrue(game.isGameOver());
	}
	
	@Test
	public void testStartPositionOfHero(){
		Configs config = new Configs(2);
		Point heroStartPosition = new Point(2,2);
		config.setKeepHeroKeyWeapon(heroStartPosition, new Point(),new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
	}
	
	@Test
	public void testStartPositionOfKey(){
		Configs config = new Configs(2);
		Point heroStartPosition = new Point(2,2);
		Point keyStartPosition = new Point(3,2);
		config.setKeepHeroKeyWeapon(heroStartPosition, keyStartPosition,new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.key.getCoord().equals(keyStartPosition));
	}
	
	@Test
	public void testStartPositionOfOgre(){
		Configs config = new Configs(2);
		Point ogreStartPosition = new Point(2,2);
		config.setKeepOgreStartPosition(ogreStartPosition);
		config.NUMBEROFOGRES = 1;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getFirstOgre().getAgentCoords().equals(ogreStartPosition));
	}
	
	@Test
	public void testIfHeroStunOgre(){
		Configs config = new Configs(2);
		config.setKeepHeroKeyWeapon(new Point(2,2), new Point(),null);
		config.setKeepOgreStartPosition(new Point(4,2));
		Game game = new Game(config);
		game.resetLevel();
		game.getFirstOgre().weapon.setCoords(new Point());
		game.moveHero('d');
		assertTrue(game.getHero().getSymbol() == 'A');
		game.moveHero('d');
		assertTrue(game.getFirstOgre().isStunned());
	}
	
	//Test Dungeon Map
	@Test
	public void testMoveHeroToFreeCell(){
		System.out.println("Testing Move Hero to Free Cell");
		Configs config = new Configs(0);
		Game game = new Game(config);
        game.resetLevel();
		Hero actualHero = game.getHero();
		assertEquals(new Point(1,1), actualHero.getAgentCoords());
		game.moveHero('s');
		assertEquals(new Point(1,2), actualHero.getAgentCoords());
	}

	@Test
	public void testMoveHeroToRight(){
		Configs config = new Configs(2);
		Point heroStartPosition = new Point(2,2);
		config.setKeepHeroKeyWeapon(heroStartPosition, new Point(),new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
		game.moveHero('d');
		heroStartPosition.x++;
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
	}
	
	@Test
	public void testMoveHeroToLeft(){
		Configs config = new Configs(2);
		Point heroStartPosition = new Point(2,2);
		config.setKeepHeroKeyWeapon(heroStartPosition, new Point(),new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
		game.moveHero('a');
		heroStartPosition.x--;
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
	}
	
	@Test
	public void testMoveHeroToUp(){
		Configs config = new Configs(2);
		Point heroStartPosition = new Point(2,2);
		config.setKeepHeroKeyWeapon(heroStartPosition, new Point(),new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
		game.moveHero('w');
		heroStartPosition.y--;
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
	}

	@Test
	public void testMoveHeroToDown(){
		Configs config = new Configs(2);
		Point heroStartPosition = new Point(2,2);
		config.setKeepHeroKeyWeapon(heroStartPosition, new Point(),new Point());
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
		game.moveHero('s');
		heroStartPosition.y++;
		assertTrue(game.getHero().getAgentCoords().equals(heroStartPosition));
	}
	
	@Test
	public void decreaseLevel(){
		Configs config = new Configs(2);
		assertTrue(config.getLevel() == 2);
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(config.getLevel() == 3);
		assertTrue(game.getLevel() == 3);
		config.decreaseLevel();
		assertTrue(config.getLevel() == 2);
		game.resetLevel();
		assertTrue(config.getLevel() == 3);
		assertTrue(game.getLevel() == 3);
	}
	
	@Test
	public void testNumberOfOgres(){
		Configs config = new Configs(2);
		config.NUMBEROFOGRES = 0;
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getFirstOgre()==null);
		config.decreaseLevel();
		config.NUMBEROFOGRES = 1;
		game.resetLevel();
		assertTrue(game.getFirstOgre()!=null);
	}
	
	@Test
	public void testIfOgreChangeSymbolWhenTookKey(){
		Configs config = new Configs(2);
		config.NUMBEROFOGRES = 1;
		Point keyStart = new Point(3,3);
		Point ogreStart = new Point(2,3);
		config.setKeepHeroKeyWeapon(null, keyStart, null);
		config.setKeepOgreStartPosition(ogreStart);
		Game game = new Game(config);
		game.resetLevel();
		assertTrue(game.getFirstOgre().getAgentCoords() == ogreStart);
		assertTrue(game.key.getCoord() == keyStart);
		char[][] map = game.getMap();
		assertTrue(map[keyStart.y][keyStart.x] == 'k');
		game.getFirstOgre().nextPos('d');
		assertTrue(game.key.getCoord() == keyStart);
		map = game.getMap();
		assertTrue(map[keyStart.y][keyStart.x] == '$');
	}

	@Test
	public void initializationOfLevel(){
		Configs config = new Configs(2);
		Game game = new Game(config);
		assertTrue(game.getHero() == null);
		assertTrue(game.getFirstOgre() == null);
		assertTrue(game.getMap() == null);
		game.resetLevel();
		assertTrue(game.getHero() != null);
		assertTrue(game.getFirstOgre() != null);
		assertTrue(game.getMap() != null);
	}

	@Test
	public void setLevelOnConfigs(){
		Configs config = new Configs(2);
		assertTrue(config.getLevel() == 2);
		config.setLevel(1);
		assertTrue(config.getLevel() == 1);
	}

	@Test
	public void getLevelOfConfigs(){
		Configs config = new Configs(1);
		assertTrue(config.getLevel() == 1);
	}


}
