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
		ViewFactory.dispose();
		EnemiesFactory.dispose();
		assetManager.dispose();
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

	public static Arena getInstance(){
		if(instance == null){
			instance = new Arena();
		}
		return instance;
	}
}