package com.raiden.game.physics_controller.entities;


import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * A concrete representation of an DynamicBody
 * representing the player space ship.
 */
public class AirPlane_3 extends ShipPhysics {
    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public AirPlane_3(World world, MovingObjectModel model) {
        super(world, model);
        width = 75;
        height = 122;
        body.setFixedRotation(true);
        updatePhysics();
    }

    @Override
    public void updatePhysics(){
        float x_Correction = width / 423f;
        float y_Correction = height / 689f;
        // Left wing
        createFixture(new float[]{
                211*x_Correction,y_Correction, x_Correction,266*y_Correction, 211*x_Correction,266*y_Correction
        }, width, height);

        // Right wing
        createFixture(new float[]{
                211*x_Correction,y_Correction, 422*x_Correction,266*y_Correction, 211*x_Correction,266*y_Correction
        }, width, height);

        // Body
        createFixture(new float[]{
                x_Correction,266*y_Correction, x_Correction,688*y_Correction, 422*x_Correction,688*y_Correction, 422*x_Correction,266*y_Correction
        }, width, height);
    }
}