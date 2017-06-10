package com.raiden.game.physics_controller;

import com.raiden.game.model.entities.BulletModel;

public interface Shoot {
    /**
     * Shoots a bullet from the spaceship at the bullet defined speed.
     */
    BulletModel shoot(float deltaTime);
}
