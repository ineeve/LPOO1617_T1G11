package com.raiden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raiden.game.screen.EnemiesFactory;
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
		startGame();
	}

	/**
	 * Starts the game.
	 */
	private void startGame() {
		setScreen(PVE_Screen.getInstance());
	}

	/**
	 * Disposes of all assets.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		ViewFactory.getInstance().dispose();
		EnemiesFactory.getInstance().dispose();
		assetManager.dispose();
		broadcast.leaveRoom();
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