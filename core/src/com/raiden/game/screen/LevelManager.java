package com.raiden.game.screen;

import static com.raiden.game.model.entities.EntityModel.ModelType.AIRPLANE_2;

/**
 * Created by João on 27/05/2017.
 */

class LevelManager {

    public LevelManager(PVE_Screen screen){
        EnemiesFactory.makeEnemy_Group_Horizontal(screen, AIRPLANE_2, 3);
    }

    void updateLevel(PVE_Screen screen){
    }
}
