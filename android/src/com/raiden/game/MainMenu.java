package com.raiden.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by João on 28/04/2017.
 */

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    public static GoogleApiClient mGoogleApiClient;

    private static final Class[] CLASSES = new Class[]{
            LoginMenu.class,
            PlayLauncher.class,
            //Settings.class,
            //HangarMenu.class
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
        findViewById(R.id.settings_button).setOnClickListener(this);
        findViewById(R.id.play_button).setOnClickListener(this);
        findViewById(R.id.hangar_button).setOnClickListener(this);
        findViewById(R.id.exit_button).setOnClickListener(this);
        findViewById(R.id.pve_singleplayer_button).setOnClickListener(this);
        findViewById(R.id.pve_multiplayer_button).setOnClickListener(this);
        findViewById(R.id.pvp_multiplayer_button).setOnClickListener(this);
        findViewById(R.id.quickstart_button).setOnClickListener(this);
        findViewById(R.id.invite_to_play_button).setOnClickListener(this);
        findViewById(R.id.view_invitation_button).setOnClickListener(this);
        findViewById(R.id.back_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                startActivity(new Intent(this, CLASSES[0]));
                break;
            case R.id.settings_button:
                break;
            case R.id.play_button:
                findViewById(R.id.main_menu_buttons).setVisibility(view.GONE);
                findViewById(R.id.play_menu_buttons).setVisibility(view.VISIBLE);
                findViewById(R.id.back_button).setVisibility(view.VISIBLE);
                break;
            case R.id.hangar_button:
                break;
            case R.id.exit_button:
                this.finish();
                System.exit(0);
                break;
            case R.id.pve_singleplayer_button:
                if (!isConnected()) {
                    GameUtils.makeSimpleDialog(this, getString(R.string.please_sign_in)).show();
                    return;
                }
                startActivity(new Intent(this, CLASSES[1]));
                break;
            case R.id.pve_multiplayer_button:
                findViewById(R.id.play_menu_buttons).setVisibility(view.GONE);
                findViewById(R.id.multiplayer_menu_buttons).setVisibility(view.VISIBLE);
                //TODO: define variabel to know is a multiplayer game
                break;
            case R.id.pvp_multiplayer_button:
                findViewById(R.id.play_menu_buttons).setVisibility(view.GONE);
                findViewById(R.id.multiplayer_menu_buttons).setVisibility(view.VISIBLE);
                //TODO: define variabel to know is a multiplayer game
                break;
            case R.id.back_button:
                if(findViewById(R.id.play_menu_buttons).getVisibility() == view.VISIBLE) {
                    findViewById(R.id.play_menu_buttons).setVisibility(view.GONE);
                    findViewById(R.id.back_button).setVisibility(view.GONE);
                    findViewById(R.id.main_menu_buttons).setVisibility(view.VISIBLE);
                }
                else if(findViewById(R.id.multiplayer_menu_buttons).getVisibility() == view.VISIBLE) {
                    findViewById(R.id.multiplayer_menu_buttons).setVisibility(view.GONE);
                    findViewById(R.id.play_menu_buttons).setVisibility(view.VISIBLE);
                }
        }
    }

    private boolean isConnected() {
        return mGoogleApiClient != null;
    }

}
