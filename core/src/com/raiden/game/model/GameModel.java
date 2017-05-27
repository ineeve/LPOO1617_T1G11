package com.raiden.game.model;

import com.badlogic.gdx.utils.Pool;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A model representing a game.
 */

public abstract class GameModel {
    /**
     * The space ship controlled by the user in this game.
     */
    protected ArrayList<EntityModel> entityModels;

    private ShipModel airplane11;
    private ShipModel airplane12;

    /**
     * The bullets currently flying through space.
     */
    private List<BulletModel> bullets;

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
     * Constructs a game level
     */
    public GameModel() {
        entityModels = new ArrayList<EntityModel>(0);
    }

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
    public MovingObjectModel getPlayer1() {
        return (MovingObjectModel) airplane11;
    }

    public ArrayList<EntityModel> getEntityModels(){
        return entityModels;
    }

    public void addEnemy(MovingObjectModel newEnemy){
        newEnemy.setRotation((float) Math.PI);
        entityModels.add(newEnemy);
    }

    public void addPlayer2(float x,float y){
        airplane12 = new Airplane_1_Model(x,y);
        entityModels.add(airplane12);
    }

    public void deleteEntityModel(EntityModel model){
        if(model != null)
            entityModels.remove(model);
    }

    public void deleteEntitiesModel(ArrayList<EntityModel> models){
        for(EntityModel model : models) {
            if (model != null)
                entityModels.remove(model);
        }
    }


}