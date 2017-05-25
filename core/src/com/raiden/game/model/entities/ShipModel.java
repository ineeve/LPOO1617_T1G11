package com.raiden.game.model.entities;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipModel extends MovingObjectModel{
    public float MAXVELOCITY;

    int WEIGHT_DEFAULT;

    int ARMOR_DEFAUL;

    int HP_DEFAULT;

    protected int hp;
    protected BulletModel bullet;
    protected int weight;
    protected int armor;

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    ShipModel(float x, float y) {
        super(x, y);
        bullet = new BulletModel(x,y);
    }

}
