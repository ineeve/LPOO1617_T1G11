package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

import java.util.Hashtable;

/**
 * Responsible for managing all the movement types
 */
public abstract class MoveManager implements Movement{
    //An instance of this class
    private static MoveManager instance;

    //An enumaration of all types of movements.
    public enum MovementType {
        VERTICAL,
        HORIZONTAL,
        CIRCULAR,
        DOWNWARD;

        public static MovementType getRandom(){
            return values()[(int) (Math.random() * values().length)];
        }
    }

    //A hashtable in which the keys are the MovementType's and the values are instances of all the classes that extend the MoveManager.
    private static Hashtable<MovementType,MoveManager> allMoves = new Hashtable<MovementType, MoveManager>(){
        {
            put(MovementType.VERTICAL, new Move_Vertical());
            put(MovementType.HORIZONTAL, new Move_Horizontal());
            put(MovementType.CIRCULAR, new Move_Circular());
            put(MovementType.DOWNWARD, new Move_Downward());
        }
    };

    /**
     * Moves a body.
     * @param ship The ship no move.
     * @param deltaTime The time elapsed in seconds.
     */
    public static void moveBody(DynamicBody ship, float deltaTime) {
        if (getType(ship) == null)
            return;
        if(allMoves.containsKey(getType(ship))){
            allMoves.get(getType(ship)).move(ship, deltaTime);
            if(ship.getBody().getLinearVelocity().y > ship.getMaxVelocity()){
                ship.getBody().setLinearVelocity(ship.getBody().getLinearVelocity().x, ship.getMaxVelocity());
            }
            else if(ship.getBody().getLinearVelocity().y < -ship.getMaxVelocity()){
                ship.getBody().setLinearVelocity(ship.getBody().getLinearVelocity().x, -ship.getMaxVelocity());
            }
        }
    }

    /**
     * The type of movement associated with this object (to be used with polymorphism).
     * @param ship
     * @return the movement type associated with this object.
     */
    private static MovementType getType(DynamicBody ship){
        return ship.getMovementType();
    }

}
