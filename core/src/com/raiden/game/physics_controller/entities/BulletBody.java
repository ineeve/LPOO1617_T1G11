package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.EntityModel;

/**
 * Created by ineeve on 25-05-2017.
 */

public class BulletBody extends DynamicBody {
    /**
     * Constructs a body representing a model in a certain world.
     *
     * @param world The world this body lives on.
     * @param model The model representing the body.
     */
    public BulletBody(World world, EntityModel model) {
        super(world, model);
        setDensity(0.5f);
        setRestitution(0.5f);
        setDensity(0.5f);
        body.setBullet(true);
        width = 10;
        height = 10;
        setFixtureVertices();
    }
    private void setFixtureVertices(){
        float x_Correction = width / 256f;
        float y_Correction = height / 256f;
        createFixture(new float[]{
                0,0, x_Correction,0, x_Correction,y_Correction,0,y_Correction
        }, width, height);
    }
}
