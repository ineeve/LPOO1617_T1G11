package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.raiden.game.model.PoolManager;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.physics_controller.movement.MoveManager;

import java.util.ArrayList;

import static com.raiden.game.physics_controller.movement.MoveManager.MovementType.CIRCULAR;
import static com.raiden.game.physics_controller.movement.MoveManager.MovementType.DOWNWARD;
import static com.raiden.game.physics_controller.movement.MoveManager.MovementType.HORIZONTAL;
import static com.raiden.game.screen.PVE_Screen.PIXEL_TO_METER;

/**
 * A class that can generate waves of enemies and bosses.
 */
public class EnemiesFactory {
    private static EnemiesFactory instance;
    private PoolManager poolManager = new PoolManager();

    private MoveManager.MovementType nextMoveType;

    private float xOfNextSpawn;
    private float yOfNextSpawn;

    public static EnemiesFactory getInstance(){
        if(instance == null)
            instance = new EnemiesFactory();
        return instance;
    }

    public PoolManager getPoolManager() {
        return poolManager;
    }

    void makeBoss(PVE_Screen screen) {
        OrthographicCamera camera = screen.getCamera();
        updateCoodsOfNextSpawn(camera);
        ShipModel boss = (ShipModel) poolManager.obtain(EntityModel.ModelType.AIRPLANE_3, xOfNextSpawn, yOfNextSpawn);
        boss.setMovementType(HORIZONTAL);
        boss.setHp(boss.getHP_DEFAULT() * 5);
        boss.setArmor(boss.getARMOR_DEFAULT() * 5);
        boss.setWidth(boss.getWidth() * 5);
        boss.setHeight(boss.getHeight() * 5);
        GameModel.getInstance().addEnemy(boss);
        Physics_Controller controller = Physics_Controller.getInstance();
        controller.addDynamicBody(boss);
    }

    void makeEnemy(PVE_Screen screen, EntityModel.ModelType typeOfEnemy){
        Gdx.app.log("EnemiesFactory", "makeEnemy() -> creating new enemy");
        OrthographicCamera camera = screen.getCamera();
        updateCoodsOfNextSpawn(camera);
        MovingObjectModel newEnemyModel = (MovingObjectModel) poolManager.obtain(typeOfEnemy, xOfNextSpawn, yOfNextSpawn);
        newEnemyModel.setMovementType(CIRCULAR);
        GameModel.getInstance().addEnemy(newEnemyModel);
        Physics_Controller controller = Physics_Controller.getInstance();
        controller.addDynamicBody(newEnemyModel);
    }


    void makeEnemy_Group_Horizontal(
            PVE_Screen screen, EntityModel.ModelType typeOfEnemy, int numberOfEnemies)
    {
        ArrayList<MovingObjectModel> enemies =
                createLinearHorizontalEnemies(typeOfEnemy, screen.getCamera(), numberOfEnemies);
        updateNextMovementType(typeOfEnemy);
        setMovement(enemies);
        nextMoveType = null;
        GameModel.getInstance().addEnemies(enemies);
        Physics_Controller.getInstance().addDynamicBodies(enemies);
    }

    private void updateNextMovementType(EntityModel.ModelType typeOfEnemy) {
        if(nextMoveType == null) {
            if (typeOfEnemy == EntityModel.ModelType.COMET)
                nextMoveType = DOWNWARD;
            else
                nextMoveType = HORIZONTAL;
        }
    }

    private ArrayList<MovingObjectModel> createLinearHorizontalEnemies(
            EntityModel.ModelType typeOfEnemy, OrthographicCamera camera, int numberOfEnemies)
    {
        ArrayList<MovingObjectModel> enemies = new ArrayList<MovingObjectModel>();
        updateCoodsOfNextSpawn(camera);
        xOfNextSpawn -= (numberOfEnemies - 1) * 2.5f;
        for(int i = 0; i < numberOfEnemies; i++){
            enemies.add((MovingObjectModel) poolManager.obtain(typeOfEnemy, xOfNextSpawn, yOfNextSpawn));
            xOfNextSpawn += 5f;
        }
        return enemies;
    }

    private ArrayList<MovingObjectModel> createLinearVerticalEnemies(
            EntityModel.ModelType typeOfEnemy, OrthographicCamera camera, int numberOfEnemies)
    {
        ArrayList<MovingObjectModel> enemies = new ArrayList<MovingObjectModel>();
        updateCoodsOfNextSpawn(camera);
        yOfNextSpawn -= (numberOfEnemies - 1) / 2f * 5f;
        for(int i = 0; i < numberOfEnemies; i++){
            enemies.add((MovingObjectModel) poolManager.obtain(typeOfEnemy, xOfNextSpawn, yOfNextSpawn));
            yOfNextSpawn += 5f;
        }
        return enemies;
    }

    private void setMovement(ArrayList<MovingObjectModel> models){
        for(MovingObjectModel model : models)
            model.setMovementType(nextMoveType);
    }

    private void setMovementType(MoveManager.MovementType moveType){
        nextMoveType = moveType;
    }

    public void dispose(){
        poolManager.clear();
    }

    private void updateCoodsOfNextSpawn(OrthographicCamera camera){
        float randomValue = (float) Math.random();
        xOfNextSpawn = (camera.position.x - camera.viewportWidth / 2f) * PIXEL_TO_METER;
        xOfNextSpawn += randomValue * camera.viewportWidth * PIXEL_TO_METER;
        Gdx.app.log("EnemyFactory:updateCoodsOfNextSpawn", String.valueOf(xOfNextSpawn));
        yOfNextSpawn = (camera.position.y + camera.viewportHeight / 2f) * PIXEL_TO_METER;
    }
}
