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
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.entities.ShipView;

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
    private static final boolean DEBUG_PHYSICS = false;

    /**
     * How much meters does a pixel represent.
     */
    public final static float PIXEL_TO_METER = 0.02f;

    /**
     * The width of the viewport in meters. The height is
     * automatically calculated using the screen ratio.
     */
    private static final float VIEWPORT_WIDTH = 20;

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

    /**
     * A ship view used to draw ships.
     */
    private final ShipView airPlane_1;

    /**
     * A renderer used to debug the physical fixtures.
     */
    private Box2DDebugRenderer debugRenderer;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    private Float initialPitch;

    private Float initialYaw;
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
        this.initialPitch = -1024f;
        this.initialYaw = -1024f;
        loadAssets();
        Texture notAnimated, animated;
        notAnimated = game.getAssetManager().get("AirPlane_1.png");
        animated = game.getAssetManager().get("spaceship-thrust.png");
        airPlane_1 = new ShipView(notAnimated, animated, 4);

        camera = createCamera();
        controller.setCamera(camera);
    }

    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, model.getPlayer1().getY()/PIXEL_TO_METER, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets() {
        this.game.getAssetManager().load( "spaceship-no-thrust.png" , Texture.class);
        this.game.getAssetManager().load( "spaceship-thrust.png" , Texture.class);
        this.game.getAssetManager().load( "AirPlane_1.png" , Texture.class);
        this.game.getAssetManager().load( "AirPlane_2.png" , Texture.class);
        this.game.getAssetManager().load( "AirPlane_3.png" , Texture.class);
        this.game.getAssetManager().load( "Tank.png" , Texture.class);

        this.game.getAssetManager().load( "background.png" , Texture.class);

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

        camera.position.set(model.getPlayer1().getX() / PIXEL_TO_METER, camera.position.y + 20*delta, 0);
        Gdx.app.log("Camera Position", String.valueOf(camera.position));
        Gdx.app.log("Player Position", "X=" + String.valueOf(model.getPlayer1().getX()) + " Y=" + String.valueOf(model.getPlayer1().getY()));
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

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {

        boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (accelerometerAvail){
            Float acceX = Gdx.input.getAccelerometerX();
            if (Math.abs(acceX) <= 0.2){
                acceX = 0f;
                airPlane_1.setAccelerating(false);
            }
            else{
                airPlane_1.setAccelerating(true);
            }
            controller.setVelocityofPlayer1(acceX, 0);

        }
       /* boolean compassAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);
        Gdx.app.log("Compass","Available =" + compassAvailable);
        if (compassAvailable){
            Integer pitch = (int)Gdx.input.getPitch();
            Integer azimuth = (int)Gdx.input.getAzimuth();
            Integer roll = (int)Gdx.input.getRoll();
            Gdx.app.log("Compass","Current Azimuth " + azimuth.toString());
            Gdx.app.log("Compass","Current pitch " + pitch.toString());
            Gdx.app.log("Compass","Current roll " + roll.toString());
        }*/

    }

    /**
     * Draws the entities to the screen.
     */
    private void drawEntities() {

        airPlane_1.update(model.getPlayer1());
        airPlane_1.draw(game.getBatch());
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