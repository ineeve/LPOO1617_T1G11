package com.raiden.game;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;

import static android.R.drawable.sym_def_app_icon;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnInvitationReceivedListener, ImageManager.OnImageLoadedListener {

    DrawerLayout drawerLayout;

    private View mHView;

    private AdView mAdView;

    private PlayLauncher playLauncher = new PlayLauncher();

    private static final int[] CLICKABLES = {
            R.id.login_button, R.id.settings_button,
            R.id.play_button, R.id.hangar_button,
            R.id.exit_button, R.id.pve_singleplayer_button,
            R.id.pve_multiplayer_button, R.id.pvp_multiplayer_button,
            R.id.quickstart_button, R.id.invite_to_play_button,
            R.id.view_invitation_button, R.id.back_button
    };

    private static final Class[] CLASSES = new Class[]{
            null,
            PlayLauncher.class,
            //Settings.class,
            HangarMenu.class
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

        NavigationView navigationViewRight = (NavigationView) findViewById(R.id.nav_view_right);
        navigationViewRight.setNavigationItemSelectedListener(this);

        // set up a click listener for everything we care about
        for (int id : CLICKABLES) {
            findViewById(id).setOnClickListener(this);
        }

        mHView =  navigationView.getHeaderView(0);
        mHView.findViewById(R.id.sign_in_button).setOnClickListener(this);
        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) mHView.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        // [END customize_button]

        // Create the Google Api Client with access to Games
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        mImageManager = ImageManager.create(this);


        //AdMob
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
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
                startActivity(new Intent(this, CLASSES[2]));
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
                playLauncher.mMultiplayer = true;
                playLauncher.startQuickGame();
                break;
            case R.id.pvp_multiplayer_button:
                findViewById(R.id.play_menu_buttons).setVisibility(View.GONE);
                findViewById(R.id.multiplayer_menu_buttons).setVisibility(View.VISIBLE);
                playLauncher.mMultiplayer = true;
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
                break;
            case R.id.sign_in_button:
                // user wants to sign in
                // Check to see the developer who's running this sample code read the instructions :-)
                // NOTE: this check is here only because this is a sample! Don't include this
                // check in your actual production app.
                if (!BaseGameUtils.verifySampleSetup(this, R.string.app_id)) {
                    Log.w(TAG, "*** Warning: setup problems detected. Sign in may not work!");
                }

                // start the sign-in flow
                Log.d(TAG, "Sign-in button clicked");
                mSignInClicked = true;
                mGoogleApiClient.connect();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation View item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_achievements) {

        } else if (id == R.id.nav_stats_account) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_SignOut) {
            if(mGoogleApiClient != null) {
                // user wants to sign out
                // sign out.
                Log.d(TAG, "Sign-out button clicked");
                if(mGoogleApiClient.isConnected()) {
                    mSignInClicked = false;
                    Games.signOut(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                    ((TextView) mHView.findViewById(R.id.account_nickname_textView)).setText(R.string.android_studio);
                    ((TextView) mHView.findViewById(R.id.account_title_textView)).setText(R.string.android_email);
                    ((ImageView) mHView.findViewById(R.id.accountImage)).setImageResource(sym_def_app_icon);
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /*
     * API INTEGRATION SECTION. This section contains the code that integrates
     * the game with the Google Play game services API.
     */

    final static String TAG = "Raiden-Multiplayer";

    // Request codes for the UIs that we show with startActivityForResult:
    final static int RC_SELECT_PLAYERS = 10000;
    final static int RC_INVITATION_INBOX = 10001;
    final static int RC_WAITING_ROOM = 10002;

    // Request code used to invoke sign in user interactions.
    private static final int RC_SIGN_IN = 9001;

    // Client used to interact with Google APIs.
    public static GoogleApiClient mGoogleApiClient;

    // Are we currently resolving a connection failure?
    private boolean mResolvingConnectionFailure = false;

    // Has the user clicked the sign-in button?
    private boolean mSignInClicked = false;

    // Set to true to automatically start the sign in flow when the Activity starts.
    // Set to false to require the user to click the button in order to sign in.
    private boolean mAutoStartSignInFlow = true;


    // If non-null, this is the id of the invitation we received via the
    // invitation listener
    String mIncomingInvitationId = null;

    ImageManager mImageManager = null;


    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        switch (requestCode) {
            case RC_SELECT_PLAYERS:
                Log.d(TAG, "onActivityResult RC_SELECT_PLAYERS");
                break;
            case RC_INVITATION_INBOX:
                Log.d(TAG, "onActivityResult RC_INVITATION_INBOX");
                break;
            case RC_WAITING_ROOM:
                Log.d(TAG, "onActivityResult RC_WAITING_ROOM");
                break;
            case RC_SIGN_IN:
                Log.d(TAG, "onActivityResult with requestCode == RC_SIGN_IN, responseCode="
                        + responseCode + ", intent=" + intent);
                mSignInClicked = false;
                mResolvingConnectionFailure = false;

                if (responseCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                } else {
                    BaseGameUtils.showActivityResultError(this,requestCode,responseCode, R.string.signin_other_error);
                }
                break;
        }
        super.onActivityResult(requestCode, responseCode, intent);
    }




    // Activity is going to the background. We have to leave the current room.
    @Override
    public void onStop() {
        Log.d(TAG, "**** got onStop");

        // if we're in a room, leave it.
        playLauncher.leaveRoom();

        // stop trying to keep the screen on
        stopKeepingScreenOn();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            //switchToMainScreen();
        } else {
            //switchToScreen(R.id.screen_sign_in);
        }
        super.onStop();
    }

    // Activity just got to the foreground. We switch to the wait screen because we will now
    // go through the sign-in flow (remember that, yes, every time the Activity comes back to the
    // foreground we go through the sign-in flow -- but if the user is already authenticated,
    // this flow simply succeeds and is imperceptible).
    @Override
    public void onStart() {
        if (mGoogleApiClient == null) {
            //switchToScreen(R.id.screen_sign_in);
        } else if (!mGoogleApiClient.isConnected()) {
            Log.d(TAG,"Connecting client.");
            //switchToScreen(R.id.screen_wait);
            mGoogleApiClient.connect();
        } else {
            Log.w(TAG,
                    "GameHelper: client was already connected on onStart()");
        }
        super.onStart();
    }

    // Handle back key to make sure we cleanly leave a game if we are in the middle of one
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // NEED to be verifyed if is on game screen to (&& mCurScreen == R.id.screen_game)
            playLauncher.leaveRoom();
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }





    // Called when we get an invitation to play a game. We react by showing that to the user.
    @Override
    public void onInvitationReceived(Invitation invitation) {
        // We got an invitation to play a game! So, store it in
        // mIncomingInvitationId
        // and show the popup on the screen.
        mIncomingInvitationId = invitation.getInvitationId();
        /*((TextView) findViewById(R.id.incoming_invitation_text)).setText(
                invitation.getInviter().getDisplayName() + " " +
                        getString(R.string.is_inviting_you));*/
        //switchToScreen(mCurScreen); // This will show the invitation popup
    }

    @Override
    public void onInvitationRemoved(String invitationId) {

        if (mIncomingInvitationId.equals(invitationId)&&mIncomingInvitationId!=null) {
            mIncomingInvitationId = null;
            //switchToScreen(mCurScreen); // This will hide the invitation popup
        }

    }

    /*
     * CALLBACKS SECTION. This section shows how we implement the several games
     * API callbacks.
     */

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "onConnected() called. Sign in successful!");

        Log.d(TAG, "Sign-in succeeded.");

        String personName = Games.Players.getCurrentPlayer(mGoogleApiClient).getDisplayName();
        Log.d(TAG, "PersonName: " + personName);
        ((TextView) mHView.findViewById(R.id.account_nickname_textView)).setText(personName);
        String personEmail = Games.Players.getCurrentPlayer(mGoogleApiClient).getTitle();
        Log.d(TAG, "PersonEmail: " + personEmail);
        ((TextView) mHView.findViewById(R.id.account_title_textView)).setText(personEmail);
        if(Games.Players.getCurrentPlayer(mGoogleApiClient).hasIconImage()) {
            Uri personPhoto = Games.Players.getCurrentPlayer(mGoogleApiClient).getHiResImageUri();
            Log.d(TAG, "PersonPhoto: " + personPhoto);
            mImageManager.loadImage((ImageView) mHView.findViewById(R.id.accountImage), personPhoto);
        }


        // register listener so we are notified if we receive an invitation to play
        // while we are in the game
        Games.Invitations.registerInvitationListener(mGoogleApiClient, this);

        if (connectionHint != null) {
            Log.d(TAG, "onConnected: connection hint provided. Checking for invite.");
            Invitation inv = connectionHint
                    .getParcelable(Multiplayer.EXTRA_INVITATION);
            if (inv != null && inv.getInvitationId() != null) {
                // retrieve and cache the invitation ID
                Log.d(TAG,"onConnected: connection hint has a room invite!");
                playLauncher.acceptInviteToRoom(inv.getInvitationId());
                return;
            }
        }
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended() called. Trying to reconnect.");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed() called, result: " + connectionResult);

        if (mResolvingConnectionFailure) {
            Log.d(TAG, "onConnectionFailed() ignoring connection failure; already resolving.");
            return;
        }

        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient,
                    connectionResult, RC_SIGN_IN, getString(R.string.signin_other_error));
        }

        //switchToScreen(R.id.screen_sign_in);
    }

    /*
     * MISC SECTION. Miscellaneous methods.
     */

    // Clears the flag that keeps the screen on.
    void stopKeepingScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    public void onImageLoaded(Uri uri, Drawable drawable, boolean b) {

    }
}
