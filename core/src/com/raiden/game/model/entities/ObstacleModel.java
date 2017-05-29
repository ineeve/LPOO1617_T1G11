package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * Created by ineeve on 24-04-2017.
 */

public class ObstacleModel extends EntityModel{

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x        The x-coordinate of this entity in meters.
     * @param y        The y-coordinate of this entity in meters.
     */
    ObstacleModel(float x, float y) {
        super(x, y);
        Gdx.app.log("ObstacleModel()", "Creating new.");
        width = 50;
        height = 50;
    }

    @Override
    public ModelType getType() {
        return ModelType.OBSTACLE;
    }
}
