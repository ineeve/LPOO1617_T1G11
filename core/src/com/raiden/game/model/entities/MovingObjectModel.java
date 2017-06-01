package com.raiden.game.model.entities;

import com.raiden.game.physics_controller.movement.MoveManager;

/**
 * A model representing a the user space ship.
 */
public abstract class MovingObjectModel extends EntityModel implements DamageObject{
    /**
     * Is this ship accelerating in this update delta
     */
    private boolean accelerating = true;

    private MoveManager.MovementType  movementType;

    int hp = 0;

    int damage = 0;

    int armor = 0;

    /**
     * Creates a new ship model in a certain position
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

    public MoveManager.MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MoveManager.MovementType movementType) {
        this.movementType = movementType;
    }
    public void decreaseHP(int amountToDecrease){
        hp -= amountToDecrease;
    }

    public int getHp(){return hp;}

    @Override
    public int getDamage() {
        return damage == 0 ? hp : damage;
    }

    @Override
    public int getArmor(){
        return armor == 0 ? hp :armor;
    }

    @Override
    public void setArmor(int armor){
        this.armor = armor;
    }

    @Override
    public void setDamage(int damage){
        this.damage = damage;
    }
}