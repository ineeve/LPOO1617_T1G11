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
                    if(h != 0 && 'X' != board[y][x+h] && (x+h != 0 || x+h !=board[y].length)){
                        board[y][x] = ' ';
                        board[y][x+h] = character;
                    }
                    else if (v != 0 && 'X' != board[y+v][x] && (y+v != 0 || x+v != board.length)){
                        board[y][x] = ' ';
                        board[y+v][x] = character;
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
        while(nextMove != 'q') {
            switch (nextMove) {
                case 'a':
                    moveCharacter('H', -1, 0);
                    break;
                case 'd':
                    moveCharacter('H', 1, 0);
                    break;
                case 'w':
                    moveCharacter('H', 0, -1);
                    break;
                case 's':
                    moveCharacter('H', 0, 1);
                    break;
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
