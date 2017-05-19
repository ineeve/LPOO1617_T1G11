package com.raiden.game.model;


public class PVP_GameModel extends GameModel {
    /**
     * Constructs a game with a.space ship in a certain position.
     *
     * @param x1 the x-coordinate of the player1 ship in meters.
     * @param y1 the y-coordinate of the player1 ship in meters.
     * @param x2 the x-coordinate of the player1 ship in meters.
     * @param y2 the y-coordinate of the player1 ship in meters.
     */
    public PVP_GameModel(float x1, float y1,float x2,float y2) {
        super();
        addPlayer(x1,y1);
        addPlayer(x2,y2);
    }
}
