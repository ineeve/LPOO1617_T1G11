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
        setRestitution(0.5f);
        setDensity(3f);
        body.setBullet(true);
        width = model.getWidth();
        height = model.getHeight();
        setFixtureVertices();
    }
    private void setFixtureVertices(){
        createFixture(new float[]{
                0,0, width,0, width,height,0,height
        }, width, height);
    }
}
