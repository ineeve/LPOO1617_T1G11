package com.raiden.game.physics_controller.entities;


import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * A concrete representation of an DynamicBody
 * representing the player space ship.
 */
public class AirPlane_1 extends ShipBody {
    public static float MAXVELOCITY_Default = 5f;

    private final float density = 0.5f;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public AirPlane_1(World world, MovingObjectModel model) {
        super(world, model);
        super.setDensity(density);
        maxVelocity = MAXVELOCITY_Default;
        width = model.getWidth();
        height = model.getHeight();
        body.setFixedRotation(true);
        updatePhysics();
    }

    public void updatePhysics(){
        float x_Correction = width / 508f;
        float y_Correction = height / 531f;
        // Left wing
        createFixture(new float[]{
                253*x_Correction,y_Correction, x_Correction,502*y_Correction, 203*x_Correction,466*y_Correction
        }, width, height);

        // Right wing
        createFixture(new float[]{
                253*x_Correction,y_Correction, 303*x_Correction,466*y_Correction, 505*x_Correction,502*y_Correction
        }, width, height);

        // Body
        createFixture(new float[]{
                253*x_Correction,y_Correction, 203*x_Correction,466*y_Correction, 253*x_Correction,530*y_Correction, 303*x_Correction,466*y_Correction
        }, width, height);
    }
}