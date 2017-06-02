package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

import java.util.Hashtable;

/**
 * Created by João on 26/05/2017.
 */

public abstract class MoveManager implements Movement{
    private static MoveManager instance;

    public enum MovementType {
        VERTICAL,
        HORIZONTAL,
        CIRCULAR,
        DOWNWARD;

        public static MovementType getRandom(){
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private static Hashtable<MovementType,MoveManager> allMoves = new Hashtable<MovementType, MoveManager>(){
        {
            put(MovementType.VERTICAL, new Move_Vertical());
            put(MovementType.HORIZONTAL, new Move_Horizontal());
            put(MovementType.CIRCULAR, new Move_Circular());
            put(MovementType.DOWNWARD, new Move_Downward());
        }
    };

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

    private static MovementType getType(DynamicBody ship){
        return ship.getMovementType();
    }

}