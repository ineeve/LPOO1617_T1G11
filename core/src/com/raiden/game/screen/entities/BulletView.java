package com.raiden.game.screen.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raiden.game.Arena;
import com.raiden.game.model.entities.EntityModel;

/**
 * Created by ineeve on 23-05-2017.
 */

public class BulletView extends EntityView {

    BulletView(Arena arena) {
        super(arena);
    }

    @Override
    public Sprite createSprite(Arena arena) {
        Texture bullet = arena.getAssetManager().get("Bullet.png");
        TextureRegion notAnimated = new TextureRegion(bullet, bullet.getWidth(), bullet.getHeight());
        return new Sprite(notAnimated);
    }

    /**
     * Updates this bullet model
     *
     * @param model the model used to update this view
     */
    @Override
    public void update(EntityModel model) {
        super.update(model);
    }


    /**
     * Draws the sprite from this view using a sprite batch.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
