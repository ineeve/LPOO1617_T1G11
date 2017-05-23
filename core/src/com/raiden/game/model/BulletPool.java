package com.raiden.game.model;

import com.badlogic.gdx.math.Vector2;
import com.raiden.game.model.entities.BulletModel;

import java.awt.Point;

/**
 * Created by ineeve on 23-05-2017.
 */

public class BulletPool {

    private static final int POOL_SIZE = 100;
    private BulletModel[] bullets = new BulletModel[POOL_SIZE];

    public void createBullet(Point position){}

}
