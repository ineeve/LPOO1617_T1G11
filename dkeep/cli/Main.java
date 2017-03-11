package dkeep.cli;

import dkeep.logic.Game;

/**
 * Created by João on 23/02/2017.
 */
class Main {
    public static void main(String[] args) {
        Game game = new Game(1);
        while (game.isGameOver() == false) {
            displayBoard(game.getMap());
            if (game.moveAllAgents()==1){
                 System.out.println("Congratulations,you've escaped");
                 return;
            }
        }
        displayBoard(game.getMap());
        System.out.println("You have been captured");
    }

    private static void displayBoard(char matrix[][]) {
        System.out.println("\n\n");
        String currentLine;

        for (int i = 0; i < matrix.length; i++) {
            currentLine = "";
            for (int j = 0; j < matrix[i].length; j++) {
                currentLine += " " + matrix[i][j];
            }
            System.out.println(currentLine);
        }

    }
}
