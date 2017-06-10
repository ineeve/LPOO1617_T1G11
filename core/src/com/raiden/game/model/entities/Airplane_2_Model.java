package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * A airplane with good amount of hp
 */
public class Airplane_2_Model extends ShipModel {

    //Id used for serialization.
    private static final long serialVersionUID = 2L;

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public Airplane_2_Model(float x, float y) {
        super(x, y);
        hp = HP_DEFAULT*2;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAULT;
        width = 80;
        height = 123;
    }

    @Override
    public ModelType getType() {
        return ModelType.AIRPLANE_2;
    }

}
