package com.raiden.game;

import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;

import java.io.Serializable;

/**
 * Created by João on 01/06/2017.
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
        }
    }

}
