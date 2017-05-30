package com.raiden.game.model.entities;


import com.badlogic.gdx.Gdx;

public class Airplane_3_Model extends ShipModel {


    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public Airplane_3_Model(float x, float y) {
        super(x, y);
        Gdx.app.log("Airplane_3_Model()", "Creating new.");
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAULT*2;
        width = 75;
        height = 122;
    }

    @Override
    public ModelType getType() {
        return ModelType.AIRPLANE_3;
    }
}
