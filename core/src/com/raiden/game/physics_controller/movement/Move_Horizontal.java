package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

/**
 * Defines a horizontal move
 */
class Move_Horizontal extends MoveManager {
    /**
     * Moves a ship horizontally
     * @param ship the ship to move
     * @param deltaTime the elapsed time in seconds.
     */
    @Override
    public void move(DynamicBody ship, float deltaTime) {
        float t = ship.getT();
        t += deltaTime;
        ship.setT(t);
        ship.setVelocity((float) Math.sin(t * Math.PI) * 4f, ship.getBody().getLinearVelocity().y);
    }
}
