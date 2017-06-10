package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.Gdx.app;

/**
 * A class that renders the final score in a pretty menu designed using libgdx.
 */
abstract class EndGameShowScore{
    private static final int NUM_BUTTONS = 3;
    private static PVE_Screen screen;
    private static Stage myStage = new Stage(new ScreenViewport());
    private static ImageButton [] buttons = new ImageButton[NUM_BUTTONS];
    private static TextureRegionDrawable [] buttonDrawables = new TextureRegionDrawable[NUM_BUTTONS];

    private static Label.LabelStyle labelStyle = null;
    private static Label label = null;
    private static boolean labelOnStage = false;



    private static ClickListener listener = new ClickListener()
    {
        @Override
        public void clicked(InputEvent event, float x, float y)
        {
            if (event.getListenerActor() == buttons[0]) {
                app.exit();
            } else if (event.getListenerActor() == buttons[1]) {
                screen.clean();
                PVE_Screen.getInstance();
            } else if (event.getListenerActor() == buttons[2]) {

            }
        }
    };

    static void initTextureRegionDrawables(){
        String []fileNames = new String[]{"back.png","tryAgain.png","continue.png"};
        Texture myTexture = null;
        TextureRegion myRegion = null;
        for (int i = 0; i < NUM_BUTTONS; i++){
            myTexture = new Texture(Gdx.files.internal(fileNames[i]));
            myRegion = new TextureRegion(myTexture);
            buttonDrawables[i] = new TextureRegionDrawable(myRegion);
        }
    }

    static void initImageButtons(){
        for (int i = 0; i < NUM_BUTTONS;i++){
            buttons[i] = new ImageButton(buttonDrawables[i]);
            myStage.addActor(buttons[i]);
            buttons[i].addListener(listener);
        }
        buttons[0].setPosition(Gdx.graphics.getWidth()/2 - buttons[0].getWidth()/2, Gdx.graphics.getHeight()/2 - 3*buttons[0].getHeight());
        buttons[1].setPosition(Gdx.graphics.getWidth()/2 - buttons[1].getWidth()/2, Gdx.graphics.getHeight()/2- buttons[1].getHeight());
        buttons[2].setPosition(Gdx.graphics.getWidth()/2 - buttons[2].getWidth()/2, Gdx.graphics.getHeight()/2 + buttons[2].getHeight());
    }

    static void initButtons(PVE_Screen pve_screen){
        screen = pve_screen;
        initTextureRegionDrawables();
        initImageButtons();
        Gdx.input.setInputProcessor(myStage);

    }

    static void setScore(int score){
        String scoreText = "Score: ";
        BitmapFont font = new BitmapFont(Gdx.files.internal("robotoFont.fnt"));
        String tempText = scoreText + score;
        if (labelStyle == null)
            labelStyle = new Label.LabelStyle(font,null);
        if (label == null)
            label = new Label(tempText,labelStyle);
        else
            label.setText(tempText);
            label.setPosition(Gdx.graphics.getWidth()/2 - label.getWidth()/2, Gdx.graphics.getHeight() - 3*label.getHeight());
        if (!labelOnStage){
            myStage.addActor(label);
            labelOnStage=true;
        }
    }

    static void render()
    {
        myStage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic

        myStage.draw(); //Draw the ui

    }
}
