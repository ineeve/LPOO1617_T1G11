package dkeep.logic;

import dkeep.logic.MovingAgent;

import java.util.Random;

public class Ogre extends MovingAgent {

	public Ogre() {
	}

	private char generateRandomKey() {
		Random r = new Random();
		int newValue = r.nextInt(4);
		char key;
		if (newValue == 0) {
			key = 'a';
		} else if (newValue == 1) {
			key = 'w';
		} else if (newValue == 2) {
			key = 's';
		} else {
			key = 'd';
		}
		return key;
	}

	public void moveOgre(char[][] board, int currentLevel) {

		move(board, generateRandomKey(), currentLevel);

	}

	public void swingClub(char[][] board) {
		Random r = new Random();
		int coordX;
		int coordY;
		int randVal;
		do {
			coordX = this.getAgentCoords()[0];
			coordY = this.getAgentCoords()[1];
			randVal = r.nextInt(4);
			switch (randVal) {
			case 0:
				coordY--;
				break;
			case 1:
				coordX--;
				break;
			case 2:
				coordX++;
				break;
			case 3:
				coordY++;
				break;
			}

		} while (board[coordY][coordX] != ' ' && board[coordY][coordX] != 'H' && board[coordY][coordX] != 'K' );

		board[coordY][coordX] = '*';

		return;
	};

}
