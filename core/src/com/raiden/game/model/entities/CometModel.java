package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * Created by ineeve on 24-04-2017.
 */

public class CometModel extends MovingObjectModel {
    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public CometModel(float x, float y) {
        super(x, y);
        Gdx.app.log("CometModel()", "Creating new.");
        width = 50;
        height = 50;
    }

    @Override
    public ModelType getType() {
        return ModelType.COMET;
    }
}
