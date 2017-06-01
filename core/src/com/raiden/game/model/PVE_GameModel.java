package com.raiden.game.model;

import com.raiden.game.physics_controller.Physics_Controller;

public class PVE_GameModel extends GameModel {


    private static PVE_GameModel instance;

    /**
     * Constructs a game with a.space ship in a certain position.
     */
    private PVE_GameModel() {
        addPlayer1(Physics_Controller.ARENA_WIDTH/2.0f,5);
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
