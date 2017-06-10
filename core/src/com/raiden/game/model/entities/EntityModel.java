package com.raiden.game.model.entities;


import java.io.Serializable;

/**
 * An abstract model representing an entity belonging to a game model.
 */
public abstract class EntityModel implements Serializable {
    //Id used for serialization.
    private static final long serialVersionUID = 6L;

    //Enumerates all types of models used in this game.
    public enum ModelType {
        AIRPLANE_1,
        AIRPLANE_2,
        AIRPLANE_3,
        TANK,
        ANY,
        COMET,
        OBSTACLE,
        BULLET;

        // Returns a ModelType that is surely not a bullet.
        public static ModelType getRandom() {
            return values()[(int) (Math.random() * values().length-1)];
        }
    }

    //Id variable used to assign a different id to each object
    private static long Next_ID = 0;


    //The x-coordinate of this model in meters.
    private float x;

    //The y-coordinate of this model in meters.
    private float y;

    //The width in pixels
    protected int width;

    //The height in pixels
    protected int height;

    //The rotation on radians.
    private float rotation;

    //True if this object is flagged to be removed from the current game model.
    private boolean flaggedForRemoval;

    //An unique id for each object.
    private long ID;

    //A static method to reset the Next_ID variable.
    public static void resetNxt_ID() {
        Next_ID = 0;
    }

    /**
     * Constructs a model with a position.
     * Sets the ID of the new model.
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

    /**
     *
     * @return The type of model that this object is. To be used with polymorphism.
     *
     */
    public abstract ModelType getType();

    /**
     *
      * @param rad The rotation of the object in radians.
     */
    public void setRotation(float rad){
        this.rotation = rad;
    }

    /**
     *
     * @return The rotation of the object in radians.
     */
    public float getRotation(){
        return rotation;
    }

    /**
     *
     * @return The width of the object in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param width The width of the object in pixels
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return The height of the object in pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param height The height of the object in pixels
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the object flagged or not for removal.
     * @param flaggedOrNot True if the object should be removed from the current game, false otherwise.
     */
    public void setFlaggedForRemoval(boolean flaggedOrNot){
        flaggedForRemoval = flaggedOrNot;
    }

    /**
     *
     * @return True if the object is flagged to be removed from the current game.
     */
    public boolean isFlaggedForRemoval(){
        return flaggedForRemoval;
    }

    /**
     *
     * @return The unique ID of this model.
     */
    public long getID() {
        return ID;
    }
}