package com.raiden.game.physics_controller;

import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.entities.BulletBody;
import com.raiden.game.physics_controller.entities.DynamicBody;

/**
 * Created by João on 01/06/2017.
 */

public interface Shoot {
    /**
     * Shoots a bullet from the spaceship at 10m/s
     */
    BulletModel shoot(float deltaTime);
}
