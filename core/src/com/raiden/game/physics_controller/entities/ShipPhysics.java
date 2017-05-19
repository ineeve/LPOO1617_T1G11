package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipPhysics extends DynamicBody {

    protected float density = 0.5f;

    protected float friction = 0.4f;

    protected float restitution = 0.5f;

    protected int width;

    protected int height;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public ShipPhysics(World world, MovingObjectModel model) {
        super(world, model);
        updatePhysics();
    }

    public abstract void updatePhysics();

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
