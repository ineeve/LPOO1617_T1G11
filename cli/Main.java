package dkeep.cli;

import dkeep.logic.Configs;
import dkeep.logic.Game;

import static dkeep.cli.UserInput.getChar;
import static dkeep.cli.UserInput.getInt;

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
        Configs.GUARDPERSONALITY = getInt();
        /*Number of Ogres*/
        System.out.println("How many Ogres do you wish to fight?");
        Configs.NUMBEROFOGRES = getInt();
        game.setConfigs(config);
        game.resetLevel();
        while (Game.gameStatus != Game.status.DEFEAT) {
            displayBoard(game.getMap());
            switch (game.moveAllAgents(getChar())){
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
