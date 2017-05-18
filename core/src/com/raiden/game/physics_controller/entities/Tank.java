package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * Created by João on 18/05/2017.
 */

public class Tank extends DynamicBody {

    private float density = 0.5f;

    private float friction = 0.4f;

    private float restitution = 0.5f;

    private int width = 488;

    private int height = 657;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public Tank (World world, MovingObjectModel model) {
        super(world, model);
        updatePhysics();
    }

    public void updatePhysics(){
        int x_Correction = width/488;
        int y_Correction = height/657;
        // Main Body
        createFixture(body, new float[]{
                35*x_Correction,72*y_Correction, 35*x_Correction,631*y_Correction, 449*x_Correction,631*y_Correction, 449*x_Correction,72*y_Correction
        }, width, height, density, friction, restitution);

        // Top Body
        createFixture(body, new float[]{
                4*x_Correction,414*y_Correction, 244*x_Correction,655*y_Correction, 488*x_Correction,657*y_Correction, 244*x_Correction,173*y_Correction
        }, width, height, density, friction, restitution);

        // Cannon
        createFixture(body, new float[]{
                218*x_Correction,5*y_Correction, 270*x_Correction,5*y_Correction, 270*x_Correction,408*y_Correction, 218*x_Correction,408*y_Correction
        }, width, height, density, friction, restitution);
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}