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
import com.raiden.game.Arena;
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

    private final int GRAVITY = -10;

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
        world = new World(new Vector2(0,GRAVITY),true);
        world.setContactListener(this);
        createBodiesFromModel(model);
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
        int n = 0;
        for(int i = 0; i < dynamicBodies.size() - n; i++) {
            MoveManager.moveBody(dynamicBodies.get(i), delta);
            if(dynamicBodies.get(i) instanceof ShipBody) {
                BulletModel bullet = ((ShipBody) dynamicBodies.get(i)).shoot(delta);
                if(bullet != null){
                    DynamicBody body = Physics_Controller.getInstance().addDynamicBody(bullet);
                    body.setVelocity(0, body.getMaxVelocity());
                    n++;
                }
            }
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
                Gdx.app.log("Physics_Controller.removeFlaggedForRemoval()","Destroying Dynamic Body");
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
                if(!((EntityModel) body.getUserData()).isPlayer()) {
                    GameModel.getInstance().deleteEntityModel((EntityModel) body.getUserData());
                    destroyDynamicBody(dynamicBody);
                    i--;
                }
            }
            else {
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            }
        }
    }

    /**
     * Verifies if the body is inside the arena bounds
     *
     * @param body The body to be verified.
     * @param delete True if this body should be deleted when it crosses the y bounds, false otherwise;
     * @return true if body should be deleted, 0 otherwise
     */
    public boolean verifyBounds(Body body, boolean delete) {
        float yLowerBound;
        float yUpperBound;
        if (camera == null){
            yLowerBound = 0;
            yUpperBound = ARENA_HEIGHT;
        }
        else{
            yLowerBound = (camera.position.y - camera.viewportHeight/2.0f) * PIXEL_TO_METER;
            yUpperBound = (camera.position.y + camera.viewportHeight/2.0f) * PIXEL_TO_METER;
        }
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

    /**
     * Uses the model to create the bodies for each Entity Model
     * @param model the Game Model to use
     */
    public void createBodiesFromModel(GameModel model){
        if ( Arena.getInstance().isHost() || !Arena.getInstance().isMultiplayer()) {
            for (EntityModel modelEntity : model.getEntityModels()) {
                dynamicBodies.add(controllerFactory.makeController(world, modelEntity));
            }
        }
        else {
            dynamicBodies.add(controllerFactory.makeController(world,model.getMyPlayer().getMyShip()));
        }
        if (dynamicBodies.size() > 0) {
            airPlane1 = (ShipBody) dynamicBodies.get(0);
        }
        actualPlayer = model.getMyPlayer();
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
                if (!bModel.isFlaggedForRemoval()){
                    updateScores(bodyA,bodyB);
                }
                bModel.setFlaggedForRemoval(true);

            }
            if (bModel.getType() == EntityModel.ModelType.BULLET){
                bModel.setFlaggedForRemoval(true);
            }
        }

    }

    private void updateScores(Body bodyA, Body bodyB){
        //B is dead
        MovingObjectModel aModel = (MovingObjectModel) bodyA.getUserData();
        MovingObjectModel bModel = (MovingObjectModel) bodyB.getUserData();
        if (aModel instanceof BulletModel){
            BulletModel bulletModel = (BulletModel) aModel;
            if (bulletModel.getOwner() == actualPlayer.getMyShip()){
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

    public static void clearInstance(){
        instance = null;
    }

    public Body getBodyByModel(EntityModel myShip) {
        for(DynamicBody body : dynamicBodies){
            if(((EntityModel) body.getUserData()).getID() == myShip.getID()){
                return body.getBody();
            }
        }
        return null;
    }
}
