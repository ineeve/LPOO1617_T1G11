package com.raiden.game.model.entities;


/**
 * Created by ineeve on 24-04-2017.
 */

public class BulletModel extends MovingObjectModel {
    private static final long serialVersionUID = 4L;

    private ShipModel owner;

    /**
     * Creates a new ship model in a certain position;
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

    public int getDamage(){
        return damage;
    }

    public ShipModel getOwner() {
        return owner;
    }

    public void setOwner(ShipModel owner) {
        this.owner = owner;
    }
}
