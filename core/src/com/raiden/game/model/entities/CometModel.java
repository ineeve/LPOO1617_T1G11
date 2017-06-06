package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * Created by ineeve on 24-04-2017.
 */

public class CometModel extends MovingObjectModel {
    private static final long serialVersionUID = 5L;
    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public CometModel(float x, float y) {
        super(x, y);
        width = 234;
        height = 400;
        Gdx.app.log("CometModel()", "Creating new.");
        damage = Integer.MAX_VALUE;
        hp = Integer.MAX_VALUE;;
    }

    @Override
    public ModelType getType() {
        return ModelType.COMET;
    }
}
