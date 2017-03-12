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
        char r = reader.next().charAt(0);
        return r;
    }
    public static int getInt(){
        Scanner reader = new Scanner(System.in);
        int input = reader.nextInt();
        return input; 
    }
}
