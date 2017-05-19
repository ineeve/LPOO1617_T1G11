package com.raiden.game.model.entities;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipModel extends MovingObjectModel{
    public static float MAXVELOCITY;

    public int WEIGHT_DEFAULT;

    public int ARMOR_DEFAUL;

    public int HP_DEFAULT;

    protected int hp;
    protected BulletModel bullet;
    protected int weight;
    protected int armor;

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    ShipModel(float x, float y, float rotation) {
        super(x, y, rotation);
        MAXVELOCITY = 0;
        WEIGHT_DEFAULT = 0;
        ARMOR_DEFAUL = 0;
        HP_DEFAULT = 0;
    }
}
