package com.raiden.game.physics_controller;

import com.raiden.game.model.GameModel;

/**
 * Created by João on 24/04/2017.
 */

public class PVE_Controller extends Physics_Controller {


    private static PVE_Controller instance;

    private PVE_Controller(GameModel model) {
        super(model);
    }


    public static PVE_Controller getInstance(GameModel model) {
        if (instance == null)
            instance = new PVE_Controller(model);
        return instance;
    }
}
