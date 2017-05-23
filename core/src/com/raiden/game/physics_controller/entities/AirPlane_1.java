package com.raiden.game.physics_controller.entities;


import com.raiden.game.model.entities.MovingObjectModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A concrete representation of an DynamicBody
 * representing the player space ship.
 */
public class AirPlane_1 extends ShipPhysics {

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public AirPlane_1(World world, MovingObjectModel model) {
        super(world, model);
        width = 508;
        height = 531;
        updatePhysics();
    }

    public void updatePhysics(){
        int x_Correction = width/508;
        int y_Correction = height/531;
        // Left wing
        createFixture(body, new float[]{
                253*x_Correction,y_Correction, x_Correction,502*y_Correction, 203*x_Correction,466*y_Correction
        }, width, height);

        // Right wing
        createFixture(body, new float[]{
                253*x_Correction,y_Correction, 303*x_Correction,466*y_Correction, 505*x_Correction,502*y_Correction
        }, width, height);

        // Body
        createFixture(body, new float[]{
                253*x_Correction,y_Correction, 203*x_Correction,466*y_Correction, 253*x_Correction,530*y_Correction, 303*x_Correction,466*y_Correction
        }, width, height);
    }
}