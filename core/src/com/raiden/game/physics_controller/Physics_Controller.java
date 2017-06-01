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
import com.raiden.game.Player;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.entities.ControllerFactory;
import com.raiden.game.physics_controller.entities.DynamicBody;
import com.raiden.game.physics_controller.entities.ShipBody;
import com.raiden.game.physics_controller.movement.MoveManager;
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
    private ShipBody airPlane1;


    /**
     * The player
     */

    private Player actualPlayer;

    /**
     * Accumulator used to calculate the simulation step.
     */
    private float accumulator;

    private OrthographicCamera camera;

    /**
     * Minimum time between consecutive shots in seconds
     */
    private final float TIME_BETWEEN_SHOTS = .2f;

    private float timeToNextShoot;

    private static Physics_Controller instance;

    private ControllerFactory controllerFactory = ControllerFactory.getInstance();

    private Physics_Controller(GameModel model){
        dynamicBodies = new ArrayList<DynamicBody>();
        world = new World(new Vector2(0,-10),true);
        world.setContactListener(this);
        for(EntityModel modelEntity : model.getEntityModels()){
            dynamicBodies.add(controllerFactory.makeController(world, modelEntity));
        }

        airPlane1 = (ShipBody) dynamicBodies.get(0);
        actualPlayer = model.getMyPlayer();
    }

    public static Physics_Controller getInstance() {
        if (instance == null)
            instance = new Physics_Controller(GameModel.getInstance());
        return instance;
    }

    public DynamicBody addDynamicBody(MovingObjectModel entityModel){

        DynamicBody body = controllerFactory.makeController(world, entityModel);
        dynamicBodies.add(body);
        return body;

    }

    public void addDynamicBodies(ArrayList<MovingObjectModel> entityModels){
        for(EntityModel modelEntity : entityModels){
            dynamicBodies.add(controllerFactory.makeController(world, modelEntity));
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
        removeFlaggedForRemoval();

        if(!LevelManager.isEndOfGame()){
            BulletModel bullet = airPlane1.shoot(delta);
            if (bullet != null){
                bullet.setOwner(actualPlayer);
            }
        }
        for(DynamicBody body : dynamicBodies){
            MoveManager.moveBody(body, delta);
        }

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
                Gdx.app.log("Physics_COntroller.removeFlaggedForRemoval()","Destroying Dynamic Body");
                destroyDynamicBody(body);
                GameModel.getInstance().deleteEntityModel(currentModel);
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
                GameModel.getInstance().deleteEntityModel((EntityModel) body.getUserData());
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
            if (bModel.getHp() <= 0) {
                bModel.setFlaggedForRemoval(true);
                setScores(bodyA,bodyB);
            }
            if (bModel.getType() == EntityModel.ModelType.BULLET){
                bModel.setFlaggedForRemoval(true);
            }
        }

    }

    private void setScores(Body bodyA, Body bodyB){
        //B is dead
        MovingObjectModel aModel = (MovingObjectModel) bodyA.getUserData();
        MovingObjectModel bModel = (MovingObjectModel) bodyB.getUserData();
        if (aModel instanceof BulletModel){
            BulletModel bulletModel = (BulletModel) aModel;
            if (bulletModel.getOwner() != null){
                actualPlayer.increaseScore();
            }
        }
        else if (bodyA == airPlane1.getBody() && bModel instanceof ShipModel){
            actualPlayer.increaseScore();
        }
    }

    public Player getActualPlayer() {
        return actualPlayer;
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

    public ShipBody getAirPlane1() {
        return airPlane1;
    }
}
