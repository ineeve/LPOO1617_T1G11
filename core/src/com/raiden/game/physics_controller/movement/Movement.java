package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

/**
 * Defines an interface for all the bodies to move.
 * Allow for defining movement patterns
 */
interface Movement {
    /**
     * Move a dynamic body in space, given an elapsed time.
     * @param ship the dynamic body to move.
     * @param deltaTime the time elapsed.
     */
    void move(DynamicBody ship, float deltaTime);
}
