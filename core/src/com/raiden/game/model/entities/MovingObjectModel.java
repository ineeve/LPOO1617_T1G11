package com.raiden.game.model.entities;

import com.raiden.game.physics_controller.movement.MoveBody;

/**
 * A model representing a the user space ship.
 */
public abstract class MovingObjectModel extends EntityModel {
    /**
     * Is this ship accelerating in this update delta
     */
    private boolean accelerating = true;

    protected MoveBody.MovementType  movementType;

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     */
    MovingObjectModel(float x, float y) {
        super(x, y);
    }

    /**
     * Set the accelerating flag for this ship
     *
     * @param accelerating the accelerating tag
     */
    public void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
    }

    /**
     * Is the ship accelerating in this update
     *
     * @return the accelerating flag
     */
    public boolean isAccelerating() {
        return accelerating;
    }

    public MoveBody.MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MoveBody.MovementType movementType) {
        this.movementType = movementType;
    }
}