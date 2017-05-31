package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.model.entities.TankModel;

/**
 * Created by João on 24/05/2017.
 */

public abstract class ControllerFactory {

    public static DynamicBody makeController(World world, EntityModel model){
        DynamicBody bodyToReturn = null;
            switch (getTypeOfModel(model)){
                case AIRPLANE_1:
                     bodyToReturn = new AirPlane_1(world,(ShipModel) model);
                    break;
                case AIRPLANE_2:
                    bodyToReturn = new AirPlane_2(world,(ShipModel) model);
                    break;
                case AIRPLANE_3:
                    bodyToReturn = new AirPlane_3(world,(ShipModel) model);
                    break;
                case TANK:
                    bodyToReturn = new Tank(world,(TankModel) model);
                    break;
                case BULLET:
                    bodyToReturn = new BulletBody(world,(BulletModel) model);
                    break;
                case COMET:
                    bodyToReturn = new CometBody(world,(MovingObjectModel) model);
                    break;
                case OBSTACLE:
                    //TODO: change this to the real view class
                    bodyToReturn = new AirPlane_1(world,(MovingObjectModel) model);
                    break;
            }
        bodyToReturn.setTransform(bodyToReturn.getX(),bodyToReturn.getY(),model.getRotation());
        return bodyToReturn;
    }

    private static EntityModel.ModelType getTypeOfModel(EntityModel model){
        return model.getType();
    }
}
