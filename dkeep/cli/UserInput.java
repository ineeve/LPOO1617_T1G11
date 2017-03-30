package dkeep.cli;

import java.util.Scanner;

/**
 * Class responsible of get user input from command line
 */
class UserInput {

    /** FUnction to get one char from user by command line
     *
     * @return char - read from command line
     */

    public static char getChar() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Next Move: ");
        return reader.next().charAt(0);
    }

    /** Function to get one int from user by command line
     *
     * @return int - read from command line
     */

    public static int getInt(){
        Scanner reader = new Scanner(System.in);
        return reader.nextInt();
    }
}
