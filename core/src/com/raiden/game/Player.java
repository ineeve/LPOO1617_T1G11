package com.raiden.game;

import com.raiden.game.model.entities.ShipModel;

import java.io.Serializable;


public class Player implements Serializable {
    private static final long serialVersionUID = 12L;

    private ShipModel ship;
    private String ID;
    private int score;
    private boolean stillPlaying = true;

    public Player(String ID){
        this.ID = ID;
        score = 0;
    }

    public ShipModel getShip() {
        return ship;
    }

    public void setShip(ShipModel ship) {
        this.ship = ship;
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

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isStillPlaying() {
        return stillPlaying;
    }

    public void setStillPlaying(boolean stillPlaying) {
        this.stillPlaying = stillPlaying;
    }
}
