package com.raiden.game.physics_controller.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.physics_controller.Shoot;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipBody extends DynamicBody implements Shoot {
    //The default friction value for all ships.
    private float friction = 0.4f;

    //The default restitution value for all ships.
    private float restitution = 0.5f;

    //How much time's left to shoot again (in seconds).
    private float timeToNextShoot = 0;

    //The time in seconds between different shots.
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

    /**
     * Defines all the fixtures of this body.
     */
    public abstract void setFixtureVertices();

    /**
     * @param friction The friction of the fixture. How slippery it is.
     */
    public void setFriction(float friction) {
        this.friction = friction;
    }

    /**
     * @param restitution The restitution of the fixture. How much it bounces.
     */
    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    /**
     * Shoots a bullet from this body with attention to the fire rate defined.
     * @param deltaTime The time elapsed in seconds since last game cycle.
     * @return the bullet shoot or null if no bullet was created do to timeToNextShoot > 0.
     */
    @Override
    public BulletModel shoot(float deltaTime){
        if(((ShipModel) this.getUserData()).canShoot()) {
            if (timeToNextShoot < 0) {
                BulletModel bullet = GameModel.getInstance().createBullet((ShipModel) this.getUserData());
                bullet.setFlaggedForRemoval(false);
                bullet.setOwner((ShipModel) this.getUserData());
                timeToNextShoot = TIME_BETWEEN_SHOTS;
                return bullet;
            } else timeToNextShoot -= deltaTime;
        }
        return null;
    }
}
