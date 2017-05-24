package com.raiden.game.screen.entities;

import com.raiden.game.Arena;
import com.raiden.game.model.entities.EntityModel;

import java.util.Hashtable;

/**
 * Created by João on 24/05/2017.
 */

public abstract class ViewFactory {
    private static Hashtable<EntityModel.ModelType, EntityView> modelToViewHash
            = new Hashtable<EntityModel.ModelType, EntityView>();

    public static EntityView makeView(Arena arena, EntityModel model){
        if (!modelToViewHash.containsKey(getTypeOfModel(model))) {
            switch (getTypeOfModel(model)){
                case AIRPLANE_1:
                    modelToViewHash.put(getTypeOfModel(model), new Airplane_1_View(arena));
                    break;
                case AIRPLANE_2:
                    modelToViewHash.put(getTypeOfModel(model), new Airplane_2_View(arena));
                    break;
                case AIRPLANE_3:
                    modelToViewHash.put(getTypeOfModel(model), new Airplane_3_View(arena));
                    break;
                case TANK:
                    modelToViewHash.put(getTypeOfModel(model), new Tank_View(arena));
                    break;
                case BULLET:
                    //TODO: change this to the real view class
                    modelToViewHash.put(getTypeOfModel(model), new Airplane_1_View(arena));
                    break;
                case COMET:
                    //TODO: change this to the real view class
                    modelToViewHash.put(getTypeOfModel(model), new Airplane_1_View(arena));
                    break;
                case OBSTACLE:
                    //TODO: change this to the real view class
                    modelToViewHash.put(getTypeOfModel(model), new Airplane_1_View(arena));
                    break;

            }
        }
        return modelToViewHash.get(getTypeOfModel(model));
    }

    private static EntityModel.ModelType getTypeOfModel(EntityModel model){
        return model.getType();
    }
}
