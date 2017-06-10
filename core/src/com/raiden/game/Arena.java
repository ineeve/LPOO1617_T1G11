package com.raiden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.raiden.game.model.GameModel;
import com.raiden.game.screen.EnemiesFactory;
import com.raiden.game.screen.PVE_Screen;
import com.raiden.game.screen.entities.ViewFactory;

import java.nio.IntBuffer;

/**
 * The game main class.
 */
public class Arena extends Game {
	//The SpriteBatch used to improve render performance.
	private SpriteBatch batch;
    //The asset manager responsible for managing all the assets.
	private AssetManager assetManager;
    //An Arena instance.
	private static Arena instance;
    //Flag to define this is a multiplayer game.
	private boolean multiplayer = false;
    //Flag to define if this instance will be the host or the client.
	private boolean host = false;
    //The player ID used by this instance of the game.
	private String mPlayerID = "I_AM_THE_REAL_MVP";
    //A Broadcast Interface used to communicate.
	private Broadcast broadcast;
    //String used to save the type (size) of background being loaded.
	private String background;
    //Flag used to check if the game input should be done by accelerometer.
	private boolean useAccelerometer = true;

    /**
     * Defines the broadcast
     * @param broadcast A class that implements the broadcast.
     */
	public void setBroadcast(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

	/**
	 * Creates the game. Initializes the sprite batch and asset manager.
	 * Also starts the game until we have a main menu.
	 */
	@Override
	public void create() {
		instance = this;
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();
		startGame();
	}

	/**
	 * Sets accelerometer flag
	 * @param useAccelerometer Set true to use the accelerometer, false to use the touch as input
	 */
	public void setUseAccelerometer(boolean useAccelerometer) {
		this.useAccelerometer = useAccelerometer;
	}

	/**
	 * Gets accelerometer switch state.
	 * @return True if accelerometer switch is enabled, false otherwise
	 */
	public boolean isUseAccelerometer() {
		return useAccelerometer;
	}

	/**
	 * Starts the game.
	 */
	private void startGame() {
		setScreen(PVE_Screen.getInstance());
	}

	/**
	 * Disposes of all assets. Submits score and leaves room;
	 */
	@Override
	public void dispose() {
		broadcast.leaveRoom();
		batch.dispose();
		ViewFactory.getInstance().dispose();
		EnemiesFactory.getInstance().dispose();
		PVE_Screen.getInstance().clean();
		multiplayer = false;
		assetManager.dispose();
	}

    /**
     * Load one Asset to the assetManager.
     * @param asset The name of the asset to be loaded.
     */
	private void loadOneAsset(String asset) {
		this.assetManager.load(asset, Texture.class);
	}

	/**
	 * Loads the assets needed by this screen. Images and Music.
	 */
	private void loadAssets() {
		loadOneAsset("spaceship-no-thrust.png");
		loadOneAsset("spaceship-thrust.png");
		loadOneAsset("AirPlane_1.png");
		loadOneAsset("AirPlane_2.png");
		loadOneAsset("AirPlane_3.png");
		loadOneAsset("Tank.png");
		loadOneAsset("Bullet.png");
		IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
		Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);
		int maxSize = intBuffer.get();
		if (maxSize > 5900) {
			loadOneAsset("background_xxxdpi.png");
			background = "background_xxxdpi.png";
		} else if (maxSize >= 4096) {
			loadOneAsset("background_xxdpi.png");
			background = "background_xxdpi.png";
		} else if (maxSize >= 3500) {
			loadOneAsset("background_xdpi.png");
			background = "background_xdpi.png";
		} else if (maxSize >= 2048) {
			loadOneAsset("background_hdpi.png");
			background = "background_hdpi.png";
		} else if (maxSize >= 1024) {
			loadOneAsset("background_mdpi.png");
			background = "background_mdpi.png";
		} else if (maxSize >= 512) {
			loadOneAsset("background_sdpi.png");
			background = "background_sdpi.png";
		}
		loadOneAsset("commet.png");
		this.assetManager.load("Oxia-Domino (Robag's Lasika Cafa Nb).mp3", Music.class);

		this.assetManager.finishLoading();
	}

	/**
	 * Returns the asset manager used to load all textures and sounds.
	 *
	 * @return the asset manager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Returns the sprite batch used to improve drawing performance.
	 *
	 * @return the sprite batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

    /**
     *
     * @return The object responsible for the broadcast.
     */
	public Broadcast getBroadcast() {
		return broadcast;
	}

    /**
     *
     * @return The instance of the arena, creating a new one if none exists.
     */
	public static Arena getInstance() {
		if (instance == null) {
			instance = new Arena();
		}
		return instance;
	}

    /**
     * @return True if this game is multiplayer, false otherwise.
     */
	public boolean isMultiplayer() {
		return multiplayer;
	}

    /**
     * @param multiplayer True if this game should be multiplayer, false otherwise.
     */
	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

    /**
     * @return True if this device is the host, false otherwise.
     */
	public boolean isHost() {
		return host;
	}

    /**
     * Defines this device as the host or as the client.
     * @param host True if this device is the host, false if is a client.
     */
	public void setHost(boolean host) {
		this.host = host;
	}

    /**
     * @return The ID of the player playing in this device.
     */
	public String getmPlayerID() {
		return mPlayerID;
	}

    /**
     * @param mPlayerID The ID of the player playing in this device.
     */
	public void setmPlayerID(String mPlayerID) {
		this.mPlayerID = mPlayerID;
	}

    /**
     * @return A string containing the name of the background that shall be used.
     */
	public String getBackground() {
		return background;
	}

    /**
     * Used the broadcast object to submit the score via GoogleApi.
     * @param score The score to submit.
     */
	public void submitScore(long score) {
		broadcast.submitScore(score);
	}
}