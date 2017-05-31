package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.EntityModel;

/**
 * Created by ineeve on 31-05-2017.
 */

public class CometBody extends DynamicBody {

    /**
     * Constructs a body representing a model in a certain world.
     *
     * @param world The world this body lives on.
     * @param model The model representing the body.
     */
    CometBody(World world, EntityModel model) {
        super(world, model);
        width = model.getWidth();
        height = model.getHeight();
        setRestitution(0f);
        setDensity(10f);
        setFixtureVertices();
    }
    private void setFixtureVertices(){
        createFixture(new float[]{
                0,0, width,0, width,height,0,height
        }, width, height);
    }
}
