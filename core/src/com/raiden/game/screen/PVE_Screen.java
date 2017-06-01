package com.raiden.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.raiden.game.Arena;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.entities.EntityView;
import com.raiden.game.screen.entities.ViewFactory;

import static com.raiden.game.Arena.isMultiplayer;
import static com.raiden.game.physics_controller.Physics_Controller.ARENA_HEIGHT;
import static com.raiden.game.physics_controller.Physics_Controller.ARENA_WIDTH;

/**
 * A view representing the game screen. Draws all the other views and
 * controls the camera.
 */
public class PVE_Screen extends ScreenAdapter {

    private String scoreText;
    BitmapFont scoreBitmap;

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

    public static final int CAMERA_Y_SPEED = 50;
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

    private Float acceY_initial;

    private LevelManager levelManager;

    /**
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     * @param controller The physics controller
     */
    private PVE_Screen(Arena game, Physics_Controller controller) {
        this.game = game;
        this.controller = controller;
        loadAssets();
        camera = createCamera();
        controller.setCamera(camera);
        levelManager = new LevelManager(this);
        instance = this;
        scoreText = "score: 0";
        scoreBitmap = new BitmapFont();
        scoreBitmap.getData().setScale(5);
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

        camera.position.set(GameModel.getInstance().getMyPlayer().getX()/PIXEL_TO_METER,
                GameModel.getInstance().getMyPlayer().getY()/PIXEL_TO_METER, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    private void loadOneAsset(String asset){
        this.game.getAssetManager().load( asset , Texture.class);
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        loadOneAsset( "spaceship-no-thrust.png");
        loadOneAsset( "spaceship-thrust.png");
        loadOneAsset( "AirPlane_1.png");
        loadOneAsset( "AirPlane_2.png");
        loadOneAsset( "AirPlane_3.png");
        loadOneAsset( "Tank.png");
        loadOneAsset( "Bullet.png");
        loadOneAsset( "background.png");
        loadOneAsset( "commet.png");

        this.game.getAssetManager().finishLoading();
    }

    private void updateScene(float delta){
        if(!LevelManager.isEndOfGame())
            handleInputs(delta);
        if (Arena.isHost() && isMultiplayer() || !isMultiplayer()){
            controller.update(delta);
            game.getBroadcast().sendMessage_from_Host(GameModel.getInstance());
        }else if (isMultiplayer()){
            game.getBroadcast().sendMessage_from_Client(GameModel.getInstance().getMyPlayer());
        }
        updateCameraPosition(delta);
        verifyCameraBounds(delta);
        camera.update();
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        if (Arena.isHost() && isMultiplayer() || !isMultiplayer()){
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
        scoreBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreBitmap.draw(game.getBatch(), scoreText, camera.position.x - 450, camera.position.y + 800);
    }


    //TODO: add comments inside the code
    private void updateCameraPosition(float delta) {
        float xVelocity_player = controller.getAirPlane1().getXVelocity();
        if (xVelocity_player == 0) {
            camera.position.set(camera.position.x, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            return;
        }

        if (GameModel.getInstance().getMyPlayer().getX() / PIXEL_TO_METER < camera.position.x - camera.viewportWidth / 4f) {
            if (xVelocity_player < 0) {
                camera.position.set(camera.position.x + xVelocity_player / PIXEL_TO_METER * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            } else {
                camera.position.set(camera.position.x, camera.position.y + CAMERA_Y_SPEED * delta, 0);
            }
        } else if (GameModel.getInstance().getMyPlayer().getX() / PIXEL_TO_METER < camera.position.x + camera.viewportWidth / 4f) {
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


    //TODO: add comments inside the code
    private void verifyCameraBounds(float delta) {
        float camera_Upper_Bound_X = ARENA_WIDTH / PIXEL_TO_METER - camera.viewportWidth / 2f;
        float camera_Lower_Bound_X = camera.viewportWidth / 2f;
        if(camera_Upper_Bound_X < camera.position.x){
            camera.position.set(camera_Upper_Bound_X, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }
        else if(camera_Lower_Bound_X > camera.position.x){
            camera.position.set(camera_Lower_Bound_X, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }

        float camera_Upper_Bound_Y = ARENA_HEIGHT / PIXEL_TO_METER- camera.viewportHeight / 2f;
        float camera_Lower_Bound_Y = camera.viewportHeight / 2f;
        if(camera_Upper_Bound_Y < camera.position.y){
            camera.position.set(camera.position.x, camera_Upper_Bound_Y, 0);
            levelManager.setFinnishOfLevel(true);
        }
        else if(camera_Lower_Bound_Y > camera.position.y){
            camera.position.set(camera.position.x, camera_Lower_Bound_Y, 0);
        }
    }

    private float getVelocity_X(float acceY){
        float acceY_correction = 2;
        if(acceY_initial == null){
            acceY_initial = acceY;
            acceY = 0f;
        } else
            acceY = acceY - acceY_initial;

        if(-acceY>=0) {
            return  -acceY * acceY_correction + 2 * CAMERA_Y_SPEED * PIXEL_TO_METER;
        }
        else {
            return  -(acceY * acceY_correction + CAMERA_Y_SPEED * PIXEL_TO_METER);
        }
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        //Gdx.app.log("Accelerometer", "Handling Inputs");
        boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (accelerometerAvail){
            Float acceX = Gdx.input.getAccelerometerX();
            if (Math.abs(acceX) <= 0.15){
                acceX = 0f;
                //TODO: change something where to set acceleration to view
            }
            Float acceY = Gdx.input.getAccelerometerY();
            float velY = getVelocity_X(acceY);
            float velX_Correction = -5.5f;
            controller.getAirPlane1().setVelocity(acceX * velX_Correction , velY);
        }
    }

    /**
     * Draws the entities to the screen.
     */
    private void drawEntities() {

        for (EntityModel modelEntity : GameModel.getInstance().getEntityModels()) {
            EntityView view = ViewFactory.getInstance().makeView(game, modelEntity);
            view.update(modelEntity);
            view.draw(game.getBatch());
        }
    }

    /**
     * Draws the background
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("background.png", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(ARENA_WIDTH / PIXEL_TO_METER), (int) (ARENA_HEIGHT / PIXEL_TO_METER));
    }


    public Physics_Controller getController() {
        return controller;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public static PVE_Screen getInstance(){
        if(instance == null){
            instance = new PVE_Screen(Arena.getInstance(),Physics_Controller.getInstance());
        }
        return instance;
    }

}