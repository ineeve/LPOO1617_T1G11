package com.raiden.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by João on 28/04/2017.
 */

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    public static GoogleApiClient mGoogleApiClient;

    private static final Class[] CLASSES = new Class[]{
            LoginMenu.class,
            PlayLauncher.class
    };

    private static final int[] DESCRIPTION_IDS = new int[] {
            R.string.desc_sign_in_activity,
            R.string.desc_play_launcher,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Button listeners
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.play_button).setOnClickListener(this);
        findViewById(R.id.exit_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                startActivity(new Intent(this, CLASSES[0]));
                break;
            case R.id.play_button:
                if (!isConnected()) {
                    GameUtils.makeSimpleDialog(this, getString(R.string.please_sign_in)).show();
                    return;
                }
                startActivity(new Intent(this, CLASSES[1]));
                break;
            case R.id.exit_button:
                Gdx.app.exit();
                System.exit(0);
                break;
        }
    }

    private boolean isConnected() {
        return mGoogleApiClient != null;
    }

}
