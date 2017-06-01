package com.raiden.game.model;

import com.badlogic.gdx.utils.Pool;
import com.raiden.game.Player;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.EnemiesFactory;

import java.util.ArrayList;


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

    private ArrayList<Player> players;

    /**
     * A pool of bullets
     */
    private Pool<BulletModel> bulletPool = new Pool<BulletModel>() {
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
    public void addPlayer(float x, float y){
        entityModels.add(new Airplane_1_Model(x,y));
    }


    void addPlayers(ArrayList<Player> players){
        float xStart = (Physics_Controller.ARENA_WIDTH - (players.size() - 1) * 5f) / 2f;
        for(Player player : players){
            addPlayer(xStart, 5);
            this.players.add(player);
            xStart += 5;
        }
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
        bullet.setPosition(
                ship.getX() + (float) Math.sin(ship.getRotation()) * 2f,
                ship.getY() + (float) Math.cos(ship.getRotation()) * 2f);
        bullet.setOwner(ship);
        entityModels.add(bullet);
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

    public void deleteEntityModel(EntityModel model){
        if(model != null) {
            entityModels.remove(model);
            if(model instanceof BulletModel){
                bulletPool.free((BulletModel)model);
            }else {
                model.setFlaggedForRemoval(false);
                EnemiesFactory.getInstance().getEnemyPool().free(model);
            }
        }
    }

    public void deleteEntitiesModel(ArrayList<EntityModel> models){
        for(EntityModel model : models) {
            if (model != null)
                entityModels.remove(model);
        }
    }

}