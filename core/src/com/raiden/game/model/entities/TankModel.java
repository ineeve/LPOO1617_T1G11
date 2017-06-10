package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * Created by João on 18/05/2017.
 */

public class TankModel extends ShipModel {
    //Id used for serialization.
    private static final long serialVersionUID = 10L;

    /**
     * Creates a new tank model in a certain position.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public TankModel(float x, float y) {
        super(x, y);
        hp = HP_DEFAULT*2;
        weight = WEIGHT_DEFAULT*3;
        armor = ARMOR_DEFAULT*3;
        width = 75;
        height = 101;
    }

    /**
     * @return ModelType.TANK
     */
    @Override
    public ModelType getType() {
        return ModelType.TANK;
    }
}
