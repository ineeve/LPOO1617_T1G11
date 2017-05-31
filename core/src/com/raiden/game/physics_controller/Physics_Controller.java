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
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.CometModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
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

public class Physics_Controller implements ContactListener{
    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 30; //52

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
     * The bullet speed
     */
    private static final float BULLET_SPEED = 20f;

    /**
     * Minimum time between consecutive shots in seconds
     */
    private final float TIME_BETWEEN_SHOTS = .2f;

    private float timeToNextShoot;

    private static Physics_Controller instance;

    private Physics_Controller(GameModel model){
        dynamicBodies = new ArrayList<DynamicBody>();
        world = new World(new Vector2(0,-2),true);
        world.setContactListener(this);
        this.model = model;
        for(EntityModel modelEntity : model.getEntityModels()){
            dynamicBodies.add(ControllerFactory.makeController(world, modelEntity));
        }

        airPlane1 = (ShipPhysics) dynamicBodies.get(0);

    }

    public static Physics_Controller getInstance(GameModel model) {
        if (instance == null)
            instance = new Physics_Controller(model);
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

    public void removeFlaggedForRemoval(){
        for(int i = 0; i < dynamicBodies.size(); i++){
            DynamicBody body = dynamicBodies.get(i);
            EntityModel currentModel = (EntityModel)body.getUserData();
            if (currentModel.isFlaggedForRemoval()){
                Gdx.app.log("Destroying","Dynamic Body");
                destroyDynamicBody(body);
                Gdx.app.log("Destroy","Removed Body");
                currentModel.setFlaggedForRemoval(false);
                model.deleteEntityModel(currentModel);
                Gdx.app.log("Destroy","Removed Model");
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
    public void shoot() {
        if (timeToNextShoot < 0) {
            Gdx.app.log("Controller","Creating Bullet");
            BulletModel bullet = model.createBullet(model.getPlayer1());
            bullet.setFlaggedForRemoval(false);
            BulletBody body = new BulletBody(world, bullet);
            body.setVelocity(0,BULLET_SPEED);
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
        Object modelA = bodyA.getUserData();
        Object modelB = bodyB.getUserData();
        if (modelA instanceof BulletModel && modelB instanceof ShipModel)
            bulletCollision(bodyA, bodyB);
        else if (modelA instanceof ShipModel && modelB instanceof BulletModel)
            bulletCollision(bodyB, bodyA);
        else if (modelA instanceof MovingObjectModel && modelB instanceof ShipModel){
            playerColision(bodyA,bodyB);
        }
        else if (modelB instanceof MovingObjectModel && modelA instanceof ShipModel){
            playerColision(bodyB,bodyA);
        }

    }

    private void playerColision(Body killerBody,Body shipBody){
        endGame();
    }

    private void endGame(){
        Gdx.app.log("Game Over","Game Over");
    }

    private void  bulletCollision(Body bulletBody,Body bodyB){
        ShipModel bModel = (ShipModel) bodyB.getUserData();
        BulletModel bulletModel = (BulletModel) bulletBody.getUserData();
        if (bModel != null && bulletModel != null) {
            int bulletDamage = bulletModel.getDamage();
            double damagePercentage= (float) (30+ 70*Math.exp(-0.027*bModel.getArmor()))/100;
            bModel.decreaseHP((int) (bulletDamage*damagePercentage));
            Gdx.app.log("Damage","Percentage =" + damagePercentage);
            Gdx.app.log("Collision", "Moving Object HP =" + bModel.getHp());
            if (bModel.getHp() < 0) {
                bModel.setFlaggedForRemoval(true);
            }
            bulletModel.setFlaggedForRemoval(true);
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
