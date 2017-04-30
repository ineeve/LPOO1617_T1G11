package com.raiden.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    DrawerLayout drawerLayout;

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
        setContentView(R.layout.activity_main);

        //LADO ESQUERDO

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*//LADO DIREITO
        mDrawerLayoutRight = (DrawerLayout) findViewById(R.id.settings_drawer_layout);
        ActionBarDrawerToggle toggleRight = new ActionBarDrawerToggle(
                this, mDrawerLayoutRight, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayoutRight.setDrawerListener(toggleRight);
        toggleRight.syncState();*/

        NavigationView navigationViewRight = (NavigationView) findViewById(R.id.nav_view_right);
        navigationViewRight.setNavigationItemSelectedListener(this);

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
                //startActivity(new Intent(this, CLASSES[0]));
                drawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.settings_button:
                drawerLayout.openDrawer(Gravity.END);
                break;
            case R.id.play_button:
                findViewById(R.id.main_menu_buttons).setVisibility(View.GONE);
                findViewById(R.id.play_menu_buttons).setVisibility(View.VISIBLE);
                findViewById(R.id.back_button).setVisibility(View.VISIBLE);
                break;
            case R.id.hangar_button:
                break;
            case R.id.exit_button:
                this.finish();
                System.exit(0);
                break;
            case R.id.pve_singleplayer_button:
                startActivity(new Intent(this, CLASSES[1]));
                break;
            case R.id.pve_multiplayer_button:
                findViewById(R.id.play_menu_buttons).setVisibility(View.GONE);
                findViewById(R.id.multiplayer_menu_buttons).setVisibility(View.VISIBLE);
                //TODO: define variabel to know is a multiplayer game
                break;
            case R.id.pvp_multiplayer_button:
                findViewById(R.id.play_menu_buttons).setVisibility(View.GONE);
                findViewById(R.id.multiplayer_menu_buttons).setVisibility(View.VISIBLE);
                //TODO: define variabel to know is a multiplayer game
                break;
            case R.id.back_button:
                if(findViewById(R.id.play_menu_buttons).getVisibility() == View.VISIBLE) {
                    findViewById(R.id.play_menu_buttons).setVisibility(View.GONE);
                    findViewById(R.id.back_button).setVisibility(View.GONE);
                    findViewById(R.id.main_menu_buttons).setVisibility(View.VISIBLE);
                }
                else if(findViewById(R.id.multiplayer_menu_buttons).getVisibility() == View.VISIBLE) {
                    findViewById(R.id.multiplayer_menu_buttons).setVisibility(View.GONE);
                    findViewById(R.id.play_menu_buttons).setVisibility(View.VISIBLE);
                }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation View item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
