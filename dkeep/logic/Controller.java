package dkeep.logic;

import java.util.Scanner;

public class Controller {

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
	
	private static void cleanClub(char [][]board) {
		for (int i = 0; i < board.length;i++){
			for (int k = 0; k < board[i].length;k++){
				if (board[i][k] == '*'){
					board[i][k] = ' ';
					return;
				}
			}
		}
		
	}

	private static boolean isEnemyClose(Hero myHero, MovingAgent theEnemy) {
		int[] heroCoords = myHero.getAgentCoords();
		int[] enemyCoords = theEnemy.getAgentCoords();
		int[] distanceVector = new int[] { enemyCoords[0] - heroCoords[0], enemyCoords[1] - heroCoords[1] };
		if (Math.sqrt(distanceVector[0] * distanceVector[0] + distanceVector[1] * distanceVector[1]) < 1.1) {
			return true;
		}
		return false;
	}

	public static String checkHeroPosition(char[][] board, Hero myHero, MovingAgent theGuard) {
		int[] heroCoords = myHero.getAgentCoords();
		String res = "";
		if (board[heroCoords[1]][heroCoords[0]] == 'S') {
			res = "Victory";
		} else if (isEnemyClose(myHero, theGuard)) {
			res = "Game Over";
		}

		return res;
	}

	public static void changeAllDoorsToStairs(char[][] board) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == 'I') {
					board[y][x] = 'S';
				}
			}
		}
	}
	




	public static char getUserInput() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Next Move: ");
		char input = reader.next().charAt(0);
		return input;
	}

}
