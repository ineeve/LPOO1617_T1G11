package com.raiden.game;

import com.raiden.game.model.entities.ShipModel;

import java.io.Serializable;

/**
 * Created by ineeve on 01-06-2017.
 */

public class Player implements Serializable {

    private ShipModel myShip;
    private String ID;
    private int score;

    public Player(String ID){
        this.ID = ID;
        score = 0;
    }

    public ShipModel getMyShip() {
        return myShip;
    }

    public void setMyShip(ShipModel myShip) {
        this.myShip = myShip;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score += 1;
    }

    public String getID() {
        return ID;
    }
}
