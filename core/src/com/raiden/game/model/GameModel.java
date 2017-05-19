package com.raiden.game.model;

import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.MovingObjectModel;

import java.util.ArrayList;


/**
 * A model representing a game.
 */

public abstract class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    protected ArrayList<Airplane_1_Model> players;


    /**
     * Constructs a game level
     */
    public GameModel() {
        players = new ArrayList<Airplane_1_Model>(0);
    }

    /**
     *
     * @param x The x coordinate of the player ship in the world.
     * @param y The y coordinate of the player ship in the world.
     */
    public void addPlayer(float x, float y){
        players.add(new Airplane_1_Model(x,y));
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