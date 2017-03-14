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
        config.GUARDPERSONALITY = UserInput.getInt();
        /*Number of Ogres*/
        System.out.println("How many Ogres do you wish to fight?");
        config.NUMBEROFOGRES = UserInput.getInt();

        config.prepareNextLevel();
        game.setMap(config.getMap());
        game.setAgents(config.getAgents());
        game.setKey(config.getKey());
        game.setKeyTaken(false);
        game.gameStatus = Game.status.PLAYING;
        while (game.isGameOver() == false) {
            displayBoard(game.getMap());
            switch (game.moveAllAgents()){
                case 0:
                    break;
                case 1:
                    config.prepareNextLevel();
                    game.setMap(config.getMap());
                    game.setAgents(config.getAgents());
                    game.setKey(config.getKey());
                    game.setKeyTaken(false);
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

        for (int i = 0; i < matrix.length; i++) {
            currentLine = "";
            for (int j = 0; j < matrix[i].length; j++) {
                currentLine += " " + matrix[i][j];
            }
            System.out.println(currentLine);
        }

    }
}
