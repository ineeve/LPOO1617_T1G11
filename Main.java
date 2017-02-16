import java.awt.Point;
import java.util.Scanner;

/**
 * Created by João on 15/02/2017.
 * With countribution of Renato
 */


public class Main {
	public static char board[][];
	
	public static Point hero = new Point(-1,-1);
	public static Point guard = new Point(-1,-1);
	public static Point ogre = new Point(-1,-1);
	
	
	
	public static void setAgentsInitialLocations(){
		for (int i = 0; i < board.length;i++){
			for (int j = 0; j < board[i].length;j++){
				if (board[i][j] == 'H'){
					hero.setLocation(j, i);
				}
				else if (board[i][j] == 'G'){
					guard.setLocation(j, i);
				}
			}
		}
	}
	
	public static boolean guardCaughtHero(){
		if (hero.distance(guard) <= 1.1){
			return true;
		}
		return false;
	}
	
	
	public static void createBoard(int levelNumber) {
		if (levelNumber == 1){
			board = new char[][] { 
			    { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' },
				{ 'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
		}
		else if (levelNumber == 2){
			board = new char[][] { 
			    { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'I', ' ', ' ', ' ', 'O', ' ', ' ', 'k', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
		}
		else{
			board = new char [][]{{}};
		}
	}
	
	

    public static boolean moveCharacter(char character, int h, int v) {
        int x = 0, y = 0;
        if (character == 'H') {
            x = hero.x;
            y = hero.y;
        } else if (character == 'G') {
            x = guard.x;
            y = guard.y;
        }
        if ('X' != board[y + v][x + h] && 'I' != board[y + v][x + h] && ((x + h != 0 || x + h != board[y].length) || (x + v != 0 || y + v != board.length))) {
            board[y][x] = ' ';
            if (board[y + v][x + h] == 'k') {
                changeAllDoorsToStairs();
            }
            if (board[y + v][x + h] == 'S') {
                board[y + v][x + h] = character;
                return true;
            }
            board[y + v][x + h] = character;
            if (character == 'H') {
                hero.x = x + h;
                hero.y = y + v;
            } else if (character == 'G') {
                guard.x = x + h;
                guard.y = y + v;
            }
        }
        return false;
    }

    public static void changeAllDoorsToStairs() {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == 'I') {
					board[y][x] = 'S';
				}
			}
		}
	}
    public static void displayBoard() {
		System.out.println("\n\n");
		String currentLine;

		for (int i = 0; i < board.length; i++) {
			currentLine = "";
			for (int j = 0; j < board[i].length; j++) {
				currentLine += " " + board[i][j];
			}
			System.out.println(currentLine);
		}
		
	}

    public static void game(){
        Scanner s = new Scanner(System.in);
        displayBoard();
        System.out.println("Insert next move: ");
        char nextMove = s.next().charAt(0);
        char guardMovement[] = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
        char character = 'H';
        int moveIndex = 0;
        while(nextMove != 'q') {
            character = 'H';
            for(int i = 0; i <= 1; i++) {
                switch (nextMove) {
                    case 'a':
                        moveCharacter(character, -1, 0);
                        break;
                    case 'd':
                        moveCharacter(character, 1, 0);
                        break;
                    case 'w':
                        moveCharacter(character, 0, -1);
                        break;
                    case 's':
                        moveCharacter(character, 0, 1);
                        break;
                }
                character = 'G';
                nextMove = guardMovement[moveIndex];
            }
            if(moveIndex == guardMovement.length - 1){
                moveIndex = 0;
            }
            else{
                moveIndex++;
            }
            displayBoard();
            System.out.println("Insert next move: ");
            nextMove = s.next().charAt(0);
        }
    }

    public static void main(String[] args){
    	createBoard(1);
    	setAgentsInitialLocations();
        game();
    }


}
