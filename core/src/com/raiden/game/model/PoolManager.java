package com.raiden.game.model;

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

//Responsible for managing several pools of models.
public class PoolManager {
    //A pool of airplane_1 Models
    private Pool<EntityModel> airplane_1_modelPool = new Pool<EntityModel>() {
        @Override
        protected Airplane_1_Model newObject() {
            return new Airplane_1_Model(0,0);
        }
    };
    //A pool of airplane_2 Models
    private Pool<EntityModel> airplane_2_modelPool = new Pool<EntityModel>() {
        @Override
        protected Airplane_2_Model newObject() {
            return new Airplane_2_Model(0,0);
        }
    };
    //A pool of airplane_3 Models
    private Pool<EntityModel> airplane_3_modelPool = new Pool<EntityModel>() {
        @Override
        protected Airplane_3_Model newObject() {
            return new Airplane_3_Model(0,0);
        }
    };
    //A pool of comet Models
    private Pool<EntityModel> cometModelPool = new Pool<EntityModel>() {
        @Override
        protected CometModel newObject() {
            return new CometModel(0,0);
        }
    };

    //A pool of tank Models
    private Pool<EntityModel> tankPool = new Pool<EntityModel>() {
        @Override
        protected TankModel newObject() {
            return new TankModel(0,0);
        }
    };

     //A pool of bullets
    private Pool<EntityModel> bulletPool = new Pool<EntityModel>() {
        @Override
        protected BulletModel newObject() {
            return new BulletModel(0, 0);
        }
    };

    /**
     * An hashtable in which the IDs are the enum of the ModelType's, and the values are the pools of those objects.
     */
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

    //The instance of this pool.
    private static PoolManager instance;

    /**
     * Creates an instance of this PoolManager if it does not exist and returns it.
     * @return the instance.
     */
    public static PoolManager getInstance(){
        if (instance == null){
            instance = new PoolManager();
        }
        return instance;
    }

    /**
     * Removes all the pools from the hashtable.
     */
    public void clear(){
        for (Pool<EntityModel> pool : allPools.values()){
            pool.clear();
        }
    }

    /**
     * Obtains a new model of the type defined and with the position already set.
     * @param type The type of model to obtain.
     * @param x The x coordinate of the object to obtain (meters).
     * @param y The y coordinate of the object to obtain (meters).
     * @return A model of the type defined and with the position already set.
     */
    public EntityModel obtain(EntityModel.ModelType type, float x, float y){
        EntityModel entityToReturn = obtain(type);
        entityToReturn.setPosition(x,y);
        return entityToReturn;
    }

    /**
     * Obtains a model but does not set it's position.
     * @param type The type of model to obtain.
     * @return A model of the type defined
     */
    public EntityModel obtain(EntityModel.ModelType type){
        type = getModelType(type);
        return allPools.get(type).obtain();
    }

    /**
     * Obtains an array with multiple models of a type.
     * If the pool of the desired type is currently full,
     * it returns an array containing another random model.
     * @param type The preferential type of model to return
     * @param numberOfObjects The number of models to obtain
     * @return An array with numberOfObjects models.
     */
    public Array<EntityModel> obtain(EntityModel.ModelType type, int numberOfObjects){
        Array<EntityModel> arrayToReturn = new Array<EntityModel>(numberOfObjects);
        type = getModelType(type);
        for(int i = 0; i < numberOfObjects; i++){
            arrayToReturn.add(allPools.get(type).obtain());
        }
        return arrayToReturn;
    }

    /**
     * Checks if a model is available in the respective pool.
     * @param type The ModelType to check if the pool is full.
     * @return If model available, returns the ModelType passed as parameter. Otherwise it returns a random ModelType.
     */
    private EntityModel.ModelType getModelType(EntityModel.ModelType type) {
        while (!allPools.containsKey(type) && type != EntityModel.ModelType.BULLET)
            type = EntityModel.ModelType.getRandom();
        return type;
    }

    /**
     * Frees a model from its pool.
     * @param entity
     */
    public void free(EntityModel entity){
        if(entity != null)
            if(allPools.containsKey(entity.getType())) {
                allPools.get(entity.getType()).free(entity);
            }
    }

    /**
     * Frees every model from the pools.
     * @param entities
     */
    public void freeAll(ArrayList<EntityModel> entities){
        for (EntityModel entity : entities ){
            free(entity);
        }
    }
}
