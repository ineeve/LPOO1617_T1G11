package com.raiden.game.model.entities;


public class Airplane_3_Model extends ShipModel {/**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public Airplane_3_Model(float x, float y) {
        super(x, y);
        Airplane_3_Model.MAXVELOCITY = (float) (Airplane_1_Model.MAXVELOCITY * 0.8);
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAUL;
    }

    @Override
    public ModelType getType() {
        return ModelType.AIRPLANE_3;
    }
}
