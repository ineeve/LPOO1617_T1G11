package com.raiden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raiden.game.model.GameModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.EnemiesFactory;
import com.raiden.game.screen.LevelManager;
import com.raiden.game.screen.PVE_Screen;
import com.raiden.game.screen.entities.ViewFactory;

/**
 * The game main class.
 */
public class Arena extends Game{
	private SpriteBatch batch;
	private AssetManager assetManager;

    private static Arena instance;

	private boolean multiplayer = false;

    private boolean host = false;

    private String mPlayerID = "I_AM_THE_REAL_MVP";

	private Broadcast broadcast;


    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

	/**
	 * Creates the game. Initializes the sprite batch and asset manager.
	 * Also starts the game until we have a main menu.
	 */
	@Override
	public void create () {
		instance = this;
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();
		startGame();
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
	public void dispose () {
		broadcast.submitScore(GameModel.getInstance().getMyPlayer().getScore());
		batch.dispose();
		ViewFactory.getInstance().dispose();
		EnemiesFactory.getInstance().dispose();
		PVE_Screen.clearInstance();
		Physics_Controller.clearInstance();
		GameModel.clearInstance();
		LevelManager.setEndOfGame(false);
		multiplayer = false;
		assetManager.dispose();
		broadcast.leaveRoom();

	}


	private void loadOneAsset(String asset){
		this.assetManager.load( asset , Texture.class);
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

	public Broadcast getBroadcast() {
		return broadcast;
	}

	public static Arena getInstance(){
		if(instance == null){
			instance = new Arena();
		}
		return instance;
	}

	public boolean isMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public String getmPlayerID() {
        return mPlayerID;
    }

    public void setmPlayerID(String mPlayerID) {
        this.mPlayerID = mPlayerID;
    }
}