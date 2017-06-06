package com.raiden.game.model.entities;


import java.io.Serializable;

/**
 * An abstract model representing an entity belonging to a game model.
 */
public abstract class EntityModel implements Serializable {
    public enum ModelType {
        AIRPLANE_1,
        AIRPLANE_2,
        AIRPLANE_3,
        TANK,
        ANY,
        COMET,
        OBSTACLE,
        BULLET;

        public static ModelType getRandom() {
            return values()[(int) (Math.random() * values().length-1)];
        }
    }

    static long Next_ID = 0;

    /**
     * The x-coordinate of this model in meters.
     */
    private float x;

    private long ID;
    /**
     * The y-coordinate of this model in meters.
     */
    private float y;

    protected int width;

    protected int height;

    private float rotation;

    private boolean flaggedForRemoval;

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     */
    EntityModel(float x, float y) {
        this.x = x;
        this.y = y;
        flaggedForRemoval =false;
        ID = Next_ID++;
    }

    /**
     * Returns the x-coordinate of this entity.
     *
     * @return The x-coordinate of this entity in meters.
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this entity.
     *
     * @return The y-coordinate of this entity in meters.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the position of this entity.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract ModelType getType();


    public void setRotation(float rad){
        this.rotation = rad;
    }

    public float getRotation(){
        return rotation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFlaggedForRemoval(boolean flaggedOrNot){
        flaggedForRemoval = flaggedOrNot;
    }

    public boolean isFlaggedForRemoval(){
        return flaggedForRemoval;
    }

    public long getID() {
        return ID;
    }
}