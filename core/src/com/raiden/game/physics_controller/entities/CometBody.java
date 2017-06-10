package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.EntityModel;

/**
 * Created by ineeve on 31-05-2017.
 */

public class CometBody extends DynamicBody {

    //The max speed default value for the comet. It is related with airplane_1 max speed.
    private static float MAXVELOCITY_Default = AirPlane_1.MAXSPEED_Default * 5f;

    /**
     * Constructs a body representing a model in a certain world.
     *
     * @param world The world this body lives on.
     * @param model The model representing the body.
     */
    public CometBody(World world, EntityModel model) {
        super(world, model);
        width = model.getWidth();
        height = model.getHeight();
        body.setFixedRotation(true);
        maxVelocity = MAXVELOCITY_Default;
        setRestitution(0f);
        setDensity(30f);
        setFixtureVertices();
    }


    /**
     * Defines all the fixtures of this body.
     */
    private void setFixtureVertices(){
        float tolerance = 0.2f;
        createFixture(new float[]{
                width/2f,0, width*(1-tolerance),height*0.1f, width/2f,height, width*tolerance,height*0.11f
        }, width, height);
    }
}
