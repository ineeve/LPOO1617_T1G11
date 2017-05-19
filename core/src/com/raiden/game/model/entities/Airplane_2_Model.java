package com.raiden.game.model.entities;

public class Airplane_2_Model extends ShipModel {/**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    public Airplane_2_Model(float x, float y, float rotation) {
        super(x, y, rotation);
        MAXVELOCITY = (float) (Airplane_1_Model.MAXVELOCITY * 1.3);
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAUL;
    }
}
