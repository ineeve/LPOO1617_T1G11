package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * Created by João on 18/05/2017.
 */

public class Tank extends ShipPhysics {

    private final float density = 2f;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public Tank (World world, MovingObjectModel model) {
        super(world, model);
        width = model.getWidth();
        height =model.getHeight();
        super.setDensity(density);
        updatePhysics();
    }

    @Override
    public void updatePhysics(){
        float x_Correction = width / 488f;
        float y_Correction = height / 657f;
        // Main Body
        createFixture(new float[]{
                35*x_Correction,72*y_Correction, 35*x_Correction,631*y_Correction, 449*x_Correction,631*y_Correction, 449*x_Correction,72*y_Correction
        }, width, height);

        // Top Body
        createFixture(new float[]{
                4*x_Correction,414*y_Correction, 244*x_Correction,655*y_Correction, 488*x_Correction,657*y_Correction, 244*x_Correction,173*y_Correction
        }, width, height);

        // Cannon
        createFixture(new float[]{
                218*x_Correction,5*y_Correction, 270*x_Correction,5*y_Correction, 270*x_Correction,408*y_Correction, 218*x_Correction,408*y_Correction
        }, width, height);
    }
}