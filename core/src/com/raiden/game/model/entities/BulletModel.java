package com.raiden.game.model.entities;

/**
 * Created by ineeve on 24-04-2017.
 */

public class BulletModel extends MovingObjectModel {

    private int damage;
    private float timeToLive;

    /**
     * Has this model been flagged for removal?
     */
    private boolean flaggedForRemoval = false;

    
    /**
     * Creates a new ship model in a certain position;
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    public BulletModel(float x, float y) {
        super(x, y);
        width = 50;
        height = 50;
    }

    /**
     * Decreases this bullet's time to live by delta seconds
     *
     * @param delta
     * @return
     */
    public boolean decreaseTimeToLive(float delta) {
        timeToLive -= delta;
        return  timeToLive < 0;
    }

    /**
     * Sets this bullet's time to live in seconds
     * @param timeToLive
     */
    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public ModelType getType() {
        return ModelType.BULLET;
    }

    public void setFlaggedForRemoval(boolean flaggedForRemoval) {
        this.flaggedForRemoval = flaggedForRemoval;
    }
}
