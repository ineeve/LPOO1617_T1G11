package com.raiden.game;

import android.app.Activity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.raiden.game.model.GameModel;

import java.util.ArrayList;
import java.util.Stack;

import static android.R.drawable.sym_def_app_icon;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        OnInvitationReceivedListener, ImageManager.OnImageLoadedListener{

    DrawerLayout drawerLayout;

    private View actualMenu;
    private Stack<View> lastMenu = new Stack<>();

    private View mHView;

    private AdView mAdView;

    private Switch accelerometerSwitch;

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

    ConnectionWithCore connectionWithCore = ConnectionWithCore.getInstance();

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

        addSeekBarTouchListener();

        //AdMob
        MobileAds.initialize(this, "ca-app-pub-1239322117847811~5610605481");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        accelerometerSwitch = (Switch) findViewById(R.id.accelerometer_switch);
        accelerometerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch", "State changed");
                connectionWithCore.setUseAccelerometer(isChecked);
                findViewById(R.id.sensibility_X_seekbar).setEnabled(isChecked);
                findViewById(R.id.sensibility_Y_seekbar).setEnabled(isChecked);
            }});

        connectionWithCore.setBroadcast(GoogleServices.getInstance());
        Arena arena = new Arena(connectionWithCore);
    }

    private void addSeekBarTouchListener() {

        View.OnTouchListener touchListenter = new ListView.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow Drawer to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow Drawer to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        if(v == findViewById(R.id.sensibility_X_seekbar)){
                            connectionWithCore.setSensibility_X(((SeekBar) v).getProgress());
                        }
                        else if(v == findViewById(R.id.sensibility_Y_seekbar)){
                            connectionWithCore.setSensibility_Y(((SeekBar) v).getProgress());
                        }
                        break;
                }

                // Handle seekbar touch events.
                v.onTouchEvent(event);
                return true;
            }
        };

        ((SeekBar) findViewById(R.id.sensibility_X_seekbar)).setMax(20);
        ((SeekBar) findViewById(R.id.sensibility_Y_seekbar)).setMax(15);
        ((SeekBar) findViewById(R.id.sensibility_X_seekbar)).setProgress((int) connectionWithCore.getSensibility_X());
        ((SeekBar) findViewById(R.id.sensibility_Y_seekbar)).setProgress((int) connectionWithCore.getSensibility_Y());
        findViewById(R.id.sensibility_X_seekbar).setOnTouchListener(touchListenter);
        findViewById(R.id.sensibility_Y_seekbar).setOnTouchListener(touchListenter);
        findViewById(R.id.volune_seekbar).setOnTouchListener(touchListenter);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.login_button:
                drawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.settings_button:
                drawerLayout.openDrawer(Gravity.END);
                break;
            case R.id.play_button:
                lastMenu.push(findViewById(R.id.main_menu_buttons));
                lastMenu.peek().setVisibility(View.GONE);
                actualMenu = findViewById(R.id.play_menu_buttons);
                actualMenu.setVisibility(View.VISIBLE);
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
                GameModel.getInstance().addPlayers(new ArrayList<Player>(){{add(new Player(connectionWithCore.getmPlayerID()));}});
                startActivity(new Intent(this, CLASSES[1]));
                break;
            case R.id.pve_multiplayer_button:
                lastMenu.push(findViewById(R.id.play_menu_buttons));
                lastMenu.peek().setVisibility(View.GONE);
                actualMenu = findViewById(R.id.multiplayer_menu_buttons);
                actualMenu.setVisibility(View.VISIBLE);
                break;
            case R.id.pvp_multiplayer_button:
                /*lastMenu.push(findViewById(R.id.play_menu_buttons));
                lastMenu.peek().setVisibility(View.GONE);
                actualMenu = findViewById(R.id.multiplayer_menu_buttons);
                actualMenu.setVisibility(View.VISIBLE);*/
                BaseGameUtils.makeSimpleDialog(this, "\n" + "This type of game is not yet implemented").show();
                break;
            case R.id.sign_in_button:
                if (!BaseGameUtils.verifySampleSetup(this, R.string.app_id)) {
                    Log.w(TAG, "*** Warning: setup problems detected. Sign in may not work!");
                }

                // start the sign-in flow
                Log.d(TAG, "Sign-in button clicked");
                mSignInClicked = true;
                mGoogleApiClient.connect();
                break;
            case R.id.quickstart_button:
                startQuickGame();
                break;
            case R.id.invite_to_play_button:
                intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(mGoogleApiClient, 1, 1);
                startActivityForResult(intent, RC_SELECT_PLAYERS);
                break;
            case R.id.view_invitation_button:
                intent = Games.Invitations.getInvitationInboxIntent(mGoogleApiClient);
                startActivityForResult(intent, RC_INVITATION_INBOX);
                break;
            case R.id.back_button:
                onKeyDown(KeyEvent.KEYCODE_BACK, null);
                break;
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation View item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_achievements) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                    REQUEST_ACHIEVEMENTS);
        } else if (id == R.id.nav_leaderboard) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                    getResources().getString(R.string.LEADERBOARD_ID)), RC_LEADERBOARD);


        } else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Raide-Multiplayer");
            String sAux = "\nThis game is amazing you must try: \n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=<AINDANAOTENHO:(> \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, getResources().getString(R.string.share)));
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

    GoogleServices mGoogleServices = new GoogleServices(this);

    final static String TAG = "Raiden-Multiplayer";

    // Request codes for the UIs that we show with startActivityForResult:
    final static int RC_SELECT_PLAYERS = 10000;
    final static int RC_INVITATION_INBOX = 10001;
    final static int RC_WAITING_ROOM = 10002;
    final static int RC_LEADERBOARD = 10003;
    final static int REQUEST_ACHIEVEMENTS = 10004;

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


    public void startQuickGame() {
        // quick-start a game with 1 randomly selected opponent
        final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
        Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
                MAX_OPPONENTS, 0);
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(mGoogleServices.roomUpdateListener);
        rtmConfigBuilder.setMessageReceivedListener(mGoogleServices.realTimeMessageReceivedListener);
        rtmConfigBuilder.setRoomStatusUpdateListener(mGoogleServices.roomStatusUpdateListener);
        rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        keepScreenOn();
        Games.RealTimeMultiplayer.create(mGoogleApiClient, rtmConfigBuilder.build());
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);

        switch (requestCode) {
            case RC_SELECT_PLAYERS:
                // we got the result from the "select players" UI -- ready to create the room
                handleSelectPlayersResult(responseCode, intent);
                break;
            case RC_INVITATION_INBOX:
                // we got the result from the "select invitation" UI (invitation inbox). We're
                // ready to accept the selected invitation:
                handleInvitationInboxResult(responseCode, intent);
                break;
            case RC_WAITING_ROOM:
                // we got the result from the "waiting room" UI.
                if (responseCode == Activity.RESULT_OK) {
                    // ready to start playing
                    Log.d(TAG, "Starting game (waiting room returned OK).");
                    startActivity(new Intent(this, CLASSES[1]));
                } else if (responseCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
                    // player indicated that they want to leave the room
                    leaveRoom();
                } else if (responseCode == Activity.RESULT_CANCELED) {
                    // Dialog was cancelled (user pressed back key, for instance). In our game,
                    // this means leaving the room too. In more elaborate games, this could mean
                    // something else (like minimizing the waiting room UI).
                    leaveRoom();
                }
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
            case RC_LEADERBOARD:
                Log.d(TAG, "Showing Leaderboard");
                break;
            case REQUEST_ACHIEVEMENTS:
                Log.d(TAG, "Showing Achievement");
                break;
        }
        super.onActivityResult(requestCode, responseCode, intent);
    }

    // Handle the result of the "Select players UI" we launched when the user clicked the
    // "Invite friends" button. We react by creating a room with those players.
    private void handleSelectPlayersResult(int response, Intent data) {
        if (response != Activity.RESULT_OK) {
            Log.w(TAG, "*** select players UI cancelled, " + response);
            return;
        }

        Log.d(TAG, "Select players UI succeeded.");

        // get the invitee list
        final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
        Log.d(TAG, "Invitee count: " + invitees.size());

        // get the automatch criteria
        Bundle autoMatchCriteria = null;
        int minAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
        int maxAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
        if (minAutoMatchPlayers > 0 || maxAutoMatchPlayers > 0) {
            autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                    minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            Log.d(TAG, "Automatch criteria: " + autoMatchCriteria);
        }

        // create the room
        Log.d(TAG, "Creating room...");
        RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(mGoogleServices.roomUpdateListener);
        rtmConfigBuilder.addPlayersToInvite(invitees);
        rtmConfigBuilder.setMessageReceivedListener(mGoogleServices.realTimeMessageReceivedListener);
        rtmConfigBuilder.setRoomStatusUpdateListener(mGoogleServices.roomStatusUpdateListener);
        if (autoMatchCriteria != null) {
            rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
        }
        keepScreenOn();
        Games.RealTimeMultiplayer.create(mGoogleApiClient, rtmConfigBuilder.build());
        Log.d(TAG, "Room created, waiting for it to be ready...");
    }

    // Handle the result of the invitation inbox UI, where the player can pick an invitation
    // to accept. We react by accepting the selected invitation, if any.
    private void handleInvitationInboxResult(int response, Intent data) {
        if (response != Activity.RESULT_OK) {
            Log.w(TAG, "*** invitation inbox UI cancelled, " + response);
            return;
        }

        Log.d(TAG, "Invitation inbox UI succeeded.");
        Invitation inv = data.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);

        // accept invitation
        acceptInviteToRoom(inv.getInvitationId());
    }

    // Accept the given invitation.
    void acceptInviteToRoom(String invId) {
        // accept the invitation
        Log.d(TAG, "Accepting invitation: " + invId);
        RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(mGoogleServices.roomUpdateListener);
        roomConfigBuilder.setInvitationIdToAccept(invId)
                .setMessageReceivedListener(mGoogleServices.realTimeMessageReceivedListener)
                .setRoomStatusUpdateListener(mGoogleServices.roomStatusUpdateListener);
        keepScreenOn();
        Games.RealTimeMultiplayer.join(mGoogleApiClient, roomConfigBuilder.build());
    }

    // Activity is going to the background. We have to leave the current room.
    @Override
    public void onStop() {
        Log.d(TAG, "**** got onStop");

        super.onStop();
    }

    // Activity just got to the foreground. We switch to the wait screen because we will now
    // go through the sign-in flow (remember that, yes, every time the Activity comes back to the
    // foreground we go through the sign-in flow -- but if the user is already authenticated,
    // this flow simply succeeds and is imperceptible).
    @Override
    public void onStart() {
        if (mGoogleApiClient != null) {
            if (!mGoogleApiClient.isConnected()) {
                Log.d(TAG,"Connecting client.");
                //switchToScreen(R.id.screen_wait);
                mGoogleApiClient.connect();
            } else {
                Log.w(TAG, "GameHelper: client was already connected on onStart()");
            }
        }
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        stopKeepingScreenOn();
    }

    // Handle back key to make sure we cleanly leave a game if we are in the middle of one
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(actualMenu == findViewById(R.id.main_menu_buttons))
                return super.onKeyDown(keyCode, e);
            actualMenu.setVisibility(View.GONE);
            lastMenu.peek().setVisibility(View.VISIBLE);
            actualMenu = lastMenu.peek();
            lastMenu.pop();
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    // Leave the room.
    void leaveRoom() {
        Log.d(TAG, "Leaving room.");
        //mSecondsLeft = 0;
        if (mGoogleServices.mRoomId != null) {
            Games.RealTimeMultiplayer.leave(mGoogleApiClient, mGoogleServices.roomUpdateListener, GoogleServices.mRoomId);
            mGoogleServices.mRoomId = null;
        }
    }

    // Show the waiting room UI to track the progress of other players as they enter the
    // room and get connected.
    void showWaitingRoom(Room room) {
        // minimum number of players required for our game
        // For simplicity, we require everyone to join the game before we start it
        // (this is signaled by Integer.MAX_VALUE).
        final int MIN_PLAYERS = Integer.MAX_VALUE;
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(mGoogleApiClient, room, MIN_PLAYERS);

        // show waiting room UI
        startActivityForResult(i, RC_WAITING_ROOM);
    }

    // Called when we get an invitation to play a game. We react by showing that to the user.
    @Override
    public void onInvitationReceived(Invitation invitation) {
        // We got an invitation to play a game! So, store it in
        // mIncomingInvitationId
        // and show the popup on the screen.
        mIncomingInvitationId = invitation.getInvitationId();
        // This will show the invitation popup
    }

    @Override
    public void onInvitationRemoved(String invitationId) {

        if (mIncomingInvitationId.equals(invitationId)&&mIncomingInvitationId!=null) {
            mIncomingInvitationId = null;
            // This will hide the invitation popup
        }

    }

    /*
     * CALLBACKS SECTION. This section shows how we implement the several games
     * API callbacks.
     */

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "onConnected() called. Sign in successful!");

        String personName = Games.Players.getCurrentPlayer(mGoogleApiClient).getDisplayName();
        ((TextView) mHView.findViewById(R.id.account_nickname_textView)).setText(personName);
        String personEmail = Games.Players.getCurrentPlayer(mGoogleApiClient).getTitle();
        ((TextView) mHView.findViewById(R.id.account_title_textView)).setText(personEmail);
        if(Games.Players.getCurrentPlayer(mGoogleApiClient).hasIconImage()) {
            Uri personPhoto = Games.Players.getCurrentPlayer(mGoogleApiClient).getHiResImageUri();
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
                acceptInviteToRoom(inv.getInvitationId());
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

    }


    // Sets the flag to keep this screen on. It's recommended to do that during
    // the
    // handshake when setting up a game, because if the screen turns off, the
    // game will be
    // cancelled.
    void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Clears the flag that keeps the screen on.
    void stopKeepingScreenOn() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Show error message about game being cancelled and return to main screen.
    void showGameError() {
        BaseGameUtils.makeSimpleDialog(this, getString(R.string.game_problem));
    }

    @Override
    public void onImageLoaded(Uri uri, Drawable drawable, boolean b) {

    }

}
