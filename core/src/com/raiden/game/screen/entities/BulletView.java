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
        Texture bullet = getAsset(arena, "Bullet.png");
        return new Sprite(bullet,bullet.getWidth(),bullet.getHeight());
    }


}
