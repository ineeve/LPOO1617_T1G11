package com.raiden.game.model.entities;

public class Airplane_2_Model extends ShipModel {

    private static float MAXVELOCITY_Default = (float) (Airplane_1_Model.MAXVELOCITY_Default * 1.3);

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public Airplane_2_Model(float x, float y) {
        super(x, y);
        MAXVELOCITY = MAXVELOCITY_Default;
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAUL;
    }

    @Override
    public ModelType getType() {
        return ModelType.AIRPLANE_2;
    }
}
