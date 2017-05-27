package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

/**
 * Created by João on 25/05/2017.
 */

interface Movement {
    void move(DynamicBody ship, float deltaTime);
}
