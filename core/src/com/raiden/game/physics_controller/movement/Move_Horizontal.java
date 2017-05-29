package com.raiden.game.physics_controller.movement;

import com.raiden.game.physics_controller.entities.DynamicBody;

/**
 * Created by João on 26/05/2017.
 */

class Move_Horizontal extends MoveBody {
    @Override
    public void move(DynamicBody ship, float deltaTime) {
        float t = ship.getT();
        t += deltaTime;
        ship.setT(t);
        ship.setVelocity((float) Math.sin(t * Math.PI) * 4f, ship.getBody().getLinearVelocity().y);
    }
}