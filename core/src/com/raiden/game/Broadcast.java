package com.raiden.game;

import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;

/**
 * Created by João on 01/06/2017.
 */

public interface Broadcast {
    boolean sendMessage_from_Host(GameModel model);

    boolean sendMessage_from_Client(EntityModel ship);

    void leaveRoom();
}
