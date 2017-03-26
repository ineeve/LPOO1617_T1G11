package dkeep.cli;

import dkeep.logic.Configs;
import dkeep.logic.Game;

import static dkeep.cli.UserInput.getChar;
import static dkeep.cli.UserInput.getInt;

/**
 * Main class of command line interface;
 */
class Main {

    /** Main Function contains de cycle of running Game
     *
     * @param args String[] - with a arguments from command line
     */

    public static void main(String[] args) {
        Configs config = new Configs(1);
        getStartInputs(config);
        Game game = new Game(config);
        resetLevel(game);
        while (game.gameStatus != Game.status.DEFEAT) {
            displayBoard(getMap(game));
            switch (game.moveAllAgents(getChar())){
                case 0:
                    break;
                case 1:
                    resetLevel(game);
                    break;
                case 2:
                    System.out.println("Congratulations,you've escaped");
                    return;
            }
        }
        displayBoard(getMap(game));
        System.out.println("You have been captured");
    }

    /** Function to reset level of specific Game
     *
     * @param game Object of class Game to reset level
     */

    private static void resetLevel(Game game){
        game.resetLevel();
    }

    /** Function to get Board of specific Game
     *
     * @param game Object of class Game to get the Board
     * @return Board of game in one matrix of char
     */

    private static char[][] getMap(Game game){
        return game.getMap();
    }

    /** Display board on command line
     *
     * @param matrix Board of game in one matrix of char
     */

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

    /** Get the start inputs to play like: NUMBEROGOGRES & GUARDPERSONALITY
     *
     * @param config Configuration object to set number of ogres to play and guard personality
     */

    private static void getStartInputs(Configs config){
        /*Guard personality*/
        System.out.println("What guard personality you want?");
        System.out.println("0) Rookie");
        System.out.println("1) Drunken");
        System.out.println("2) Suspicious");
        config.GUARDPERSONALITY = getInt();
        /*Number of Ogres*/
        System.out.println("How many Ogres do you wish to fight?");
        config.NUMBEROFOGRES = getInt();
    }
}
