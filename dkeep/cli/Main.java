package dkeep.cli;

import dkeep.logic.Game;
import dkeep.logic.maps.DungeonMap;

/**
 * Created by João on 23/02/2017.
 */
class Main {
    public static void main(String[] args) {
        Game game = new Game(1);
        while (!game.isGameOver()) {
            displayBoard(game.getMap());
            if(game.update() != 0){
                System.out.println("End Game!");
                return;
            };
        }
        displayBoard(game.getMap());
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
