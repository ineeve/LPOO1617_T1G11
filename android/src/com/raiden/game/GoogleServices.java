package com.raiden.game;

import android.util.Log;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.ShipModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.raiden.game.MainActivity.TAG;
import static com.raiden.game.MainActivity.mGoogleApiClient;


public class GoogleServices implements Broadcast{

    MainActivity mMainActivity;

    GoogleServices(MainActivity activity){
        mMainActivity = activity;
    }

    // The participants in the currently active game
    ArrayList<Participant> mParticipants = null;

    // Room ID where the currently active game is taking place; null if we're
    // not playing.
    String mRoomId = null;

    // My participant ID in the currently active game
    String mMyId = null;

    RealTimeMessageReceivedListener realTimeMessageReceivedListener = new RealTimeMessageReceivedListener() {

        @Override
        public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
            GameModel model = GameModel.getInstance();
            byte[] buf = realTimeMessage.getMessageData();
            String sender = realTimeMessage.getSenderParticipantId();
            Log.d(TAG, "Message received from: " + sender);
            if(Arena.getInstance().isHost()){
                ShipModel player2 = (ShipModel) arrayByteToMsg(buf);
                if(player2 == null)
                    return;
                model.updatePlayerCoords(player2, realTimeMessage.getSenderParticipantId());
            }
            else {
                ArrayList<EntityModel> modelReceived = (ArrayList<EntityModel>) arrayByteToMsg(buf);
                if(modelReceived == null)
                    return;
            }
        }
    };

    @Override
    public boolean sendMessage_from_Host(GameModel model) {
        Log.d(TAG,"Start Sending Message from host");
        if (!Arena.getInstance().isMultiplayer())
            return false; // playing single-player mode

        byte[] mMsgBuf = msgToArrayByte(model);
        if(mMsgBuf == null)
            return false;
        sendMessage(mMsgBuf);
        return true;
    }

    @Override
    public boolean sendMessage_from_Client(EntityModel ship) {
        if (!Arena.getInstance().isMultiplayer())
            return false; // playing single-player mode

        byte[] mMsgBuf = msgToArrayByte(ship);
        if(mMsgBuf == null)
            return false;
        sendMessage(mMsgBuf);
        return true;
    }

    private byte[] msgToArrayByte(Object o){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] mMsgBuf;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();
            mMsgBuf = bos.toByteArray();
            bos.close();
        }
        catch (IOException ex) {
            return null;
        }
        return mMsgBuf;
    }

    private Object arrayByteToMsg(byte[] buf){
        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
        ObjectInput in = null;
        Object o;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
            in.close();
        }
        catch (IOException | ClassNotFoundException ex) {
                return null;
        }
        return o;
    }

    private void sendMessage(byte[] mMsgBuf){
        // Send to every other participant.
        for (Participant p : mParticipants) {
            if (p.getParticipantId().equals(mMyId))
                continue;
            if (p.getStatus() != Participant.STATUS_JOINED)
                continue;
            if (false) {
                Log.d(TAG, "Sending Message from: " + mMyId);
                // final score notification must be sent via reliable message
                Games.RealTimeMultiplayer.sendReliableMessage(mGoogleApiClient, null, mMsgBuf,
                        mRoomId, p.getParticipantId());
            } else {
                Log.d(TAG, "Sending Message from: " + mMyId);
                // it's an interim score notification, so we can use unreliable
                Games.RealTimeMultiplayer.sendUnreliableMessage(mGoogleApiClient, mMsgBuf, mRoomId,
                        p.getParticipantId());
            }
        }
    }

    private void createPlayer(){
        ArrayList<Player> players = new ArrayList<>();
        for (Participant p : mParticipants) {
            players.add(new Player(p.getParticipantId()));
        }
        GameModel.getInstance().addPlayers(players);
    }


    RoomStatusUpdateListener roomStatusUpdateListener = new RoomStatusUpdateListener() {
        // Called when we are connected to the room. We're not ready to play yet! (maybe not everybody
        // is connected yet).
        @Override
        public void onConnectedToRoom(Room room) {
            Log.d(TAG, "onConnectedToRoom.");

            //get participants and my ID:
            mParticipants = room.getParticipants();
            mMyId = room.getParticipantId(Games.Players.getCurrentPlayerId(mGoogleApiClient));
            Arena.getInstance().setmPlayerID(mMyId);
            setHost();
            //if(Arena.isHost())
                createPlayer();

            // save room ID if its not initialized in onRoomCreated() so we can leave cleanly before the game starts.
            if(mRoomId==null)
                mRoomId = room.getRoomId();

            // print out the list of participants (for debug purposes)
            Log.d(TAG, "Room ID: " + mRoomId);
            Log.d(TAG, "My ID " + mMyId);
            Log.d(TAG, "<< CONNECTED TO ROOM>>");
        }

        // Called when we get disconnected from the room. We return to the main screen.
        @Override
        public void onDisconnectedFromRoom(Room room) {
            mRoomId = null;
            mMainActivity.showGameError();
        }

        @Override
        public void onPeerDeclined(Room room, List<String> arg1) {
            updateRoom(room);
        }

        @Override
        public void onPeerInvitedToRoom(Room room, List<String> arg1) {
            updateRoom(room);
        }

        @Override
        public void onP2PDisconnected(String participant) {
        }

        @Override
        public void onP2PConnected(String participant) {
        }

        @Override
        public void onPeerJoined(Room room, List<String> arg1) {
            updateRoom(room);
        }

        @Override
        public void onPeerLeft(Room room, List<String> peersWhoLeft) {
            updateRoom(room);
        }

        @Override
        public void onRoomAutoMatching(Room room) {
            updateRoom(room);
        }

        @Override
        public void onRoomConnecting(Room room) {
            updateRoom(room);
        }

        @Override
        public void onPeersConnected(Room room, List<String> peers) {
            updateRoom(room);
        }

        @Override
        public void onPeersDisconnected(Room room, List<String> peers) {
            updateRoom(room);
        }

    };

    private void setHost() {
        String maxID = mParticipants.get(0).getParticipantId();
        for (Participant p : mParticipants){
            if(p.getParticipantId().compareTo(maxID) > 0 )
                maxID = p.getParticipantId();
        }
        if(maxID.compareTo(mMyId) == 0){
            Log.d(TAG, "Set as Host");
            Arena.getInstance().setHost(true);
        }
    }


    RoomUpdateListener roomUpdateListener = new RoomUpdateListener() {
        // Called when room has been created
        @Override
        public void onRoomCreated(int statusCode, Room room) {
            Log.d(TAG, "onRoomCreated(" + statusCode + ", " + room + ")");
            if (statusCode != GamesStatusCodes.STATUS_OK) {
                Log.e(TAG, "*** Error: onRoomCreated, status " + statusCode);
                mMainActivity.showGameError();
                return;
            }

            // save room ID so we can leave cleanly before the game starts.
            mRoomId = room.getRoomId();

            // show the waiting room UI
            mMainActivity.showWaitingRoom(room);
        }

        @Override
        public void onJoinedRoom(int statusCode, Room room) {
            Log.d(TAG, "onJoinedRoom(" + statusCode + ", " + room + ")");
            if (statusCode != GamesStatusCodes.STATUS_OK) {
                Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
                mMainActivity.showGameError();
                return;
            }

            // show the waiting room UI
            mMainActivity.showWaitingRoom(room);
        }


        // Called when we've successfully left the room (this happens a result of voluntarily leaving
        // via a call to leaveRoom(). If we get disconnected, we get onDisconnectedFromRoom()).
        @Override
        public void onLeftRoom(int statusCode, String roomId) {
            // we have left the room; return to main screen.
            Log.d(TAG, "onLeftRoom, code " + statusCode);
            //switchToMainScreen();
        }

        // Called when room is fully connected.
        @Override
        public void onRoomConnected(int statusCode, Room room) {
            Log.d(TAG, "onRoomConnected(" + statusCode + ", " + room + ")");
            if (statusCode != GamesStatusCodes.STATUS_OK) {
                Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
                mMainActivity.showGameError();
                return;
            }
            updateRoom(room);
        }
    };


    // We treat most of the room update callbacks in the same way: we update our list of
    // participants and update the display. In a real game we would also have to check if that
    // change requires some action like removing the corresponding player avatar from the screen,
    // etc.


    void updateRoom(Room room) {
        if (room != null) {
            mParticipants = room.getParticipants();
        }
        if (mParticipants != null) {
            //updatePeerScoresDisplay();
        }
    }

}