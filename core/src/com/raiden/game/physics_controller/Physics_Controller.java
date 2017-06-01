package com.raiden.game.physics_controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.PVE_GameModel;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.physics_controller.entities.BulletBody;
import com.raiden.game.physics_controller.entities.ControllerFactory;
import com.raiden.game.physics_controller.entities.DynamicBody;
import com.raiden.game.physics_controller.entities.ShipPhysics;
import com.raiden.game.physics_controller.movement.MoveBody;
import com.raiden.game.screen.LevelManager;

import java.util.ArrayList;

import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;

/**
 * Controlls the physics Aspects of the PVE Game
 */

public class Physics_Controller implements ContactListener{
    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 27; //52

    /**
     * The arena height in meters.
     */
    public static final int ARENA_HEIGHT = 3530; //117


    /**
     * The physics world controlled by this controller.
     */
    protected final World world;

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
     * Minimum time between consecutive shots in seconds
     */
    private final float TIME_BETWEEN_SHOTS = .2f;

    private float timeToNextShoot;

    private static Physics_Controller instance;

    private Physics_Controller(GameModel model){
        dynamicBodies = new ArrayList<DynamicBody>();
        world = new World(new Vector2(0,-10),true);
        world.setContactListener(this);
        this.model = model;
        for(EntityModel modelEntity : model.getEntityModels()){
            dynamicBodies.add(ControllerFactory.makeController(world, modelEntity));
        }

        airPlane1 = (ShipPhysics) dynamicBodies.get(0);

    }

    public static Physics_Controller getInstance() {
        if (instance == null)
            instance = new Physics_Controller(PVE_GameModel.getInstance());
        return instance;
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
        if (dynamicBodies.contains(body)){
            dynamicBodies.remove(body);
            world.destroyBody(body.getBody());
        }
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
        if(!LevelManager.isEndOfGame())
            shoot();
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

        removeFlaggedForRemoval();
        verifyPositionOfBodies();

    }

    public void removeFlaggedForRemoval(){
        for(int i = 0; i < dynamicBodies.size(); i++){
            DynamicBody body = dynamicBodies.get(i);
            EntityModel currentModel = (EntityModel)body.getUserData();
            if (currentModel.isFlaggedForRemoval()){
                Gdx.app.log("Physics_COntroller.removeFlaggedForRemoval()","Destroying Dynamic Body");
                destroyDynamicBody(body);
                model.deleteEntityModel(currentModel);
                i--;
            }
        }
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
            else {
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            }
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
        if (body.getPosition().x < 0)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < yLowerBound) {
            if (!delete)
                body.setTransform(body.getPosition().x, yLowerBound, body.getAngle());
            else
                return true;
        }
        if (body.getPosition().x > ARENA_WIDTH)
            body.setTransform(ARENA_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > yUpperBound) {
            if (!delete)
                body.setTransform(body.getPosition().x, yUpperBound, body.getAngle());
            else
                return true;
        }
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
     * Sets the player 1 spaceship velocity.
     *
     * @param velX The velocity in the X-Axis, which will be multiplied by a correction constant;
     * @param velY The velocity in the Y-Axis
     */

    public void setVelocityofPlayer1(float velX, float velY) {
        float vel_Correction = 5.5f;
        airPlane1.setVelocity(-velX * vel_Correction, velY);
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
    private void shoot() {
        if (timeToNextShoot < 0) {
            BulletModel bullet = model.createBullet(model.getPlayer1());
            bullet.setFlaggedForRemoval(false);
            DynamicBody body = new BulletBody(world, bullet);
            body.setVelocity(0,body.getMaxVelocity());
            dynamicBodies.add(body);
            timeToNextShoot = TIME_BETWEEN_SHOTS;
        }
    }

    /*
 * A contact between two objects was detected
 *
 * @param contact the detected contact
 */
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        collisionHandler(bodyA,bodyB);
        collisionHandler(bodyB,bodyA);
        if(!LevelManager.isEndOfGame())
            checkIfGameEnd();
    }

    private void checkIfGameEnd() {
        if(((MovingObjectModel) airPlane1.getUserData()).isFlaggedForRemoval()){
            LevelManager.setEndOfGame(true);
        }
    }

    private void collisionHandler(Body bodyA, Body bodyB){
        MovingObjectModel aModel = (MovingObjectModel) bodyA.getUserData();
        MovingObjectModel bModel = (MovingObjectModel) bodyB.getUserData();
        if (bModel != null && aModel != null) {
            int aDamage = aModel.getDamage();
            double aDamagePercentage= (float) (30+ 70*Math.exp(-0.027*bModel.getArmor()))/100;
            bModel.decreaseHP((int) (aDamage * aDamagePercentage));
            if (bModel.getHp() <= 0 || bModel.getType() == EntityModel.ModelType.BULLET) {
                bModel.setFlaggedForRemoval(true);
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}
