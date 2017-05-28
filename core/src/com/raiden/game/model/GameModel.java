package com.raiden.game.model;

import com.badlogic.gdx.utils.Pool;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.screen.EnemiesFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * A model representing a game.
 */

public abstract class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    protected ArrayList<EntityModel> entityModels = new ArrayList<EntityModel>(0);

    private ShipModel airplane11;
    private ShipModel airplane12;

    /**
     * The bullets currently flying through space.
     */
    private List<BulletModel> bullets = new ArrayList<BulletModel>();

    /**
     * A pool of bullets
     */
    Pool<BulletModel> bulletPool = new Pool<BulletModel>() {
        @Override
        protected BulletModel newObject() {
            return new BulletModel(0, 0);
        }
    };


    /**
     *
     * @param x The x coordinate of the player ship in the world.
     * @param y The y coordinate of the player ship in the world.
     */
    public void addPlayer1(float x, float y){
        airplane11 = new Airplane_1_Model(x,y);
        entityModels.add(airplane11);
    }

    /**
     * Returns the player space ship.
     *
     * @return the space ship.
     */
    public ShipModel getPlayer1() {
        return airplane11;
    }



    public BulletModel createBullet(ShipModel ship) {
        BulletModel bullet = bulletPool.obtain();

        bullet.setFlaggedForRemoval(false);
        bullet.setPosition(ship.getX(), ship.getY()+5);
        bullet.setTimeToLive(.5f);

        bullets.add(bullet);

        return bullet;
    }





    public ArrayList<EntityModel> getEntityModels(){
        return entityModels;
    }

    public void addEnemy(MovingObjectModel newEnemy){
        newEnemy.setRotation((float) Math.PI);
        entityModels.add(newEnemy);
    }

    public void addEnemies(ArrayList <MovingObjectModel> newEnemies){
        for (MovingObjectModel newEnemy : newEnemies)
            this.addEnemy(newEnemy);
    }

    public void addPlayer2(float x,float y){
        airplane12 = new Airplane_1_Model(x,y);
        entityModels.add(airplane12);
    }

    public void deleteEntityModel(EntityModel model){
        if(model != null) {
            entityModels.remove(model);
            EnemiesFactory.getEnemyPool().free(model);
        }
    }

    public void deleteEntitiesModel(ArrayList<EntityModel> models){
        for(EntityModel model : models) {
            if (model != null)
                entityModels.remove(model);
        }
    }

    public void update(float delta) {
        for (BulletModel bullet : bullets)
            if (bullet.decreaseTimeToLive(delta))
                bullet.setFlaggedForRemoval(true);
    }

    /*
    * Returns a list of all bullets in the scene
     */
    public List<BulletModel> getBullets() {
        return bullets;
    }
}