package com.raiden.game.screen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raiden.game.Arena;
import com.raiden.game.model.entities.EntityModel;

/**
 * A view representing a space ship
 */
public abstract class ShipView extends EntityView {

    /**
     * The animation used when the ship is accelerating
     */
    private Animation<TextureRegion> acceleratingAnimation;

    /**
     * The texture used when the ship is not accelerating
     */
    private TextureRegion notAcceleratingRegion;

    /**
     * Time since the space ship started the game. Used
     * to calculate the frame to show in animations.
     */
    private float stateTime = 0;

    /**
     * Is the space ship accelerating.
     */
    private boolean accelerating;


    //TODO: fix comment
    /**
     * Constructs a space ship model.
     */
    public ShipView(Arena arena) {
        super(arena);
    }

    /**
     * Creates a sprite representing this space ship.
     *
     * @return the sprite representing this space ship
     */
    @Override
    public Sprite createSprite(Arena arena) {
        notAcceleratingRegion = createNotAcceleratingRegion(arena);
        acceleratingAnimation = createAcceleratingAnimation(arena);
        return new Sprite(notAcceleratingRegion);
    }


    //TODO: fix comment
    /**
     * Creates the texture used when the ship is not accelerating
     *
     * @return the texture used when the ship is not accelerating
     */
    protected abstract TextureRegion createNotAcceleratingRegion(Arena arena);


    //TODO: fix comment
    /**
     * Creates the animation used when the ship is accelerating
     *
     * @return the animation used when the ship is accelerating
     */
    protected abstract Animation<TextureRegion> createAcceleratingAnimation(Arena arena);

    /**
     * Draws the sprite from this view using a sprite batch.
     * Chooses the correct texture or animation to be used
     * depending on the accelerating flag.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        if (accelerating)
            sprite.setRegion(acceleratingAnimation.getKeyFrame(stateTime, true));
        else
            sprite.setRegion(notAcceleratingRegion);

        sprite.draw(batch);
    }

    public void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
    }
}