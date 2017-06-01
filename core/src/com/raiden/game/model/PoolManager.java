package com.raiden.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.Airplane_2_Model;
import com.raiden.game.model.entities.Airplane_3_Model;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.CometModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.TankModel;

import java.util.ArrayList;
import java.util.Hashtable;


public class PoolManager {
    private Pool<EntityModel> airplane_1_modelPool = new Pool<EntityModel>() {
        @Override
        protected Airplane_1_Model newObject() {
            return new Airplane_1_Model(0,0);
        }
    };

    private Pool<EntityModel> airplane_2_modelPool = new Pool<EntityModel>() {
        @Override
        protected Airplane_2_Model newObject() {
            return new Airplane_2_Model(0,0);
        }
    };

    private Pool<EntityModel> airplane_3_modelPool = new Pool<EntityModel>() {
        @Override
        protected Airplane_3_Model newObject() {
            return new Airplane_3_Model(0,0);
        }
    };

    private Pool<EntityModel> cometModelPool = new Pool<EntityModel>() {
        @Override
        protected CometModel newObject() {
            return new CometModel(0,0);
        }
    };

    private Pool<EntityModel> tankPool = new Pool<EntityModel>() {
        @Override
        protected TankModel newObject() {
            return new TankModel(0,0);
        }
    };
    private static PoolManager instance;
    /**
     * A pool of bullets
     */
    private Pool<EntityModel> bulletPool = new Pool<EntityModel>() {
        @Override
        protected BulletModel newObject() {
            return new BulletModel(0, 0);
        }
    };

    public static PoolManager getInstance(){
        if (instance == null){
            instance = new PoolManager();
        }
        return instance;
    }

    private Hashtable<EntityModel.ModelType, Pool<EntityModel>> allPools = new Hashtable<EntityModel.ModelType, Pool<EntityModel>>() {
        {
            put(EntityModel.ModelType.AIRPLANE_1, airplane_1_modelPool);
            put(EntityModel.ModelType.AIRPLANE_2, airplane_2_modelPool);
            put(EntityModel.ModelType.AIRPLANE_3, airplane_3_modelPool);
            put(EntityModel.ModelType.COMET, cometModelPool);
            put(EntityModel.ModelType.TANK, tankPool);
            put(EntityModel.ModelType.BULLET,bulletPool);
        }
    };

    public void clear(){
        for (Pool<EntityModel> pool : allPools.values()){
            pool.clear();
        }
    }

    public EntityModel obtain(EntityModel.ModelType type, float x, float y){
        EntityModel entityToReturn;
        while (!allPools.containsKey(type))
            type = EntityModel.ModelType.getRandom();
        entityToReturn = allPools.get(type).obtain();
        entityToReturn.setPosition(x,y);
        return entityToReturn;
    }

    public EntityModel obtain(EntityModel.ModelType type){
        while (!allPools.containsKey(type))
            type = EntityModel.ModelType.getRandom();
        return allPools.get(type).obtain();
    }

    public Array<EntityModel> obtain(EntityModel.ModelType type, int numberOfObjects){
        Array<EntityModel> arrayToReturn = new Array<EntityModel>(numberOfObjects);
        while (!allPools.containsKey(type))
            type = EntityModel.ModelType.getRandom();
        for(int i = 0; i < numberOfObjects; i++){
            arrayToReturn.add(allPools.get(type).obtain());
        }
        return arrayToReturn;
    }

    public void free(EntityModel entity){
        if(entity != null)
            if(allPools.containsKey(entity.getType())) {
                Gdx.app.log("PoolManager.free()", "free model");
                allPools.get(entity.getType()).free(entity);
            }
    }

    public void freeAll(ArrayList<EntityModel> entities){
        for (EntityModel entity : entities ){
            free(entity);
        }
    }
}
