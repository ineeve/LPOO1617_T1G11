package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * Created by ineeve on 24-04-2017.
 */

public class ObstacleModel extends EntityModel{
    //Id used for serialization.
    private static final long serialVersionUID = 8L;

    /**
     * Constructs a obstacle model with a position.
     *
     * @param x        The x-coordinate of this entity in meters.
     * @param y        The y-coordinate of this entity in meters.
     */
    ObstacleModel(float x, float y) {
        super(x, y);
        width = 50;
        height = 50;
    }

    /**
     *
     * @return The type of this object which is a OBSTACLE.
     */
    @Override
    public ModelType getType() {
        return ModelType.OBSTACLE;
    }
}
