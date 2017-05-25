package com.raiden.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.raiden.game.Arena;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.entities.EntityView;
import com.raiden.game.screen.entities.ViewFactory;

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

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.02f;

    private final float acceY_correction = 2;

    /**
     * The game this screen belongs to.
     */
    private final Arena game;

    /**
     * The model drawn by this screen.
     */
    private final GameModel model;

    /**
     * The physics controller for this game.
     */
    private final Physics_Controller controller;

    /**
     * The camera used to show the viewport.
     */
    private final OrthographicCamera camera;

    private final int CAMERA_Y_SPEED = 50;
    private final int CAMERA_X_SPEED = 200;

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
    /**
     * Creates this screen.
     *
     * @param game The game this screen belongs to
     * @param model The model to be drawn
     * @param controller The physics controller
     */
    public PVE_Screen(Arena game, GameModel model, Physics_Controller controller) {
        this.game = game;
        this.model = model;
        this.controller = controller;
        loadAssets();

        camera = createCamera();
        controller.setCamera(camera);
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

        camera.position.set(model.getPlayer1().getX()/PIXEL_TO_METER, model.getPlayer1().getY()/PIXEL_TO_METER, 0);
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

        this.game.getAssetManager().finishLoading();
    }

    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    @Override
    public void render(float delta) {
        handleInputs(delta);
        controller.update(delta);
        updateCameraPosition(delta);
        verifyCameraBounds(delta);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }


    //TODO: add comments inside the code
    private void updateCameraPosition(float delta) {
        if (model.getPlayer1().getX() / PIXEL_TO_METER > camera.position.x - camera.viewportWidth / 4f && controller.getXVelocityofPlayer1() < 0) {
            camera.position.set(camera.position.x - CAMERA_X_SPEED * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }
        else if (model.getPlayer1().getX() / PIXEL_TO_METER < camera.position.x - camera.viewportWidth / 4f && controller.getXVelocityofPlayer1() < 0) {
            camera.position.set(camera.position.x + controller.getXVelocityofPlayer1() /PIXEL_TO_METER * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }
        if (model.getPlayer1().getX() / PIXEL_TO_METER < camera.position.x + camera.viewportWidth / 4f && controller.getXVelocityofPlayer1() > 0) {
            camera.position.set(camera.position.x + CAMERA_X_SPEED * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }
        else if (model.getPlayer1().getX() / PIXEL_TO_METER > camera.position.x + camera.viewportWidth / 4f && controller.getXVelocityofPlayer1() > 0) {
            camera.position.set(camera.position.x + controller.getXVelocityofPlayer1() / PIXEL_TO_METER * delta, camera.position.y + CAMERA_Y_SPEED * delta, 0);
        }
        if(controller.getXVelocityofPlayer1() == 0){
            camera.position.set(camera.position.x, camera.position.y + CAMERA_Y_SPEED * delta, 0);
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
        }
        else if(camera_Lower_Bound_Y > camera.position.y){
            camera.position.set(camera.position.x, camera_Lower_Bound_Y, 0);
        }
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        Gdx.app.log("Accelerometer", "Handling Inputs");
        boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (accelerometerAvail){
            Float acceX = Gdx.input.getAccelerometerX();
            if (Math.abs(acceX) <= 0.25){
                acceX = 0f;
                //TODO: change something where to set acceleration to view
            }
            Float acceY = Gdx.input.getAccelerometerY();
            if(acceY_initial == null){
                acceY_initial = acceY;
                acceY = 0f;
            } else
                acceY = acceY - acceY_initial;

            float velY;
            if(-acceY>=0) {
                velY = -acceY * acceY_correction + CAMERA_Y_SPEED * PIXEL_TO_METER;
                controller.setVelocityofPlayer1(acceX, velY);
            }
            else if (-acceY<0) {
                velY = -(acceY * acceY_correction + CAMERA_Y_SPEED * PIXEL_TO_METER);
                controller.setVelocityofPlayer1(acceX, velY);
            }
        }
    }

    /**
     * Draws the entities to the screen.
     */
    private void drawEntities() {

        for (EntityModel modelEntity : model.getEntityModels()) {
            EntityView view = ViewFactory.makeView(game, modelEntity);
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
}