package com.raiden.game.model;

import com.raiden.game.model.entities.AirplaneModel;
import com.raiden.game.model.entities.MovingObjectModel;

import java.util.ArrayList;


/**
 * A model representing a game.
 */

public abstract class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    protected ArrayList<AirplaneModel> players;


    /**
     * Constructs a game level
     */
    public GameModel() {
        players = new ArrayList<AirplaneModel>(0);
    }

    /**
     *
     * @param x The x coordinate of the player ship in the world.
     * @param y The y coordinate of the player ship in the world.
     * @param rotation The rotation in radians of the player ship in the world.
     */
    public void addPlayer(float x, float y,float rotation){
        players.add(new AirplaneModel(x,y,rotation));
    }

    /**
     * Returns the player space ship.
     *
     * @return the space ship.
     */
    public MovingObjectModel getPlayer1() {
        return players.get(0);
    }


}