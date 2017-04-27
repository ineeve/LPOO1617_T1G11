package com.raiden.game.model.entities;

/**
 * Created by ineeve on 24-04-2017.
 */

public class AirplaneModel extends MovingObjectModel {

    private int hp;
    private BulletModel bullet;
    private int weight;
    private int armor;

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public AirplaneModel(float x, float y, int rotation) {
        super(x, y, rotation);
    }
}
