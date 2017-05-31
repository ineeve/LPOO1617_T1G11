package com.raiden.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.raiden.game.MainActivity.RC_WAITING_ROOM;
import static com.raiden.game.MainActivity.TAG;
import static com.raiden.game.MainActivity.mGoogleApiClient;

public class PlayLauncher extends AndroidApplication implements RealTimeMessageReceivedListener, RoomStatusUpdateListener, RoomUpdateListener {

	private Arena arena;

	private Room mRoom;

	// Room ID where the currently active game is taking place; null if we're
	// not playing.
	String mRoomId = null;

	// The participants in the currently active game
	ArrayList<Participant> mParticipants = null;

	// My participant ID in the currently active game
	String mMyId = null;

	// Are we playing in multiplayer mode?
	boolean mMultiplayer = false;

	// Message buffer for sending messages
	byte[] mMsgBuf = new byte[2];

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("IP");
        myRef.setValue("Ola do aplicativo");

		startGame();
	}

	private void startGame(){
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = true;
		config.useImmersiveMode = true;
		arena = new Arena();

		initialize(arena, config);
	}

	// Activity is going to the background. We have to leave the current room.
	@Override
	public void onStop() {
		Log.d(TAG, "**** got onStop");

		// if we're in a room, leave it.
		leaveRoom();

		if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			//switchToMainScreen();
		} else {
			//switchToScreen(R.id.screen_sign_in);
		}
		super.onStop();
	}

    /*
     * COMMUNICATIONS SECTION. Methods that implement the game's network
     * protocol.
     */

	// Score of other participants. We update this as we receive their scores
	// from the network.
	Map<String, Integer> mParticipantScore = new HashMap<>();

	// Participants who sent us their final score.
	Set<String> mFinishedParticipants = new HashSet<>();

	// Called when we receive a real-time message from the network.
	// Messages in our game are made up of 2 bytes: the first one is 'F' or 'U'
	// indicating
	// whether it's a final or interim score. The second byte is the score.
	// There is also the
	// 'S' message, which indicates that the game should start.
	@Override
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		byte[] buf = rtm.getMessageData();
		String sender = rtm.getSenderParticipantId();
		Log.d(TAG, "Message received: " + (char) buf[0] + "/" + (int) buf[1]);

		if (buf[0] == 'F' || buf[0] == 'U') {
			// score update.
			int existingScore = mParticipantScore.containsKey(sender) ?
					mParticipantScore.get(sender) : 0;
			int thisScore = (int) buf[1];
			if (thisScore > existingScore) {
				// this check is necessary because packets may arrive out of
				// order, so
				// should only ever consider the highest score we received, as
				// we know in our
				// game there is no way to lose points. If there was a way to
				// lose points,
				// we'd have to add a "serial number" to the packet.
				mParticipantScore.put(sender, thisScore);
			}

			// update the scores on the screen
			//updatePeerScoresDisplay();

			// if it's a final score, mark this participant as having finished
			// the game
			if ((char) buf[0] == 'F') {
				mFinishedParticipants.add(rtm.getSenderParticipantId());
			}
		}
	}

	// Broadcast my score to everybody else.
	void broadcastScore(boolean finalScore) {
		if (!mMultiplayer)
			return; // playing single-player mode

		// First byte in message indicates whether it's a final score or not
		mMsgBuf[0] = (byte) (finalScore ? 'F' : 'U');

		// Second byte is the score.
		mMsgBuf[1] = (byte) 10;

		// Send to every other participant.
		for (Participant p : mParticipants) {
			if (p.getParticipantId().equals(mMyId))
				continue;
			if (p.getStatus() != Participant.STATUS_JOINED)
				continue;
			if (finalScore) {
				// final score notification must be sent via reliable message
				Games.RealTimeMultiplayer.sendReliableMessage(mGoogleApiClient, null, mMsgBuf,
						mRoomId, p.getParticipantId());
			} else {
				// it's an interim score notification, so we can use unreliable
				Games.RealTimeMultiplayer.sendUnreliableMessage(mGoogleApiClient, mMsgBuf, mRoomId,
						p.getParticipantId());
			}
		}
	}

	void startQuickGame() {
		// quick-start a game with 1 randomly selected opponent
		final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
				MAX_OPPONENTS, 0);
		RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
		rtmConfigBuilder.setMessageReceivedListener(this);
		rtmConfigBuilder.setRoomStatusUpdateListener(this);
		rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
		//switchToScreen(R.id.screen_wait);
		//resetGameVars();
		Games.RealTimeMultiplayer.create(mGoogleApiClient, rtmConfigBuilder.build());
	}

	// Leave the room.
	void leaveRoom() {
		Log.d(TAG, "Leaving room.");
		//mSecondsLeft = 0;
		if (mRoomId != null) {
			Games.RealTimeMultiplayer.leave(mGoogleApiClient, this, mRoomId);
			mRoomId = null;
			//switchToScreen(R.id.screen_wait);
		} else {
			//switchToMainScreen();
		}
	}


	// Called when we are connected to the room. We're not ready to play yet! (maybe not everybody
	// is connected yet).
	@Override
	public void onConnectedToRoom(Room room) {
		Log.d(TAG, "onConnectedToRoom.");

		//get participants and my ID:
		mParticipants = room.getParticipants();
		mMyId = room.getParticipantId(Games.Players.getCurrentPlayerId(mGoogleApiClient));

		// save room ID if its not initialized in onRoomCreated() so we can leave cleanly before the game starts.
		if(mRoomId==null)
			mRoomId = room.getRoomId();

		// print out the list of participants (for debug purposes)
		Log.d(TAG, "Room ID: " + mRoomId);
		Log.d(TAG, "My ID " + mMyId);
		Log.d(TAG, "<< CONNECTED TO ROOM>>");
	}

	// Called when we've successfully left the room (this happens a result of voluntarily leaving
	// via a call to leaveRoom(). If we get disconnected, we get onDisconnectedFromRoom()).
	@Override
	public void onLeftRoom(int statusCode, String roomId) {
		// we have left the room; return to main screen.
		Log.d(TAG, "onLeftRoom, code " + statusCode);
		//switchToMainScreen();
	}

	// Called when we get disconnected from the room. We return to the main screen.
	@Override
	public void onDisconnectedFromRoom(Room room) {
		mRoomId = null;
		showGameError();
	}


	// We treat most of the room update callbacks in the same way: we update our list of
	// participants and update the display. In a real game we would also have to check if that
	// change requires some action like removing the corresponding player avatar from the screen,
	// etc.
	@Override
	public void onRoomConnecting(Room room) {
		updateRoom(room);
	}

	@Override
	public void onRoomAutoMatching(Room room) {
		updateRoom(room);
	}

	@Override
	public void onPeerInvitedToRoom(Room room, List<String> list) {
		updateRoom(room);
	}

	@Override
	public void onPeerDeclined(Room room, List<String> list) {
		updateRoom(room);
	}

	@Override
	public void onPeerJoined(Room room, List<String> list) {
		updateRoom(room);
	}

	@Override
	public void onPeerLeft(Room room, List<String> list) {
		updateRoom(room);
	}

	@Override
	public void onPeersConnected(Room room, List<String> list) {
		updateRoom(room);
	}

	@Override
	public void onPeersDisconnected(Room room, List<String> list) {
		updateRoom(room);
	}

	@Override
	public void onP2PConnected(String s) {

	}

	@Override
	public void onP2PDisconnected(String s) {

	}

	void updateRoom(Room room) {
		if (room != null) {
			mParticipants = room.getParticipants();
		}
		if (mParticipants != null) {
			//updatePeerScoresDisplay();
		}
	}

	// Called when room has been created
	@Override
	public void onRoomCreated(int statusCode, Room room) {
		Log.d(TAG, "onRoomCreated(" + statusCode + ", " + room + ")");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			Log.e(TAG, "*** Error: onRoomCreated, status " + statusCode);
			showGameError();
			return;
		}

		// save room ID so we can leave cleanly before the game starts.
		mRoomId = room.getRoomId();

		// show the waiting room UI
		showWaitingRoom(room);
	}

	// Called when room is fully connected.
	@Override
	public void onRoomConnected(int statusCode, Room room) {
		Log.d(TAG, "onRoomConnected(" + statusCode + ", " + room + ")");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
			showGameError();
			return;
		}
		updateRoom(room);
	}

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
		Log.d(TAG, "onJoinedRoom(" + statusCode + ", " + room + ")");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
			showGameError();
			return;
		}

		// show the waiting room UI
		showWaitingRoom(room);
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

	// Show error message about game being cancelled and return to main screen.
	void showGameError() {
		BaseGameUtils.makeSimpleDialog(this, getString(R.string.game_problem));
		//switchToMainScreen();
	}

}
