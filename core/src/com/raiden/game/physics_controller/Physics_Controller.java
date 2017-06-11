package com.raiden.game.physics_controller;

import com.badlogic.gdx.Game;
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

    /**
     * The gravity that is being applied to the world.
     */
    private final int GRAVITY = -10;

    /**
     * The arena width in meters.
     */
    public static final int ARENA_WIDTH = 27; //52

    /**
     * The arena height in meters.
     */
    public static final int ARENA_HEIGHT = 3530; //117 3530


    /**
     * The physics world controlled by this controller.
     */
    protected final World world;


    //An array with all the bodies active in the game.
    private ArrayList<DynamicBody> dynamicBodies;

    //The player1 spaceship
    private ShipBody airPlane1;

    //The player2 spaceship
    private ShipBody airPlane2;

    /**
     * Accumulator used to calculate the simulation step.
     */
    private float accumulator;

    //The camera used to render the view and to verify the position of all bodies.
    private OrthographicCamera camera;

    /**
     * Minimum time between consecutive shots in seconds
     */
    private final float TIME_BETWEEN_SHOTS = .2f;

    //Remaining time for the next shoot.
    private float timeToNextShoot;

    //An instance of this controller.
    private static Physics_Controller instance;

    //An instance of a controller factory, which is responsible for creating the bodies.
    private final ControllerFactory controllerFactory = ControllerFactory.getInstance();

    //An instance of the arena.
    private final Arena arena = Arena.getInstance();

    public ArrayList<DynamicBody> getDynamicBodies(){
        return dynamicBodies;
    }

    /**
     * The constructor, creates a new world and the bodies from the GameModel provided.
     * @param model The instance of GameModel, used to create the respective bodies.
     */
    private Physics_Controller(GameModel model){
        dynamicBodies = new ArrayList<DynamicBody>();
        world = new World(new Vector2(0,GRAVITY),true);
        world.setContactListener(this);
        createPlayersBodiesFromModel(model);
    }

    /**
     * Return and Sets the physics_controller if needed.
     * @return The current instance of the Physics Controller if exists, otherwise a new Physics_Controller.
     */
    public static Physics_Controller getInstance() {
        if (instance == null)
            instance = new Physics_Controller(GameModel.getInstance());
        return instance;
    }

    /**
     * Adds a dynamic body to this controller.
     * @param entityModel The model of the body to add.
     * @return the DynamicBody created.
     */
    public DynamicBody addDynamicBody(MovingObjectModel entityModel){

        DynamicBody body = controllerFactory.makeController(world, entityModel);
        dynamicBodies.add(body);
        return body;

    }

    /**
     * Adds an entire list of bodies to this controller.
     * @param entityModels The models used to generate the bodies.
     */
    public void addDynamicBodies(ArrayList<MovingObjectModel> entityModels){
        for(EntityModel modelEntity : entityModels){
            dynamicBodies.add(controllerFactory.makeController(world, modelEntity));
        }
    }

    /**
     * Checks if a body exists, and removes it if so.
     * @param body The body to be removed/destroyed.
     */
    public void destroyDynamicBody(DynamicBody body){
        if (dynamicBodies.contains(body)){
            dynamicBodies.remove(body);
            world.destroyBody(body.getBody());
        }
    }

    /**
     * Removes and entire list of bodies from this controller.
     * @param bodies The bodies to be removed.
     */
    public void destroyDynamicBodies(ArrayList<DynamicBody> bodies){
        for(DynamicBody body : bodies){
            destroyDynamicBody(body);
        }
    }

    /**
     * Sets the camera that shall be used to verify the bodies positions.
     * @param camera
     */
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

    /**
     * Removes from this controller all the bodies that are flagged to be removed.
     * Also removes the body from the game model.
     */
    public void removeFlaggedForRemoval(){
        for(int i = 0; i < dynamicBodies.size(); i++){
            DynamicBody body = dynamicBodies.get(i);
            EntityModel currentModel = (EntityModel)body.getUserData();
            if (currentModel.isFlaggedForRemoval()){
                destroyDynamicBody(body);
                GameModel.getInstance().deleteEntityModel(currentModel);
                i--;
            }
        }
    }

    /**
     * Corrects the position of the players ships if they are out of the viewport.
     * Destroys all the other bodies that are out of the viewport.
     */
    private void verifyPositionOfBodies(){
        for (int i = 0; i < dynamicBodies.size(); i++) {
            DynamicBody dynamicBody = dynamicBodies.get(i);
            Body body = dynamicBody.getBody();
            if (dynamicBody == airPlane1 || dynamicBody == airPlane2){
                verifyBounds(body, false);
                ((EntityModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
            }
            else {
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
    }

    /**
     * Verifies if the body is inside the viewport and arena bounds
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
        if (body.getPosition().x < 0){
            body.setTransform(0, body.getPosition().y, body.getAngle());
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        }

        if (body.getPosition().y < yLowerBound) {
            if (!delete){
                body.setTransform(body.getPosition().x, yLowerBound, body.getAngle());
                body.setLinearVelocity(body.getLinearVelocity().x,2);
            }
            else
                return true;
        }
        if (body.getPosition().x > ARENA_WIDTH){
            body.setTransform(ARENA_WIDTH, body.getPosition().y, body.getAngle());
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        }

        if (body.getPosition().y > yUpperBound) {
            if (!delete){
                body.setTransform(body.getPosition().x, yUpperBound, body.getAngle());
                body.setLinearVelocity(body.getLinearVelocity().x,0);
            }
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
     * Uses the model to create the bodies for the each player ship
     * @param model the Game Model to use
     */
    public void createPlayersBodiesFromModel(GameModel model) {
        for (Player player : model.getPlayers()) {
            DynamicBody newDynamicBody = controllerFactory.makeController(world, player.getShip());
            dynamicBodies.add(newDynamicBody);
            if (player.getID().compareTo(arena.getConfigCore().getmPlayerID()) == 0) {
                airPlane1 = (ShipBody) newDynamicBody;
                airPlane1.getBody().setGravityScale(0);
            } else {
                airPlane2 = (ShipBody) newDynamicBody;
                airPlane2.getBody().setGravityScale(0);
            }
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

    /**
     * Checks if the game has ended, if so -> tries to submit the score and communicates the game state to the level manager.
     */
    private void checkIfGameEnd() {
        boolean endGame = false;
        if(!LevelManager.isEndOfGame()) {
            if (GameModel.getInstance().getMyPlayer().getShip().isFlaggedForRemoval()) {
                LevelManager.setEndOfGame(true); endGame = true;
            }
            if (arena.getConfigCore().isMultiplayer() && arena.getConfigCore().isHost()) {
                if (GameModel.getInstance().getOtherPlayer().getShip().isFlaggedForRemoval()) {
                    LevelManager.setEndOfGame(true); endGame = true;
                }
            }
        }
        if (endGame){
            if (arena.getBroadcast() != null){
                arena.getBroadcast().submitScore(GameModel.getInstance().getMyPlayer().getScore());
            }
        }
    }

    /**
     * Handle collision between 2 bodies.
     * @param bodyA A body that collided
     * @param bodyB A body that collided
     */
    private void collisionHandler(Body bodyA, Body bodyB){
        MovingObjectModel aModel = (MovingObjectModel) bodyA.getUserData();
        MovingObjectModel bModel = (MovingObjectModel) bodyB.getUserData();
        if (bModel != null && aModel != null) {
            int aDamage = aModel.getDamage();
            double aDamagePercentage= (float) (30+ 70*Math.exp(-0.027*bModel.getArmor()))/100;
            if (bModel.getType() == EntityModel.ModelType.BULLET){
                bModel.setFlaggedForRemoval(true);
            }
            if(friendlyFire(aModel, bModel))
                return;
            bModel.decreaseHP((int) (aDamage * aDamagePercentage));
            if (bModel.getHp() <= 0) {
                if (!bModel.isFlaggedForRemoval()){
                    updateScores(bodyA,bodyB);
                }
                bModel.setFlaggedForRemoval(true);
            }
        }

    }

    /**
     *
     * @param aModel A model suspect of being part of friendly fire.
     * @param bModel A model suspect of being part of friendly fire.
     * @return true if friendly-fire between players has been detected, false otherwise.
     */
    public boolean friendlyFire(EntityModel aModel, EntityModel bModel) {
        if (aModel instanceof BulletModel){
            if(((BulletModel) aModel).getOwner() == GameModel.getInstance().getMyPlayer().getShip()){
                if(bModel == GameModel.getInstance().getMyPlayer().getShip())
                    return true;
                else if(arena.getConfigCore().isMultiplayer()){
                    if(bModel == GameModel.getInstance().getOtherPlayer().getShip())
                        return true;
                }
            }
            else if (arena.getConfigCore().isMultiplayer()){
                if(((BulletModel) aModel).getOwner() == GameModel.getInstance().getOtherPlayer().getShip()){
                    if(bModel == GameModel.getInstance().getMyPlayer().getShip())
                        return true;
                    else if (bModel == GameModel.getInstance().getOtherPlayer().getShip())
                        return true;
                }
            }
        }
        else if (aModel instanceof ShipModel && bModel instanceof ShipModel){
            if (arena.getConfigCore().isMultiplayer()){
                ShipModel myShip = GameModel.getInstance().getMyPlayer().getShip();
                ShipModel otherShip = GameModel.getInstance().getOtherPlayer().getShip();
                if ((aModel == myShip && bModel == otherShip) || (aModel == otherShip && bModel == myShip)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Increases score of a player, if is ship has killed some other body,
     * @param bodyA A body suspect of being involved in the killing of another body.
     * @param bodyB A body suspect of being involved in the killing of another body.
     */
    private void updateScores(Body bodyA, Body bodyB){
        //B is dead
        MovingObjectModel aModel = (MovingObjectModel) bodyA.getUserData();
        MovingObjectModel bModel = (MovingObjectModel) bodyB.getUserData();
        if (aModel instanceof BulletModel){
            BulletModel bulletModel = (BulletModel) aModel;
            if (bulletModel.getOwner() == GameModel.getInstance().getMyPlayer().getShip()){
                GameModel.getInstance().getMyPlayer().increaseScore();
            }
            if(arena.getConfigCore().isMultiplayer()) {
                if (bulletModel.getOwner() == GameModel.getInstance().getOtherPlayer().getShip())
                    GameModel.getInstance().getOtherPlayer().increaseScore();
            }
        } else {
            if (aModel == GameModel.getInstance().getMyPlayer().getShip()
                    && bModel instanceof ShipModel){
                GameModel.getInstance().getMyPlayer().increaseScore();
            }
            else if (arena.getConfigCore().isMultiplayer()){
                if(aModel == GameModel.getInstance().getOtherPlayer().getShip()
                        && bModel instanceof ShipModel)
                    GameModel.getInstance().getOtherPlayer().increaseScore();
            }
        }
    }


    /**
     *
     * @return the player1 airplane's body.
     */
    public ShipBody getAirPlane1() {
        return airPlane1;
    }

    /**
     *
     * @return the player2 airplane's body.
     */
    public ShipBody getAirPlane2() {
        return airPlane2;
    }
    /**
     * Sets the instance of this controller to null.
     */
    public static void clearInstance(){
        instance = null;
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
