package com.raiden.game.model;

import com.raiden.game.Arena;
import com.raiden.game.Player;
import com.raiden.game.physics_controller.Physics_Controller;

import java.util.ArrayList;

public class PVE_GameModel extends GameModel {


    private static PVE_GameModel instance;

    /**
     * Constructs a game with a.space ship in a certain position.
     */
    private PVE_GameModel(){
    }

    /**
     * Returns a singleton instance of the game model
     *
     * @return the singleton instance
     */
    public static PVE_GameModel getInstance() {
        if (instance == null)
            instance = new PVE_GameModel();
        return instance;
    }


}
