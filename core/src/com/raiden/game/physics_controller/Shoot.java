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
    void shoot(ShipModel ship) {
        if (timeToNextShoot < 0) {
            BulletModel bullet = model.createBullet(ship);
            bullet.setFlaggedForRemoval(false);
            DynamicBody body = new BulletBody(world, bullet);
            body.setVelocity(0,body.getMaxVelocity());
            dynamicBodies.add(body);
            timeToNextShoot = TIME_BETWEEN_SHOTS;
        }
    }
}
