package com.raiden.game.model.entities;


/**
 * A class that contains all the relevant data about a bullet.
 */
public class BulletModel extends MovingObjectModel {
    //Id used for serialization.
    private static final long serialVersionUID = 4L;

    //The model that fired this bullet;
    private ShipModel owner;

    /**
     * Creates a new bullet model in a certain position;
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public BulletModel(float x, float y) {
        super(x, y);
        width = 20;
        height = 20;
        damage = 25;
    }


    @Override
    public ModelType getType() {
        return ModelType.BULLET;
    }

    /**
     * Return the intrinsic damage of a bullet. This is the damage that the bullet would do to an
     * object without armor.
     * @return the amount of damage that the bullet can do.
     */
    public int getDamage(){
        return damage;
    }

    /**
     * Retrieves the owner of the bullet
     * @return The model that fired the bullet.
     */
    public ShipModel getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the bullet
     * @param owner The model that fired the bullets
     */
    public void setOwner(ShipModel owner) {
        this.owner = owner;
    }
}
