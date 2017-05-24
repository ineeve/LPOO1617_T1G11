package com.raiden.game.model;

import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;

import java.util.ArrayList;


/**
 * A model representing a game.
 */

public abstract class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    protected ArrayList<EntityModel> entityModels;


    /**
     * Constructs a game level
     */
    public GameModel() {
        entityModels = new ArrayList<EntityModel>(0);
    }

    /**
     *
     * @param x The x coordinate of the player ship in the world.
     * @param y The y coordinate of the player ship in the world.
     */
    public void addPlayer(float x, float y){
        entityModels.add(new Airplane_1_Model(x,y));
    }

    /**
     * Returns the player space ship.
     *
     * @return the space ship.
     */
    public MovingObjectModel getPlayer1() {
        return (MovingObjectModel) entityModels.get(0);
    }

    public ArrayList<EntityModel> getEntityModels(){
        return entityModels;
    }

}