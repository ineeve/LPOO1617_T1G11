package com.raiden.game.model;

import com.raiden.game.model.entities.EntityModel;

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
<<<<<<< HEAD
        addEnemy(new Airplane_2_Model(x-5,15));
        addEnemy(new Airplane_2_Model(x+5,15));
        addEnemy(new Airplane_2_Model(x,20));
=======
        addEnemy((new EnemyPool()).obtain(EntityModel.ModelType.ANY, x, 15));
>>>>>>> e3c3d5d9ae12aa0b91dc24d3f0379df5bd1f63ab
    }

}
