package com.raiden.game.screen.entities;

import com.raiden.game.Arena;
import com.raiden.game.model.entities.EntityModel;

import java.util.Hashtable;

/**
 * Created by João on 24/05/2017.
 */

public class ViewFactory {

    private static ViewFactory instance;

    private Hashtable<EntityModel.ModelType, EntityView> modelToViewHash
            = new Hashtable<EntityModel.ModelType, EntityView>();

    public EntityView makeView(Arena arena, EntityModel model){
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
                    modelToViewHash.put(getTypeOfModel(model), new BulletView(arena));
                    break;
                case COMET:
                    modelToViewHash.put(getTypeOfModel(model), new CometView(arena));
                    break;
                case OBSTACLE:
                    //TODO: change this to the real view class
                    modelToViewHash.put(getTypeOfModel(model), new Airplane_1_View(arena));
                    break;
            }
        }
        modelToViewHash.get(getTypeOfModel(model)).sprite.setRotation((float) ((model.getRotation() * 180) / Math.PI));
        return modelToViewHash.get(getTypeOfModel(model));
    }

    private EntityModel.ModelType getTypeOfModel(EntityModel model){
        return model.getType();
    }

    public void dispose() {
        modelToViewHash.clear();
    }

    public static ViewFactory getInstance(){
        if(instance == null)
            instance = new ViewFactory();
        return instance;
    }
}
