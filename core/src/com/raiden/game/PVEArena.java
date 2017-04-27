package com.raiden.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raiden.game.model.GameModel;
import com.raiden.game.physics_controller.Physics_Controller;

/**
 * The game main class.
 */
public class PVEArena extends Game {
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
		GameModel model = new GameModel(Physics_Controller.ARENA_WIDTH /2, Physics_Controller.ARENA_HEIGHT / 2);

		//setScreen(new SinglePlayerScreen(this, model, new Physics_Controller(model)));
	}

	/**
	 * Disposes of all assets.
	 */
	@Override
	public void dispose () {
		batch.dispose();
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