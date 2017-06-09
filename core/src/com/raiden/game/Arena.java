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
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.EnemiesFactory;
import com.raiden.game.screen.LevelManager;
import com.raiden.game.screen.PVE_Screen;
import com.raiden.game.screen.entities.ViewFactory;

import java.nio.IntBuffer;

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

	private String background;


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
		broadcast.leaveRoom();
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
		IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
		Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);
		int maxSize = intBuffer.get();
		Gdx.app.log("MaxGraphicsBuf_SIXE", String.valueOf(intBuffer.get()));
		if (maxSize > 5900){
			loadOneAsset( "background_xxxdpi.png");
			background = "background_xxxdpi.png";
		}else if(maxSize >= 4096){
			loadOneAsset( "background_xxdpi.png");
			background = "background_xxdpi.png";
		}else if(maxSize >= 3500){
			loadOneAsset( "background_xdpi.png");
			background = "background_xdpi.png";
		}else if(maxSize >= 2048){
			loadOneAsset( "background_hdpi.png");
			background = "background_hdpi.png";
		}
		else if(maxSize >= 1024){
			loadOneAsset( "background_mdpi.png");
			background = "background_mdpi.png";
		}
		else if(maxSize >= 512){
			loadOneAsset( "background_sdpi.png");
			background = "background_sdpi.png";
		}
		loadOneAsset( "commet.png");
        this.assetManager.load( "Oxia-Domino (Robag's Lasika Cafa Nb).mp3", Music.class);

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

	public String getBackground() {
		return background;
	}

	public void submitScore(long score){
		broadcast.submitScore(score);
	}
}