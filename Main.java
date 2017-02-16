import java.util.Scanner;

/**
 * Created by João on 15/02/2017.
 */


public class Main {
    private static char board[][]= {{'X','X','X','X','X','X','X','X','X','X',},
            {'X','H',' ',' ','I',' ','X',' ','G','X',},
            {'X','X','X',' ','X','X','X',' ',' ','X',},
            {'X',' ','I',' ','I',' ','X',' ',' ','X',},
            {'I',' ',' ',' ',' ',' ',' ',' ',' ','X',},
            {'I',' ',' ',' ',' ',' ',' ',' ',' ','X',},
            {'X','X','X',' ','X','X','X','X',' ','X',},
            {'X',' ','I',' ','I',' ','X','K',' ','X',},
            {'X','X','X','X','X','X','X','X','X','X',}};

    public static void moveCharacter(char character, int h, int v){
        for (int y = 0; y < board.length; y++){
            for(int x = 0; x < board[y].length; x++){
                if(character == board[y][x]){
                    if('X' != board[y+v][x+h] && 'I' != board[y+v][x+h] && ((x+h != 0 || x+h !=board[y].length) || (x+v != 0 || y+v != board.length))){
                        board[y][x] = ' ';
                        if(board[y+v][x+h] == 'k'){
                            changeAllDoorsToStairs();
                        }
                        board[y+v][x+h] = character;
                    }
                    return;
                }
            }
        }
    }

    public static void displayBoard(){
        for(int y = 0; y < board.length; y++){
            System.out.println(board[y]);
        }
    }

    public static void game(){
        Scanner s = new Scanner(System.in);
        displayBoard();
        System.out.println("Insert next move: ");
        char nextMove = s.nextLine().charAt(0);
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
        game();
    }


}
