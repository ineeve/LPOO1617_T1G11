package com.raiden.game.model;

import com.raiden.game.model.entities.Airplane_2_Model;

public class PVE_GameModel extends GameModel {


    /**
     * Constructs a game with a.space ship in a certain position.
     *
     * @param x the x-coordinate of the Player1 ship in meters.
     * @param y the y-coordinate of the Player1 ship in meters.
     */
    public PVE_GameModel(float x, float y) {
        super();
        addPlayer1(x,y);
        addEnemy(new Airplane_2_Model(x-5,15));
        addEnemy(new Airplane_2_Model(x+5,15));
        addEnemy(new Airplane_2_Model(x,20));
    }

}
