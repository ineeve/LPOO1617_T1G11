package dkeep.cli;

import java.util.Scanner;

/**
 * Created by João on 23/02/2017.
 */
public class UserInput {
    public UserInput() {
    }

    public static char getChar() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Next Move: ");
        char input = reader.next().charAt(0);
        return input;
    }
}
