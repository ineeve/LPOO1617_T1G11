package com.raiden.game.screen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raiden.game.model.entities.EntityModel;

/**
 * Created by ineeve on 23-05-2017.
 */

public class BulletView extends EntityView {

    /**
     * Creates a view belonging to a game.
     *
     * @param texture to after create the sprite of object
     */


    private TextureRegion bulletRegion;

    BulletView(Texture texture) {
        super(texture);
        sprite = createSprite();
    }

    @Override
    public Sprite createSprite() {
        bulletRegion = new TextureRegion(texture,texture.getWidth(),texture.getHeight());
        return new Sprite(bulletRegion);
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
