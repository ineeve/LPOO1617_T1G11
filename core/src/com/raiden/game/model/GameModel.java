package com.raiden.game.model;

import com.raiden.game.model.entities.AirplaneModel;
import com.raiden.game.model.entities.MovingObjectModel;


/**
 * A model representing a game.
 */

public class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    private AirplaneModel player1;
    private AirplaneModel player2;


    /**
     * Constructs a game with a.space ship in a certain position.
     *
     * @param x the x-coordinate of the space ship in meters.
     * @param y the y-coordinate of the space ship in meters.
     */
    public GameModel(float x, float y) {

        player1 = new AirplaneModel(x, y, 3.141592f/2);

    }

    /**
     * Returns the player space ship.
     *
     * @return the space ship.
     */
    public MovingObjectModel getPlayer1() {
        return player1;
    }


}