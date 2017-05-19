package com.raiden.game.model.entities;

/**
 * Created by João on 18/05/2017.
 */

public class TankModel extends ShipModel { /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     * @param rotation the rotation in radians
     */
    TankModel(float x, float y, float rotation) {
        super(x, y, rotation);
        TankModel.MAXVELOCITY = (float) (Airplane_1_Model.MAXVELOCITY * 0.6);
        hp = HP_DEFAULT;
        weight = WEIGHT_DEFAULT;
        armor = ARMOR_DEFAUL;
    }
}
