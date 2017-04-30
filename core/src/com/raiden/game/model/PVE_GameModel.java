package com.raiden.game.model;

public class PVE_GameModel extends GameModel {
    /**
     * Constructs a game with a.space ship in a certain position.
     *
     * @param x the x-coordinate of the Player1 ship in meters.
     * @param y the y-coordinate of the Player1 ship in meters.
     */
    public PVE_GameModel(float x, float y) {
        super();
        addPlayer(x,y,0);
    }
    public void addPlayer2(float x,float y){
        super.addPlayer(x,y,0);
    }
}
