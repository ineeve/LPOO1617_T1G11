package com.raiden.game.physics_controller.entities;


import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * A concrete representation of an DynamicBody
 * representing the player space ship.
 */
public class AirPlane_2 extends ShipBody {

    //The max speed default value for this ship. It is related with airplane_1 max speed.
    private static float MAXSPEED_Default = AirPlane_1.MAXSPEED_Default * 1.3f;

    //The density of this body.
    private final float density = 1.5f;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public AirPlane_2(World world, MovingObjectModel model) {
        super(world, model);
        super.setDensity(density);
        maxVelocity = MAXSPEED_Default;
        width = model.getWidth();
        height = model.getHeight();
        body.setFixedRotation(true);
        setFixtureVertices();
    }

    /**
     * Defines all the fixtures of this body.
     */
    @Override
    public void setFixtureVertices(){
        float x_Correction = width / 346f;
        float y_Correction = height / 532f;
        // Left wing
        createFixture(new float[]{
                173*x_Correction,y_Correction, x_Correction,468*y_Correction, x_Correction,531*y_Correction, 173*x_Correction,531*y_Correction
        }, width, height);

        // Right wing
        createFixture(new float[]{
                173*x_Correction,y_Correction, 345*x_Correction,468*y_Correction, 345*x_Correction,531*y_Correction, 173*x_Correction,531*y_Correction
        }, width, height);
    }
}