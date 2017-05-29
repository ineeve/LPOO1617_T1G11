package com.raiden.game.model.entities;

import com.badlogic.gdx.Gdx;

/**
 * Created by ineeve on 24-04-2017.
 */

public class BulletModel extends MovingObjectModel {

    private int damage;
    
    /**
     * Creates a new ship model in a certain position;
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public BulletModel(float x, float y) {
        super(x, y);
        Gdx.app.log("BulletModel()", "Creating new.");
        width = 20;
        height = 20;
        damage = 25;
    }


    @Override
    public ModelType getType() {
        return ModelType.BULLET;
    }

    public int getDamage(){
        return damage;
    }
}
