package com.raiden.game.physics_controller.entities;


import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * A concrete representation of an DynamicBody
 * representing the player space ship.
 */
public class AirPlane_2 extends DynamicBody {

    private float density = 0.5f;

    private float friction = 0.4f;

    private float restitution = 0.5f;

    private int width = 346;

    private int height = 532;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public AirPlane_2(World world, MovingObjectModel model) {
        super(world, model);
        updatePhysics();
    }

    public void updatePhysics(){
        int x_Correction = width/346;
        int y_Correction = height/532;
        // Left wing
        createFixture(body, new float[]{
                173*x_Correction,y_Correction, x_Correction,468*y_Correction, x_Correction,531*y_Correction, 173*x_Correction,531*y_Correction
        }, width, height, density, friction, restitution);

        // Right wing
        createFixture(body, new float[]{
                173*x_Correction,y_Correction, 345*x_Correction,468*y_Correction, 345*x_Correction,531*y_Correction, 173*x_Correction,531*y_Correction
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