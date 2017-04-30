package com.raiden.game.model.entities;


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
    public AirplaneModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }
}
