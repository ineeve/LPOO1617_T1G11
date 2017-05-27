package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.raiden.game.model.EnemyPool;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.physics_controller.movement.MoveBody;

import java.util.ArrayList;

import static com.raiden.game.physics_controller.movement.MoveBody.MovementType.CIRCULAR;
import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;


public abstract class EnemiesFactory {
    private static EnemyPool enemyPool = new EnemyPool();

    private static MoveBody.MovementType nextMoveType;

    public static EnemyPool getEnemyPool() {
        return enemyPool;
    }

    static void makeEnemy(PVE_Screen screen, EntityModel.ModelType typeOfEnemy){
        Gdx.app.log("EnemiesFactory", "makeEnemy() -> creating new enemy");
        OrthographicCamera camera = screen.getCamera();
        float x = camera.position.x * PIXEL_TO_METER;
        float y = (camera.position.y + camera.viewportHeight / 2f) * PIXEL_TO_METER;
        MovingObjectModel newEnemyModel = (MovingObjectModel) enemyPool.obtain(typeOfEnemy, x, y);
        newEnemyModel.setMovementType(CIRCULAR);
        GameModel model = screen.getModel();
        model.addEnemy(newEnemyModel);
        Physics_Controller controller = screen.getController();
        controller.addDynamicBody(newEnemyModel);
    }


    static void makeEnemy_Group_Horizontal(PVE_Screen screen, EntityModel.ModelType typeOfEnemy, int numberOfEnemies){
        ArrayList<MovingObjectModel> enemies =
                createLinearHorizontalEnemies(typeOfEnemy, screen.getCamera(), numberOfEnemies);
        if(nextMoveType == null)
            nextMoveType = CIRCULAR;
        setMovement(enemies);
        nextMoveType = null;
        screen.getModel().addEnemies(enemies);
        screen.getController().addDynamicBodies(enemies);
    }

    private static ArrayList<MovingObjectModel> createLinearHorizontalEnemies(EntityModel.ModelType typeOfEnemy, OrthographicCamera camera, int numberOfEnemies){
        ArrayList<MovingObjectModel> enemies = new ArrayList<MovingObjectModel>();
        float x = camera.position.x * PIXEL_TO_METER -(numberOfEnemies - 1) / 2f * 5f;
        float y = (camera.position.y + camera.viewportHeight / 2f) * PIXEL_TO_METER;
        for(int i = 0; i < numberOfEnemies; i++){
            enemies.add((MovingObjectModel) enemyPool.obtain(typeOfEnemy, x, y));
            x += 5f;
        }
        return enemies;
    }

    private static ArrayList<MovingObjectModel> createLinearVerticalEnemies(EntityModel.ModelType typeOfEnemy, OrthographicCamera camera, int numberOfEnemies){
        ArrayList<MovingObjectModel> enemies = new ArrayList<MovingObjectModel>();
        float x = camera.position.x * PIXEL_TO_METER;
        float y = (camera.position.y + camera.viewportHeight / 2f) * PIXEL_TO_METER - (numberOfEnemies - 1) / 2f * 5f;
        for(int i = 0; i < numberOfEnemies; i++){
            y += 5f;
            enemies.add((MovingObjectModel) enemyPool.obtain(typeOfEnemy, x, y));
        }
        return enemies;
    }

    private static void setMovement(ArrayList<MovingObjectModel> models){
        for(MovingObjectModel model : models)
            model.setMovementType(nextMoveType);
    }

    private static void setMovementType(MoveBody.MovementType moveType){
        nextMoveType = moveType;
    }
}
