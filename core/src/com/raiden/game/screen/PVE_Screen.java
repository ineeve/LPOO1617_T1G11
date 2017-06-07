package com.raiden.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.raiden.game.Arena;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.entities.EntityView;
import com.raiden.game.screen.entities.ViewFactory;

import java.util.Iterator;

import static com.raiden.game.physics_controller.Physics_Controller.ARENA_HEIGHT;
import static com.raiden.game.physics_controller.Physics_Controller.ARENA_WIDTH;

/**
 * A view representing the game screen. Draws all the other views and
 * controls the camera.
 */
public class PVE_Screen extends ScreenAdapter {

    /**
     * Used to debug the position of the physics fixtures
     */
    private static final boolean DEBUG_PHYSICS = true;

    private static PVE_Screen instance;

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.02f;

    /**
     * The game this screen belongs to.
     */
    private final Arena game;

    /**
     * The physics controller for this game.
     */
    private final Physics_Controller controller;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private static final int CAMERA_Y_SPEED = 50;
    private static final int CAMERA_X_SPEED = 200;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    private LevelManager levelManager;

    private String scoreText;

    private BitmapFont scoreBitmap;

    private boolean accelerometerAvail;


    //-----------------------------------------METHODS------------------------------------------------//
    public static PVE_Screen getInstance(){
        if(instance == null){
            instance = new PVE_Screen(Arena.getInstance(),Physics_Controller.getInstance());
        }
        return instance;
    }

    public static void clearInstance(){
        instance = null;
    }

    /**
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     * @param controller The physics controller
     */
    private PVE_Screen(Arena game, Physics_Controller controller) {
        this.game = game;
        this.controller = controller;
        camera = createCamera();
        controller.setCamera(camera);
        levelManager = new LevelManager();
        instance = this;
        initializeBitmapFont();
        accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        loadMusicAndStartPlaying();
    }

    private void loadMusicAndStartPlaying(){
        Music myMusic = game.getAssetManager().get("Oxia-Domino (Robag's Lasika Cafa Nb).mp3", Music.class);
        myMusic.setLooping(true);
        myMusic.play();
    }

    private void initializeBitmapFont() {
        scoreText = "score: 0";
        scoreBitmap = new BitmapFont();
        scoreBitmap.getData().setScale(5);
        scoreBitmap.setColor(0, 0, 0, 1.0f);
    }

    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        float viewport_width = 20;
        float viewport_height = viewport_width * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        OrthographicCamera camera = new OrthographicCamera(viewport_width / PIXEL_TO_METER, viewport_height / PIXEL_TO_METER);
        ShipModel myShip = GameModel.getInstance().getMyPlayer().getMyShip();
        camera.position.set(myShip.getX()/PIXEL_TO_METER, myShip.getY()/PIXEL_TO_METER, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    /**
     * Function to get Camera of the scene.
     *
     * @return Camera of scene.
     */
    OrthographicCamera getCamera() {
        return camera;
    }

    private void updateScene(float delta){
        if(!LevelManager.isEndOfGame()){
            handleInputs(delta);
        }
        controller.update(delta);
        if (Arena.getInstance().isHost() && Arena.getInstance().isMultiplayer()){
            game.getBroadcast().sendMessage_from_Host(GameModel.getInstance());
        }else if (Arena.getInstance().isMultiplayer()){
            game.getBroadcast().sendMessage_from_Client(GameModel.getInstance().getMyPlayer().getMyShip());
        }
        updateCameraPosition(delta);
        verifyCameraBounds(delta);
        camera.update();

    }

    /**
     * Renders this screen, and update all game state.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        // Just the host in case of being a multi-player
        // game or on single-player game update the level.
        if ((Arena.getInstance().isHost() && Arena.getInstance().isMultiplayer()) || !Arena.getInstance().isMultiplayer()){
            levelManager.updateLevel(this, delta);
        }
        updateScene(delta);

        game.getBatch().setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.getBatch().begin();
        drawBackground();
        drawEntities();
        drawScore();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    private void drawScore() {
        scoreText = "score: " + GameModel.getInstance().getMyPlayer().getScore();
        scoreBitmap.draw(game.getBatch(), scoreText, camera.position.x - 450, camera.position.y + 800);
    }


    //TODO: add comments inside the code
    private void updateCameraPosition(float delta) {
        float xVelocity_player = controller.getAirPlane1().getXVelocity();
        if (xVelocity_player == 0) {
            camera.position.set(camera.position.x, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            return;
        }

        if (GameModel.getInstance().getMyPlayer().getMyShip().getX() / PIXEL_TO_METER < camera.position.x - camera.viewportWidth / 4f) {
            if (xVelocity_player < 0) {
                camera.position.set(camera.position.x + xVelocity_player / PIXEL_TO_METER * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            } else {
                camera.position.set(camera.position.x, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            }
        } else if (GameModel.getInstance().getMyPlayer().getMyShip().getX() / PIXEL_TO_METER < camera.position.x + camera.viewportWidth / 4f) {
            if (xVelocity_player < 0) {
                camera.position.set(camera.position.x - CAMERA_X_SPEED * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            } else {
                camera.position.set(camera.position.x + CAMERA_X_SPEED * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            }
        } else {
            if (xVelocity_player > 0) {
                camera.position.set(camera.position.x + xVelocity_player / PIXEL_TO_METER * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            } else {
                camera.position.set(camera.position.x, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            }
        }
    }

    /**
     * Verify and correct if necessary the position of camera in relation of X values.
     *
     * @param delta time since last renders in seconds.
     */
    private void verifyXCameraBounds(float delta){
        float camera_Upper_Bound_X = ARENA_WIDTH / PIXEL_TO_METER - camera.viewportWidth / 2f;
        float camera_Lower_Bound_X = camera.viewportWidth / 2f;

        if(camera_Upper_Bound_X < camera.position.x){
            camera.position.set(camera_Upper_Bound_X, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }
        else if(camera_Lower_Bound_X > camera.position.x){
            camera.position.set(camera_Lower_Bound_X, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }
    }

    /**
     * Verify and correct if necessary the position of camera in relation of Y values.
     *
     * @param delta time since last renders in seconds.
     */
    private  void verifyYCameraBounds(float delta){
        float camera_Upper_Bound_Y = ARENA_HEIGHT / PIXEL_TO_METER - camera.viewportHeight / 2f;
        float camera_Lower_Bound_Y = camera.viewportHeight / 2f;

        if(camera_Upper_Bound_Y < camera.position.y){
            camera.position.set(camera.position.x, camera_Upper_Bound_Y, 0);
            //This is to implement on system of levels, when arrive the final of map they
            //fight to a boss to pass level.
            levelManager.setFinnishOfLevel(true);
        }
        else if(camera_Lower_Bound_Y > camera.position.y){
            camera.position.set(camera.position.x, camera_Lower_Bound_Y, 0);
        }
    }


    /**
     * Verify and correct if necessary the position of camera
     *
     * @param delta time since last renders in seconds.
     */
    private void verifyCameraBounds(float delta) {
        verifyXCameraBounds(delta);

        verifyYCameraBounds(delta);
    }

    /**
     * Attribute to calibrate the y axel of accelerometer.
     */
    private Float acceY_initial;

    /**
     * Variable to change the sensibility of y axel of accelerometer.
     */
    private float sensibility_Y = 2;

    /**
     * Functions to get the right value of velocity on Y axel.
     * First correct the value of accelerometer by our calibration
     * Last correct the value of velocity by multiplying accelerometer value with sensibility
     *
     * @param accelerometer_Y direct value of accelerometer getted
     * @return the correct value of velocity
     */
    private float getVelocity_Y(float accelerometer_Y){

        if(acceY_initial == null){
            acceY_initial = accelerometer_Y;
            accelerometer_Y = 0f;
        } else
            //Correct value of accelerometer
            accelerometer_Y = accelerometer_Y - acceY_initial;

        if(-accelerometer_Y>=0) {
            return  -accelerometer_Y * sensibility_Y + 2 * CAMERA_Y_SPEED * PIXEL_TO_METER;
        }
        else {
            return  -(accelerometer_Y * sensibility_Y + CAMERA_Y_SPEED * PIXEL_TO_METER);
        }
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        //Gdx.app.log("Accelerometer", "Handling Inputs");
        if (accelerometerAvail){
            Float acceX = Gdx.input.getAccelerometerX();
            if (Math.abs(acceX) <= 0.15){
                acceX = 0f;
                //TODO: change something where to set acceleration to view
            }
            Float acceY = Gdx.input.getAccelerometerY();
            float velY = getVelocity_Y(acceY);
            float velX_Correction = -5.5f;
            controller.getAirPlane1().setVelocity(acceX * velX_Correction , velY);
        }
    }

    /**
     * Draws the entities to the screen.
     */
    private void drawEntities() {
        synchronized(GameModel.getInstance().getEntityModels()) {
            Iterator<EntityModel> iterator = GameModel.getInstance().getEntityModels().iterator();
            while (iterator.hasNext()){
                EntityModel model = iterator.next();
                EntityView view = ViewFactory.getInstance().makeView(game, model);
                view.update(model);
                view.draw(game.getBatch());
            }
        }
    }

    /**
     * Draws the background of scene
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));
    }

}