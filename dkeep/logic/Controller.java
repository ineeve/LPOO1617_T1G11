package dkeep.logic;

/*public class Controller {

	public static boolean runLevel(int currentLevel) {
		// Initialize Variables
		char[][] board = createLevel(currentLevel);
		Hero myHero = new Hero(board);
		Guard theGuard = null;
		Ogre theOgre = null;
		if (currentLevel == 1){
			theGuard = new Guard(board);
		}
		else if (currentLevel == 2){
			theOgre = new Ogre(board);
		}
		View.displayBoard(board);
		char userInput;
		// Run Game
		while (true) {
			String status = null;
			userInput = getUserInput();
			cleanClub(board);
			myHero.move(board, userInput,currentLevel);
				if (theGuard != null){
					status = checkHeroPosition(board, myHero, theGuard);
					View.updateBoard(board, myHero, theGuard);
				}
				else if (theOgre != null){
					status = checkHeroPosition(board,myHero,theOgre);
					View.updateBoard(board, myHero, theOgre);
				}
			
			View.displayBoard(board);
			
			if (status.equals("Game Over")) {
				return false;
			}
			else if (status.equals("Victory")){
				return true;
			}
			if (theGuard != null){
				theGuard.moveGuard(board,currentLevel);
				status = checkHeroPosition(board, myHero, theGuard);
				View.updateBoard(board, myHero, theGuard);
			}
			else if (theOgre != null){
				theOgre.moveOgre(board,currentLevel);
				View.updateBoard(board, myHero, theOgre);
				theOgre.swingClub(board);
				status = checkHeroPosition(board, myHero, theOgre);
				View.updateBoard(board, myHero, theOgre);
			}
			
			View.displayBoard(board);

			if (status.equals("Game Over")) {
				return false;
			}
			else if (status.equals("Victory")){
				return true;
			}
		}
	}
}
*/