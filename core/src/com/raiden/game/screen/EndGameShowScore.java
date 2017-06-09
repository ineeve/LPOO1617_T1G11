package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by João on 09/06/2017.
 */

abstract class EndGameShowScore{
    private static PVE_Screen screen;
    private static Stage myStage = new Stage(new ScreenViewport());
    private static ImageButton returnToMenuButton;
    private static ImageButton tryAgainButton;
    private static ImageButton continueButton;
    private static ClickListener listener = new ClickListener()
    {
        @Override
        public void clicked(InputEvent event, float x, float y)
        {
            Gdx.app.log("Listener Actor: ", String.valueOf(event.getListenerActor()));
            Gdx.app.log("Listener type: ", String.valueOf(event.getType()));
            Gdx.app.log("Listener keycode: ", String.valueOf(event.getKeyCode()));
            if (event.getListenerActor() == returnToMenuButton) {
                Gdx.app.exit();
            } else if (event.getListenerActor() == tryAgainButton) {
                screen.clean();
                PVE_Screen.getInstance();
            } else if (event.getListenerActor() == continueButton) {

            }
        }
    };

    static void initButtons(PVE_Screen pve_screen){
        screen = pve_screen;
        Texture myTexture = new Texture(Gdx.files.internal("returnButton.jpg"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        returnToMenuButton = new ImageButton(myTexRegionDrawable); //Set the button up
        tryAgainButton = new ImageButton(myTexRegionDrawable); //Set the button up
        continueButton = new ImageButton(myTexRegionDrawable); //Set the button
        returnToMenuButton.setPosition(Gdx.graphics.getWidth()/2 - returnToMenuButton.getWidth(), Gdx.graphics.getHeight()/2 - returnToMenuButton.getHeight());
        tryAgainButton.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - tryAgainButton.getHeight());
        continueButton.setPosition(Gdx.graphics.getWidth()/2 - continueButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        myStage.addActor(returnToMenuButton);
        myStage.addActor(tryAgainButton);
        myStage.addActor(continueButton);
        Gdx.input.setInputProcessor(myStage);

        returnToMenuButton.addListener(listener);
        tryAgainButton.addListener(listener);
        continueButton.addListener(listener);
    }

    static void render()
    {
        myStage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        myStage.draw(); //Draw the ui
    }
}
