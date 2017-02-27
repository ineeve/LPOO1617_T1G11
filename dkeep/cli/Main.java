package dkeep.cli;

import dkeep.logic.Game;

/**
 * Created by João and Renato on 23/02/2017.
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        while (!game.isGameOver()){
            displayBoard(game.getMap());
            game.update();
        }

    }

    public static void displayBoard(char matrix[][]) {
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
