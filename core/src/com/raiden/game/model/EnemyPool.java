package com.raiden.game.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.Airplane_2_Model;
import com.raiden.game.model.entities.Airplane_3_Model;
import com.raiden.game.model.entities.CometModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.TankModel;

import java.util.ArrayList;
import java.util.Hashtable;


public class EnemyPool {
    private Pool<MovingObjectModel> airplane_1_modelPool = new Pool<MovingObjectModel>() {
        @Override
        protected Airplane_1_Model newObject() {
            return new Airplane_1_Model(0,0);
        }
    };

    private Pool<MovingObjectModel> airplane_2_modelPool = new Pool<MovingObjectModel>() {
        @Override
        protected Airplane_2_Model newObject() {
            return new Airplane_2_Model(0,0);
        }
    };

    private Pool<MovingObjectModel> airplane_3_modelPool = new Pool<MovingObjectModel>() {
        @Override
        protected Airplane_3_Model newObject() {
            return new Airplane_3_Model(0,0);
        }
    };

    private Pool<MovingObjectModel> cometModelPool = new Pool<MovingObjectModel>() {
        @Override
        protected CometModel newObject() {
            return new CometModel(0,0);
        }
    };

    private Pool<MovingObjectModel> tankPool = new Pool<MovingObjectModel>() {
        @Override
        protected TankModel newObject() {
            return new TankModel(0,0);
        }
    };

    private Hashtable<EntityModel.ModelType, Pool<MovingObjectModel>> allPools = new Hashtable<EntityModel.ModelType, Pool<MovingObjectModel>>() {
        {
            put(EntityModel.ModelType.AIRPLANE_1, airplane_1_modelPool);
            put(EntityModel.ModelType.AIRPLANE_2, airplane_2_modelPool);
            put(EntityModel.ModelType.AIRPLANE_3, airplane_3_modelPool);
            put(EntityModel.ModelType.COMET, cometModelPool);
            put(EntityModel.ModelType.TANK, tankPool);
        }
    };

    public void clear(){
        for (Pool<MovingObjectModel> pool : allPools.values()){
            pool.clear();
        }
    }

    public MovingObjectModel obtain(EntityModel.ModelType type, float x, float y){
        MovingObjectModel entityToReturn;
        if(allPools.containsKey(type))
            entityToReturn = allPools.get(type).obtain();
        else
            entityToReturn = allPools.get(EntityModel.ModelType.getRandom()).obtain();
        entityToReturn.setPosition(x,y);
        return entityToReturn;
    }

    public MovingObjectModel obtain(EntityModel.ModelType type){
        if(allPools.containsKey(type))
            return allPools.get(type).obtain();
        else
            return allPools.get(EntityModel.ModelType.getRandom()).obtain();
    }

    public Array<MovingObjectModel> obtain(EntityModel.ModelType type, int numberOfObjects){
        Array<MovingObjectModel> arrayToReturn = new Array<MovingObjectModel>(numberOfObjects);
        if(!allPools.containsKey(type))
            type = EntityModel.ModelType.getRandom();
        for(int i = 0; i < numberOfObjects; i++){
            arrayToReturn.add(allPools.get(type).obtain());
        }
        return arrayToReturn;
    }

    public void free(MovingObjectModel entity){
        if(allPools.containsKey(entity.getType()))
            allPools.get(entity.getType()).free(entity);
    }

    public void freeAll(ArrayList<MovingObjectModel> entities){
        for (MovingObjectModel entity : entities ){
            this.free(entity);
        }
    }
}
