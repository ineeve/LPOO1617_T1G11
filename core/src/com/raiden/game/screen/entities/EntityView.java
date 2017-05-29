package com.raiden.game.screen.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raiden.game.Arena;
import com.raiden.game.model.entities.EntityModel;

import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;

/**
 * A abstract view capable of holding a sprite with a certain
 * position and rotation.
 *
 * This view is able to update its data based on a entity model.
 */
public abstract class EntityView {
    /**
     * The sprite representing this entity view.
     */
    Sprite sprite;

    //TODO: complete
    /**
     * Creates a view belonging to a game.
     *
     * @param
     */
    EntityView(Arena arena) {
        this.sprite = createSprite(arena);
    }

    /**
     * Draws the sprite from this view using a sprite batch.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /**
     * Abstract method that creates the view sprite. Concrete
     * implementation should extend this method to create their
     * own sprites.
     *
     * @return the sprite representing this view.
     */
    public abstract Sprite createSprite(Arena arena);

    /**
     * Updates this view based on a certain model.
     *
     * @param model the model used to update this view
     */
    public void update(EntityModel model) {
        sprite.setSize(model.getWidth(), model.getHeight());
        sprite.setCenter(model.getX() / PIXEL_TO_METER, model.getY() / PIXEL_TO_METER);
    }

    protected Texture getAsset(Arena arena, String asset){
        return arena.getAssetManager().get(asset);
    }
}