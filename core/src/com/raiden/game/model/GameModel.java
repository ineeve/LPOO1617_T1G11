package com.raiden.game.model;

import com.raiden.game.model.entities.ShipModel;


/**
 * A model representing a game.
 */

public class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    private ShipModel ship;


    /**
     * Constructs a game with a.space ship in a certain position.
     *
     * @param x the x-coordinate of the space ship in meters.
     * @param y the y-coordinate of the space ship in meters.
     */
    public GameModel(float x, float y) {

        ship = new ShipModel(x, y, 0);

    }

    /**
     * Returns the player space ship.
     *
     * @return the space ship.
     */
    public ShipModel getShip() {
        return ship;
    }


}