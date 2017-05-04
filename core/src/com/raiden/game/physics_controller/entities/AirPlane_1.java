package com.raiden.game.physics_controller.entities;


import com.raiden.game.model.entities.MovingObjectModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A concrete representation of an DynamicBody
 * representing the player space ship.
 */
public class AirPlane_1 extends DynamicBody {

    private float density = 0.5f;

    private float friction = 0.4f;

    private float restitution = 0.5f;

    private int width = 508;

    private int height = 531;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public AirPlane_1(World world, MovingObjectModel model) {
        super(world, model);
        updatePhysics();
    }

    public void updatePhysics(){
        int x_Correction = width/508;
        int y_Correction = height/531;
        // Left wing
        createFixture(body, new float[]{
                253*x_Correction,y_Correction, x_Correction,502*y_Correction, 203*x_Correction,466*y_Correction
        }, width, height, density, friction, restitution);

        // Right wing
        createFixture(body, new float[]{
                253*x_Correction,y_Correction, 303*x_Correction,466*y_Correction, 505*x_Correction,502*y_Correction
        }, width, height, density, friction, restitution);

        // Body
        createFixture(body, new float[]{
                253*x_Correction,y_Correction, 203*x_Correction,466*y_Correction, 253*x_Correction,530*y_Correction, 303*x_Correction,466*y_Correction
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