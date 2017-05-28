package com.raiden.game.model.entities;


public class Airplane_3_Model extends ShipModel {

    private static float MAXVELOCITY_Default = (float) (Airplane_1_Model.MAXVELOCITY_Default * 0.8);

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public Airplane_3_Model(float x, float y) {
        super(x, y);
        MAXVELOCITY = MAXVELOCITY_Default;
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAUL;
        width = 75;
        height = 122;
    }

    @Override
    public ModelType getType() {
        return ModelType.AIRPLANE_3;
    }
}
