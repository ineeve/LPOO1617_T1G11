package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;

import java.util.Hashtable;

/**
 * Created by João on 24/05/2017.
 */

public abstract class ControllerFactory {
    private static Hashtable<EntityModel.ModelType, DynamicBody> modelToControllerHash
            = new Hashtable<EntityModel.ModelType, DynamicBody>();

    public static DynamicBody makeController(World world, EntityModel model){
        if (!modelToControllerHash.containsKey(getTypeOfModel(model))) {
            switch (getTypeOfModel(model)){
                case AIRPLANE_1:
                    modelToControllerHash.put(getTypeOfModel(model), new AirPlane_1(world,(MovingObjectModel) model));
                    break;
                case AIRPLANE_2:
                    modelToControllerHash.put(getTypeOfModel(model), new AirPlane_2(world,(MovingObjectModel) model));
                    break;
                case AIRPLANE_3:
                    modelToControllerHash.put(getTypeOfModel(model), new AirPlane_3(world,(MovingObjectModel) model));
                    break;
                case TANK:
                    modelToControllerHash.put(getTypeOfModel(model), new Tank(world,(MovingObjectModel) model));
                    break;
                case BULLET:
                    //TODO: change this to the real view class
                    modelToControllerHash.put(getTypeOfModel(model), new AirPlane_1(world,(MovingObjectModel) model));
                    break;
                case COMET:
                    //TODO: change this to the real view class
                    modelToControllerHash.put(getTypeOfModel(model), new AirPlane_1(world,(MovingObjectModel) model));
                    break;
                case OBSTACLE:
                    //TODO: change this to the real view class
                    modelToControllerHash.put(getTypeOfModel(model), new AirPlane_1(world,(MovingObjectModel) model));
                    break;

            }
        }
        return modelToControllerHash.get(getTypeOfModel(model));
    }

    private static EntityModel.ModelType getTypeOfModel(EntityModel model){
        return model.getType();
    }
}
