package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.raiden.game.model.EnemyPool;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.physics_controller.movement.MoveBody;

import java.util.ArrayList;

import static com.raiden.game.physics_controller.movement.MoveBody.MovementType.CIRCULAR;
import static com.raiden.game.physics_controller.movement.MoveBody.MovementType.DOWNWARD;
import static com.raiden.game.physics_controller.movement.MoveBody.MovementType.HORIZONTAL;
import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;


public abstract class EnemiesFactory {
    private static EnemyPool enemyPool = new EnemyPool();

    private static MoveBody.MovementType nextMoveType;

    public static EnemyPool getEnemyPool() {
        return enemyPool;
    }

    private static float xOfNextSpawn;
    private static float yOfNextSpawn;

    static void makeBoss(PVE_Screen screen) {
        OrthographicCamera camera = screen.getCamera();
        updateCoodsOfNextSpawn(camera);
        ShipModel boss = (ShipModel) enemyPool.obtain(EntityModel.ModelType.AIRPLANE_3, xOfNextSpawn, yOfNextSpawn);
        boss.setMovementType(HORIZONTAL);
        boss.setHp(boss.getHP_DEFAULT() * 5);
        boss.setArmor(boss.getARMOR_DEFAULT() * 5);
        boss.setWidth(boss.getWidth() * 5);
        boss.setHeight(boss.getHeight() * 5);
        GameModel model = screen.getModel();
        model.addEnemy(boss);
        Physics_Controller controller = screen.getController();
        controller.addDynamicBody(boss);
    }

    static void makeEnemy(PVE_Screen screen, EntityModel.ModelType typeOfEnemy){
        Gdx.app.log("EnemiesFactory", "makeEnemy() -> creating new enemy");
        OrthographicCamera camera = screen.getCamera();
        updateCoodsOfNextSpawn(camera);
        MovingObjectModel newEnemyModel = (MovingObjectModel) enemyPool.obtain(typeOfEnemy, xOfNextSpawn, yOfNextSpawn);
        newEnemyModel.setMovementType(CIRCULAR);
        GameModel model = screen.getModel();
        model.addEnemy(newEnemyModel);
        Physics_Controller controller = screen.getController();
        controller.addDynamicBody(newEnemyModel);
    }


    static void makeEnemy_Group_Horizontal(
            PVE_Screen screen, EntityModel.ModelType typeOfEnemy, int numberOfEnemies)
    {
        ArrayList<MovingObjectModel> enemies =
                createLinearHorizontalEnemies(typeOfEnemy, screen.getCamera(), numberOfEnemies);
        updateNextMovementType(typeOfEnemy);
        setMovement(enemies);
        nextMoveType = null;
        screen.getModel().addEnemies(enemies);
        screen.getController().addDynamicBodies(enemies);
    }

    private static void updateNextMovementType(EntityModel.ModelType typeOfEnemy) {
        if(nextMoveType == null) {
            if (typeOfEnemy == EntityModel.ModelType.COMET)
                nextMoveType = DOWNWARD;
            else
                nextMoveType = HORIZONTAL;
        }
    }

    private static ArrayList<MovingObjectModel> createLinearHorizontalEnemies(
            EntityModel.ModelType typeOfEnemy, OrthographicCamera camera, int numberOfEnemies)
    {
        ArrayList<MovingObjectModel> enemies = new ArrayList<MovingObjectModel>();
        updateCoodsOfNextSpawn(camera);
        xOfNextSpawn -= (numberOfEnemies - 1) * 2.5f;
        for(int i = 0; i < numberOfEnemies; i++){
            enemies.add((MovingObjectModel) enemyPool.obtain(typeOfEnemy, xOfNextSpawn, yOfNextSpawn));
            xOfNextSpawn += 5f;
        }
        return enemies;
    }

    private static ArrayList<MovingObjectModel> createLinearVerticalEnemies(
            EntityModel.ModelType typeOfEnemy, OrthographicCamera camera, int numberOfEnemies)
    {
        ArrayList<MovingObjectModel> enemies = new ArrayList<MovingObjectModel>();
        updateCoodsOfNextSpawn(camera);
        yOfNextSpawn -= (numberOfEnemies - 1) / 2f * 5f;
        for(int i = 0; i < numberOfEnemies; i++){
            enemies.add((MovingObjectModel) enemyPool.obtain(typeOfEnemy, xOfNextSpawn, yOfNextSpawn));
            yOfNextSpawn += 5f;
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

    public static void dispose(){
        enemyPool.clear();
    }

    private static void updateCoodsOfNextSpawn(OrthographicCamera camera){
        float randomValue = (float) Math.random();
        if(randomValue < 0.5)
            randomValue *= -1;
        xOfNextSpawn = (camera.position.x + randomValue * (camera.viewportWidth / 2f)) * PIXEL_TO_METER;
        Gdx.app.log("EnemyFactory:updateCoodsOfNextSpawn", String.valueOf(xOfNextSpawn));
        yOfNextSpawn = (camera.position.y + camera.viewportHeight / 2f) * PIXEL_TO_METER;
    }

}
