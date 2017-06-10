package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;


/**
 * Defines a downward move
 */
public class Move_Downward extends MoveManager {
    /**
     * Moves a ship in a downward pattern
     * @param ship the ship to move
     * @param deltaTime the elapsed time in seconds.
     */
    @Override
    public void move(DynamicBody ship, float deltaTime) {
        ship.setVelocity(ship.getBody().getLinearVelocity().x, ship.getMaxVelocity());
    }
}
