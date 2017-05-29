package com.raiden.game.physics_controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.PVE_GameModel;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.physics_controller.entities.BulletBody;
import com.raiden.game.physics_controller.entities.ControllerFactory;
import com.raiden.game.physics_controller.entities.DynamicBody;
import com.raiden.game.physics_controller.entities.ShipPhysics;
import com.raiden.game.physics_controller.movement.MoveBody;

import java.util.ArrayList;

import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;

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
    public static final int ARENA_HEIGHT = 3530; //117


    /**
     * The physics world controlled by this controller.
     */
    private final World world;

    private ArrayList<DynamicBody> dynamicBodies;

    /**
     * The spaceship body.
     */
    private final ShipPhysics airPlane1;

    /**
     * Accumulator used to calculate the simulation step.
     */
    private float accumulator;

    private OrthographicCamera camera;

    private GameModel model;

    /**
     * The bullet speed
     */
    private static final float BULLET_SPEED = 20f;

    /**
     * Minimum time between consecutive shots in seconds
     */
    private final float TIME_BETWEEN_SHOTS = .2f;

    private float timeToNextShoot;

    Physics_Controller(GameModel model){
        dynamicBodies = new ArrayList<DynamicBody>();
        world = new World(new Vector2(0,0),true);
        this.model = model;

        for(EntityModel modelEntity : model.getEntityModels()){
            dynamicBodies.add(ControllerFactory.makeController(world, modelEntity));
        }
        for (EntityModel bulletModel : model.getBullets()){
            dynamicBodies.add(ControllerFactory.makeController(world,bulletModel));
        }

        airPlane1 = (ShipPhysics) dynamicBodies.get(0);

    }

    public void addDynamicBody(MovingObjectModel entityModel){
        dynamicBodies.add(ControllerFactory.makeController(world, entityModel));
    }

    public void addDynamicBodies(ArrayList<MovingObjectModel> entityModels){
        for(EntityModel modelEntity : entityModels){
            dynamicBodies.add(ControllerFactory.makeController(world, modelEntity));
        }
    }

    public void destroyDynamicBody(DynamicBody body){
        dynamicBodies.remove(body);
        world.destroyBody(body.getBody());
    }

    public void destroyDynamicBodies(ArrayList<DynamicBody> bodies){
        for(DynamicBody body : bodies){
            dynamicBodies.remove(body);
            world.destroyBody(body.getBody());
        }
    }

    public void setCamera(OrthographicCamera camera){
        this.camera = camera;
    }
    /**
     * Calculates the next physics step of duration delta (in seconds).
     *
     * @param delta The size of this physics step in seconds.
     */
    public void update(float delta) {
        PVE_GameModel.getInstance().update(delta);

        for(DynamicBody body : dynamicBodies){
            MoveBody.moveBody(body, delta);
        }
        timeToNextShoot -= delta;

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

       verifyPositionOfBodies();
    }

    private void verifyPositionOfBodies(){
        verifyBounds(airPlane1.getBody(), false);
        for (int i = 0; i < dynamicBodies.size(); i++) {
            DynamicBody dynamicBody = dynamicBodies.get(i);
            Body body = dynamicBody.getBody();
            if(verifyBounds(body, true)) {
                model.deleteEntityModel((EntityModel) body.getUserData());
                destroyDynamicBody(dynamicBody);
                i--;
            }
            else
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    /**
     * Verifies if the body is inside the arena bounds and if not
     * wraps it around to the other side.
     *
     * @param body The body to be verified.
     */
    private boolean verifyBounds(Body body, boolean delete) {
        float yLowerBound = (camera.position.y - camera.viewportHeight/2.0f) * PIXEL_TO_METER;
        float yUpperBound = (camera.position.y + camera.viewportHeight/2.0f) * PIXEL_TO_METER;
        if (body.getPosition().x < 0 && !delete)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < yLowerBound) {
            if (!delete)
                body.setTransform(body.getPosition().x, yLowerBound, body.getAngle());
            else
                return true;
        }
        if (body.getPosition().x > ARENA_WIDTH && !delete)
            body.setTransform(ARENA_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > yUpperBound && !delete)
            body.setTransform(body.getPosition().x, yUpperBound, body.getAngle());
        return false;
    }

    /**
     * Returns the world controlled by this controller. Needed for debugging purposes only.
     *
     * @return The world controlled by this controller.
     */
    public World getWorld() {
        return world;
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
        airPlane1.setVelocity(-acceX * Airplane_1_Model.MAXVELOCITY_Default, acceY);
    }

    public float getXVelocityofPlayer1() {
        return airPlane1.getBody().getLinearVelocity().x;
    }

    public float getYVelocityofPlayer1() {
        return airPlane1.getBody().getLinearVelocity().y;
    }

    /**
     * Shoots a bullet from the spaceship at 10m/s
     */
    public void shoot() {
        if (timeToNextShoot < 0) {
            BulletModel bullet = PVE_GameModel.getInstance().createBullet(PVE_GameModel.getInstance().getPlayer1());
            BulletBody body = new BulletBody(world, bullet);
            body.setVelocity(0,BULLET_SPEED);
            timeToNextShoot = TIME_BETWEEN_SHOTS;
        }
    }

}
