package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;


/**
 * Defines a vertical move
 */
class Move_Vertical extends MoveManager {
    /**
     * Moves a ship in a vertically.
     * @param ship the ship to move
     * @param deltaTime the elapsed time in seconds.
     */
    @Override
    public void move(DynamicBody ship, float deltaTime) {
        float t = ship.getT();
        t += deltaTime;
        ship.setT(t);
        ship.setVelocity(ship.getBody().getLinearVelocity().x, (float) -Math.sin(t * Math.PI) * 4f);
    }
}
