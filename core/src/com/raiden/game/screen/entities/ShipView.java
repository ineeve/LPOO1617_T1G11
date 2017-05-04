package com.raiden.game.screen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;

/**
 * A view representing a space ship
 */
public class ShipView extends EntityView {
    /**
     * The time between the animation frames
     */
    private static final float FRAME_TIME = 0.05f;

    /**
     * The number of animations
     */
    private int numberOfAnimations;

    /**
     * The texture used when the ship is accelerating
     */
    private Texture animatedTexture;

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

    /**
     * Constructs a space ship model.
     *
     * @param notAccelerated
     *
     * @param accelerated
     *
     * @param animationNumbers
     */
    public ShipView(Texture notAccelerated, Texture accelerated, int animationNumbers) {
        super(notAccelerated);
        animatedTexture = accelerated;
        this.numberOfAnimations = animationNumbers;
        sprite = createSprite();
    }

    /**
     * Creates a sprite representing this space ship.
     *
     * @return the sprite representing this space ship
     */
    @Override
    public Sprite createSprite() {
        notAcceleratingRegion = createNotAcceleratingRegion(texture);
        acceleratingAnimation = createAcceleratingAnimation(animatedTexture, numberOfAnimations);
        return new Sprite(notAcceleratingRegion);
    }

    /**
     * Creates the texture used when the ship is not accelerating
     *
     * @param noThrustTexture
     *
     * @return the texture used when the ship is not accelerating
     */
    private TextureRegion createNotAcceleratingRegion(Texture noThrustTexture) {
        return new TextureRegion(noThrustTexture, noThrustTexture.getWidth(), noThrustTexture.getHeight());
    }

    /**
     * Creates the animation used when the ship is accelerating
     *
     * @param thrustTexture
     *
     * @param numberOfAnimations
     *
     * @return the animation used when the ship is accelerating
     */
    private Animation<TextureRegion> createAcceleratingAnimation(Texture thrustTexture, int numberOfAnimations) {
        TextureRegion[][] thrustRegion = TextureRegion.split(thrustTexture, thrustTexture.getWidth() / numberOfAnimations, thrustTexture.getHeight());

        TextureRegion[] frames = new TextureRegion[4];
        System.arraycopy(thrustRegion[0], 0, frames, 0, 4);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }

    /**
     * Updates this ship model. Also save and resets
     * the accelerating flag from the model.
     *
     * @param model the model used to update this view
     */
    @Override
    public void update(EntityModel model) {
        super.update(model);

        accelerating = ((MovingObjectModel)model).isAccelerating();
        ((MovingObjectModel)model).setAccelerating(false);
    }

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
}