package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.PVE_GameModel;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.physics_controller.Shoot;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipBody extends DynamicBody implements Shoot {

    private float friction = 0.4f;

    private float restitution = 0.5f;

    private float timeToNextShoot = 0;

    private final float TIME_BETWEEN_SHOTS = 0.2f;

    /**
     * Constructs a space ship body according to
     * a space ship model.
     *
     * @param world the physical world this space ship belongs to.
     * @param model the model representing this space ship.
     */
    public ShipBody(World world, MovingObjectModel model) {
        super(world, model);
        super.setFriction(friction);
        super.setRestitution(restitution);
        movementType = model.getMovementType();
    }

    public abstract void updatePhysics();

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    @Override
    public void shoot(float deltaTime){
        if (timeToNextShoot < 0) {
            BulletModel bullet = PVE_GameModel.getInstance().createBullet((ShipModel)this.getUserData());
            bullet.setFlaggedForRemoval(false);
            DynamicBody body = Physics_Controller.getInstance().addDynamicBody(bullet);
            body.setVelocity(0,body.getMaxVelocity());
            timeToNextShoot = TIME_BETWEEN_SHOTS;
        }else timeToNextShoot -= deltaTime;

    }
}
