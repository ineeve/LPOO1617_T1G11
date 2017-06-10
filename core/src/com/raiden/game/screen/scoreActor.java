package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by ineeve on 10-06-2017.
 */

public class scoreActor extends Actor {

    private BitmapFont font;
    private int score;

    public scoreActor(){
        font = new BitmapFont(Gdx.files.internal("robotoFont.fnt"));
    }

    public void setScore(int score){
        this.score = score;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

    }
}
