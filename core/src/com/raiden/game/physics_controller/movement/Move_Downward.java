package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

/**
 * Created by João on 31/05/2017.
 */

public class Move_Downward extends MoveManager {
    @Override
    public void move(DynamicBody ship, float deltaTime) {
        ship.setVelocity(ship.getBody().getLinearVelocity().x, ship.getMaxVelocity());
    }
}
