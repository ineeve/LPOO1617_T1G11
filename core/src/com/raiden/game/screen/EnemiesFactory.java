package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.raiden.game.model.EnemyPool;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.physics_controller.Physics_Controller;

import static com.raiden.game.physics_controller.movement.MoveBody.MovementType.CIRCULAR;
import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;


public abstract class EnemiesFactory {
    private static EnemyPool enemyPool = new EnemyPool();

    public static EnemyPool getEnemyPool() {
        return enemyPool;
    }

    static void makeEnemy(PVE_Screen screen, EntityModel.ModelType typeOfEnemy){
        Gdx.app.log("EnemiesFactory", "makeEnemy() -> creating new enemy");
        OrthographicCamera camera = screen.getCamera();
        float x = camera.position.x * PIXEL_TO_METER;
        float y = (camera.position.y + camera.viewportHeight / 2f) * PIXEL_TO_METER;
        MovingObjectModel newEnemyModel = enemyPool.obtain(typeOfEnemy, x, y);
        newEnemyModel.setMovementType(CIRCULAR);
        GameModel model = screen.getModel();
        model.addEnemy(newEnemyModel);
        Physics_Controller controller = screen.getController();
        controller.addDynamicBody(newEnemyModel);
    }

    static void makeEnemy_Group_1(PVE_Screen screen){

    }
}
