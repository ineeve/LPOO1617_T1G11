package com.raiden.game.physics_controller.entities;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.physics_controller.movement.MoveManager;

import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;


/**
 * Wrapper class that represents an abstract physical
 * body supported by a Box2D body.
 */
public abstract class DynamicBody {
    /**
     * The Box2D body that supports this body.
     */
    final Body body;

    //The density of this body.
    private float density;
    //The friction of this body.
    private float friction;
    //The restitution of this body.
    private float restitution;
    //The width of this body in pixels.
    protected int width;
    //The height of this body in pixels.
    protected int height;
    //The maxVelocity of this body in meters/second.
    protected float maxVelocity;

    //Time acumulator, to be used with the movement.
    private float t = 0;

    //A type of movement associated with this body.
    MoveManager.MovementType movementType = null;

    /**
     * Constructs a body representing a model in a certain world.
     *
     * @param world The world this body lives on.
     * @param model The model representing the body.
     */
    DynamicBody(World world, EntityModel model) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    /**
     * Helper method to create a polygon fixture represented by a set of vertexes.
     *
     * @param vertexes The vertexes defining the fixture in pixels so it is
     *                 easier to get them from a bitmap image.
     * @param width The width of the bitmap the vertexes where extracted from.
     * @param height The height of the bitmap the vertexes where extracted from.
     */
    final void createFixture(float[] vertexes, int width, int height) {
        // Transform pixels into meters, center and invert the y-coordinate
        for (int i = 0; i < vertexes.length; i++) {
            if (i % 2 == 0) vertexes[i] -= width / 2;   // center the vertex x-coordinate
            if (i % 2 != 0) vertexes[i] -= height / 2;  // center the vertex y-coordinate

            if (i % 2 != 0) vertexes[i] *= -1;          // invert the y-coordinate

            vertexes[i] *= PIXEL_TO_METER;              // scale from pixel to meter
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertexes);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);

        polygon.dispose();
    }

    /**
     * @param density The density of the fixture. How heavy it is in relation to its area.
     */
    void setDensity(float density){
        this.density = density;
    }

    /**
     * @param friction The friction of the fixture. How slippery it is.
     */
    void setFriction(float friction){
        this.friction = friction;
    }

    /**
     * @param restitution The restitution of the fixture. How much it bounces.
     */
    void setRestitution(float restitution){
        this.restitution = restitution;
    }


    /**
     * Wraps the getX method from the Box2D body class.
     *
     * @return the x-coordinate of this body.
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Wraps the getY method from the Box2D body class.
     *
     * @return the y-coordinate of this body.
     */
    public float getY() {
        return body.getPosition().y;
    }

    /**
     * Wraps the getAngle method from the Box2D body class.
     *
     * @return the angle of rotation of this body.
     */
    public float getAngle() {
        return body.getAngle();
    }

    /**
     * Wraps the setTransform method from the Box2D body class.
     *
     * @param x the new x-coordinate for this body
     * @param y the new y-coordinate for this body
     * @param angle the new rotation angle for this body
     */
    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /**
     * Wraps the setAngularVelocity method from the Box2D body class.
     *
     * @param omega the new angular velocity angle for this body
     */
    public void setAngularVelocity(float omega) {
        body.setAngularVelocity(omega);
    }

    /**
     * Wraps the applyForceToCenter method from the Box2D body class.
     *
     * @param forceX the x-component of the force to be applied
     * @param forceY the y-component of the force to be applied
     * @param awake should the body be awaken
     */
    public void applyForceToCenter(float forceX, float forceY, boolean awake) {
        body.applyForceToCenter(forceX, forceY, awake);
    }

    /**
     * Wraps the getUserData method from the Box2D body class.
     *
     * @return the user data
     */
    public Object getUserData() {
        return body.getUserData();
    }

    /**
     * Sets the linear velocity for this body
     * @param velX xVelocity in meters/second.
     * @param velY yVelocity in meters/second.
     */
    public void setVelocity(float velX, float velY){
        body.setLinearVelocity(velX, velY);
    }

    /**
     *
     * @return the libgdx body associated with this DynamicBody.
     */
    public Body getBody(){
        return body;
    }

    /**
     *
     * @return the t varaible
     */
    public float getT() {
        return t;
    }

    /**
     *
     * @param t the t variable
     */
    public void setT(float t) {
        this.t = t;
    }

    /**
     * Sets the movement type for this dynamic body.
     * @param movementType the movement type.
     */
    public void setMovementType(MoveManager.MovementType movementType) {
        this.movementType = movementType;
    }

    /**
     *
     * @return The movement type associated with this body.
     */
    public MoveManager.MovementType getMovementType() {
        return movementType;
    }

    /**
     *
     * @param width The width in pixels
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @param height The height in pixels
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @return The maximum velocity of this body.
     */

    public float getMaxVelocity() {
        return maxVelocity;
    }

    /**
     *
     * @param maxVelocity the max velocity to associate with this body.
     */
    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    /**
     *
     * @return The speed in the x Axis in Meter/second.
     */
    public float getXVelocity() {
        return body.getLinearVelocity().x;
    }

    /**
     *
     * @return The speed in the y Axis in Meter/second.
     */
    public float getYVelocity() {
        return body.getLinearVelocity().y;
    }
}