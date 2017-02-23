package dkeep.cli;

/**
 * Created by João on 23/02/2017.
 */
public class Main {
    public static void main(String[] args) {
        if (runLevel(1)){
            System.out.println("Oh, the 'S' actually meant stairs");
            System.out.println("You are on the first floor");
            System.out.println("Prepare to join Keep's Crazy dkeep.logic.Ogre");
            if (runLevel(2)){
                System.out.println("You've escaped, congrats!");
            }
            else {
                System.out.println("Game Over");
            }
        }
        else {
            System.out.println("Game Over");
        }
    }
}
