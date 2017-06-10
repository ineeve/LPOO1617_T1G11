package com.raiden.game;

import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;

import java.io.Serializable;


/**
 * An interface that defines the protocol of communication between the host and the client.
 * It also allows to make calls to the GoogleApi from inside of the core module.
 */
public interface Broadcast {
    boolean sendMessage_from_Host(GameModel model);

    boolean sendMessage_from_Client(EntityModel ship);

    void submitScore(long score);

    void leaveRoom();

    final class StructToSend implements Serializable {
        public EntityModel.ModelType type;
        public float x;
        public float y;
        public float angle;

        StructToSend(EntityModel.ModelType type, float x, float y, float angle){
            this.type = type;
            this.x = x;
            this.y = y;
            this.angle = angle;
        }
    }

    final class UserDataToSend implements Serializable {
        public int score;
        public boolean isDead;

        UserDataToSend(int score, boolean isDead){
            this.score = score;
            this.isDead = isDead;
        }
    }

}
