package com.raiden.game.model.entities;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.raiden.game.physics_controller.movement.MoveManager;

/**
 * A model representing a the user space ship.
 */
public abstract class MovingObjectModel extends EntityModel implements DamageObject{
    //Id used for serialization.
    private static final long serialVersionUID = 7L;

    //Type of Movement assigned to this object.
    private MoveManager.MovementType  movementType;

    //The initial hp of the object is 0.
    protected int hp = 0;

    //The initial damage of the object is 0.
    protected int damage = 0;

    //The initial armor of the object is 0.
    protected int armor = 0;


    /**
     * Creates a new Moving Object model in a certain position
     *
     * @param x the x-coordinate in meters
     * @param y the y-coordinate in meters
     */
    MovingObjectModel(float x, float y) {
        super(x, y);
    }

    /**
     *
     * @return The type of movement associated with this model.
     */
    public MoveManager.MovementType getMovementType() {
        return movementType;
    }

    /**
     *
     * @param movementType Sets the type of movement associated with this object.
     */
    public void setMovementType(MoveManager.MovementType movementType) {
        this.movementType = movementType;
    }

    /**
     * Decreases the hp of this model. It should be called when the object suffers damage.
     * @param amountToDecrease The amount of hp to decrease.
     */
    public void decreaseHP(int amountToDecrease){
        hp -= amountToDecrease;
    }

    /**
     * @return The current hp of this model.
     */
    public int getHp(){return hp;}

    /**
     * @return The damage of this model if damage != 0, otherwise it returns the hp.
     */
    @Override
    public int getDamage() {
        return damage == 0 ? hp : damage;
    }

    /**
     * @return The armor of this model if armor != 0, otherwise it returns the hp.
     */
    @Override
    public int getArmor(){
        return armor == 0 ? hp :armor;
    }

    /**
     * @param armor the amount of armor
     */
    @Override
    public void setArmor(int armor){
        this.armor = armor;
    }

    /**
     * @param damage The damage of the object
     */
    @Override
    public void setDamage(int damage){
        this.damage = damage;
    }
}