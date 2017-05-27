package com.raiden.game.screen.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.raiden.game.Arena;

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
