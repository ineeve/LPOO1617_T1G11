package com.raiden.game.screen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raiden.game.Arena;

/**
 * Created by ineeve on 31-05-2017.
 */

public class CometView extends EntityView {

    /**
     * The time between the animation frames
     */
    private final float FRAME_TIME = 0.05f;

    /**
     * The animation used for the commet.
     */
    private Animation<TextureRegion> animation;

    /**
     * Time since the commet started the game. Used
     * to calculate the frame to show in the animation.
     */
    private float stateTime = 0;

    private final int NUM_FRAMES = 11;

    /**
     * Creates a view belonging to a game.
     * @param arena
     */
    CometView(Arena arena) {
        super(arena);
    }

    @Override
    public Sprite createSprite(Arena arena) {
        Texture animated = getAsset(arena, "commet.png");

        TextureRegion[][] animatedRegion = TextureRegion.split(animated, animated.getWidth() / 4, animated.getHeight()/3);

        TextureRegion[] frames = new TextureRegion[NUM_FRAMES];
        System.arraycopy(animatedRegion[0], 0, frames, 0, 3);
        System.arraycopy(animatedRegion[1], 0, frames, 3, 4);
        System.arraycopy(animatedRegion[2], 0, frames, 7, 4);

        animation = new Animation<TextureRegion>(FRAME_TIME, frames);
        return new Sprite(frames[0]);

    }

    /**
     * Draws the sprite from this view using a sprite batch.
     * Chooses the correct frame based on the delta time
     *
     * @param batch The sprite batch to be used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        sprite.setRegion(animation.getKeyFrame(stateTime, true));
        sprite.draw(batch);
    }
}
