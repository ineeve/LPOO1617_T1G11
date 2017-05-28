package com.raiden.game.model.entities;


/**
 * An abstract model representing an entity belonging to a game model.
 */
public abstract class EntityModel {
    public enum ModelType {
        AIRPLANE_1,
        AIRPLANE_2,
        AIRPLANE_3,
        TANK,
        BULLET,
        COMET,
        OBSTACLE,
        ANY;

        public static ModelType getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    /**
     * The x-coordinate of this model in meters.
     */
    private float x;

    /**
     * The y-coordinate of this model in meters.
     */
    private float y;

    protected int width;

    protected int height;

    private float rotation;

    /**
     * Constructs a model with a position and a rotation.
     *
     * @param x The x-coordinate of this entity in meters.
     * @param y The y-coordinate of this entity in meters.
     */
    EntityModel(float x, float y) {
        this.x = x;
        this.y = y;
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
}