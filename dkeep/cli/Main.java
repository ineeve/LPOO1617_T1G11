package dkeep.cli;

import dkeep.logic.Configs;
import dkeep.logic.Game;

/**
 * Created by João on 23/02/2017.
 */
class Main {
    public static void main(String[] args) {
        Configs config = new Configs(1);
        Game game = new Game();

        /*Guard personality*/
        System.out.println("What guard personality you want?");
        System.out.println("0) Rookie");
        System.out.println("1) Drunken");
        System.out.println("2) Suspicious");
        Configs.GUARDPERSONALITY = UserInput.getInt();
        /*Number of Ogres*/
        System.out.println("How many Ogres do you wish to fight?");
        Configs.NUMBEROFOGRES = UserInput.getInt();
        game.setConfigs(config);
        game.resetLevel();
        while (!game.isGameOver()) {
            displayBoard(game.getMap());
            switch (game.moveAllAgents()){
                case 0:
                    break;
                case 1:
                    game.resetLevel();
                    break;
                case 2:
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

        for (char[] aMatrix : matrix) {
            currentLine = "";
            for (char anAMatrix : aMatrix) {
                currentLine += " " + anAMatrix;
            }
            System.out.println(currentLine);
        }

    }
}
