package dkeep.cli;

import java.util.Scanner;

/**
 * Created by João on 23/02/2017.
 */
class UserInput {

    public static char getChar() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Next Move: ");
        return reader.next().charAt(0);
    }
    public static int getInt(){
        Scanner reader = new Scanner(System.in);
        return reader.nextInt();
    }
}
