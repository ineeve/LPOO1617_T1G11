package com.raiden.game.model.entities;

/**
 * Created by João on 18/05/2017.
 */

public class TankModel extends MovingObjectModel {

    public static final int MAXVELOCITY = 10;

    public static int WEIGHT_DEFAULT = 0;

    public static int ARMOR_DEFAUL = 0;

    public static int HP_DEFAULT = 0;

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
    TankModel(float x, float y, float rotation) {
        super(x, y, rotation);
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAUL;
    }
}
