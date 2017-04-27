package com.raiden.game.model.entities;

/**
 * Created by ineeve on 24-04-2017.
 */

public class BulletModel extends MovingObjectModel {

    private int damage;
    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public BulletModel(float x, float y, int rotation) {
        super(x, y, rotation);
    }
}
