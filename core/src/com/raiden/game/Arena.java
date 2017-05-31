package com.raiden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.PVE_GameModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.EnemiesFactory;
import com.raiden.game.screen.PVE_Screen;
import com.raiden.game.screen.entities.ViewFactory;

/**
 * The game main class.
 */
public class Arena extends Game{
	private SpriteBatch batch;
	private AssetManager assetManager;

	/**
	 * Creates the game. Initializes the sprite batch and asset manager.
	 * Also starts the game until we have a main menu.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		startGame();
	}

	/**
	 * Starts the game.
	 */
	private void startGame() {
		GameModel model = PVE_GameModel.getInstanceOf();

		setScreen(new PVE_Screen(this, model,Physics_Controller.getInstance(model)));
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
}