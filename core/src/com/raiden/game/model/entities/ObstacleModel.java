package com.raiden.game.model.entities;

/**
 * Created by ineeve on 24-04-2017.
 */

public class ObstacleModel extends EntityModel{

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x        The x-coordinate of this entity in meters.
     * @param y        The y-coordinate of this entity in meters.
     * @param rotation The current rotation of this entity in radians.
     */
    ObstacleModel(float x, float y, float rotation) {
        super(x, y, rotation);
    }
}