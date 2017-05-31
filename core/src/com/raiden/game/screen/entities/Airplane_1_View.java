package com.raiden.game.screen.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raiden.game.Arena;

/**
 * Created by João on 24/05/2017.
 */

public class Airplane_1_View extends ShipView {
    /**
     * The time between the animation frames
     */
    private static final float FRAME_TIME = 0.05f;


    private final int numberOfAnimations = 4;


    /**
     * Constructs a space ship model.
     *
     * @param arena
     */
    public Airplane_1_View(Arena arena) {
        super(arena);
    }

    @Override
    protected TextureRegion createNotAcceleratingRegion(Arena arena) {
        Texture notAnimated = getAsset(arena, "AirPlane_1.png");
        return new TextureRegion(notAnimated, notAnimated.getWidth(), notAnimated.getHeight());
    }

    @Override
    protected Animation<TextureRegion> createAcceleratingAnimation(Arena arena) {
        //TODO: change this animated image
        Texture animated = getAsset(arena, "spaceship-thrust.png");

        TextureRegion[][] thrustRegion = TextureRegion.split(animated, animated.getWidth() / numberOfAnimations, animated.getHeight());

        TextureRegion[] frames = new TextureRegion[numberOfAnimations];
        System.arraycopy(thrustRegion[0], 0, frames, 0, numberOfAnimations);

        return new Animation<TextureRegion>(FRAME_TIME, frames);
    }
}
