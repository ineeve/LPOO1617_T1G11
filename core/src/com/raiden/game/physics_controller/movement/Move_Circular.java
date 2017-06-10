package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

/**
 * Defines a circular move
 */
class Move_Circular extends MoveManager {
    /**
     * Moves a ship in a circular pattern
     * @param ship the ship to move
     * @param deltaTime the elapsed time in seconds.
     */
    @Override
    public void move(DynamicBody ship, float deltaTime) {
        float t = ship.getT();
        t += deltaTime;
        ship.setT(t);
        ship.setVelocity((float) -Math.cos(t * Math.PI) * 4f, (float) -Math.sin(t * Math.PI) * 4f);
    }
}
