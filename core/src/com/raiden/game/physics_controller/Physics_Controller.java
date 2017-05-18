package com.raiden.game.physics_controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.physics_controller.entities.AirPlane_1;

/**
 * Controlls the physics Aspects of the PVE Game
 */

public abstract class Physics_Controller {
    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 52;

    /**
     * The arena height in meters.
     */
    public static final int ARENA_HEIGHT = 117;

    /**
     * The rotation speed in radians per second.
     */
    private static final float ROTATION_SPEED = 5f;

    /**
     * The acceleration impulse in newtons.
     */
    private static final float MAX_ACCELERATION_FORCE = 1f;

    /**
     * The physics world controlled by this controller.
     */
    private final World world;

    /**
     * The spaceship body.
     */
    private final AirPlane_1 airPlane1;

    /**
     * Accumulator used to calculate the simulation step.
     */
    private float accumulator;

    Physics_Controller(GameModel model){

        world = new World(new Vector2(0,0),true);
        airPlane1 = new AirPlane_1(world, model.getPlayer1());

    }

    /**
     * Calculates the next physics step of duration delta (in seconds).
     *
     * @param delta The size of this physics step in seconds.
     */
    public void update(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            verifyBounds(body);
            ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            ((EntityModel) body.getUserData()).setRotation(body.getAngle());
        }
    }

    /**
     * Verifies if the body is inside the arena bounds and if not
     * wraps it around to the other side.
     *
     * @param body The body to be verified.
     */
    private void verifyBounds(Body body) {
        if (body.getPosition().x < 0)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, 0, body.getAngle());

        if (body.getPosition().x > ARENA_WIDTH)
            body.setTransform(ARENA_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > ARENA_HEIGHT)
            body.setTransform(body.getPosition().x, ARENA_HEIGHT, body.getAngle());
    }

    /**
     * Returns the world controlled by this controller. Needed for debugging purposes only.
     *
     * @return The world controlled by this controller.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Rotates the spaceship left. The rotation takes into consideration the
     * constant rotation speed and the delta for this simulation step.
     *
     * @param delta Duration of the rotation in seconds.
     */
    public void rotateLeft(float delta) {
        airPlane1.setTransform(airPlane1.getX(), airPlane1.getY(), airPlane1.getAngle() + ROTATION_SPEED * delta);
        airPlane1.setAngularVelocity(0);
    }

    /**
     * Rotates the spaceship right. The rotation takes into consideration the
     * constant rotation speed and the delta for this simulation step.
     *
     * @param delta Duration of the rotation in seconds.
     */
    public void rotateRight(float delta) {
        airPlane1.getX();

        airPlane1.setTransform(airPlane1.getX(), airPlane1.getY(), airPlane1.getAngle() - ROTATION_SPEED * delta);
        airPlane1.setAngularVelocity(0);
    }

    //TODO: Complete comment

    /**
     * Accelerates the spaceship. The acceleration takes into consideration the
     * constant acceleration force and the delta for this simulation step.
     *
     * @param acceX
     * @param acceY
     */

    public void setVelocityofPlayer1(float acceX, float acceY) {
        airPlane1.setVelocity(-acceX * Airplane_1_Model.MAXVELOCITY, acceY);
    }

}
