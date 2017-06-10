package com.raiden.game.model.entities;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

/**
 * A well balanced airplane
 */
public class Airplane_1_Model extends ShipModel {

    //Id used for serialization
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public Airplane_1_Model(float x, float y) {
        super(x, y);
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAULT;
        width = 98;
        height = 102;
    }

    @Override
    public ModelType getType() {
        return ModelType.AIRPLANE_1;
    }

}
