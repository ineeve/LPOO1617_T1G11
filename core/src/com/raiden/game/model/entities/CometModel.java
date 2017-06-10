package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * Created by ineeve on 24-04-2017.
 */

public class CometModel extends MovingObjectModel {
    //Id used for serialization.
    private static final long serialVersionUID = 5L;
    /**
     * Creates a new comet model in a certain position.
     * Comets have infinity damage and hp
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public CometModel(float x, float y) {
        super(x, y);
        width = 234;
        height = 400;
        damage = Integer.MAX_VALUE;
        hp = Integer.MAX_VALUE;;
    }

    @Override
    public ModelType getType() {
        return ModelType.COMET;
    }
}
