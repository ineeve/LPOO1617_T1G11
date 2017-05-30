package com.raiden.game.physics_controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;

/**
 * Created by João on 24/04/2017.
 */

public class PVE_Controller extends Physics_Controller implements ContactListener {


    private static PVE_Controller instance;

    private PVE_Controller(GameModel model) {
        super(model);
        world.setContactListener(this);
    }


    public static PVE_Controller getInstance(GameModel model) {
        if (instance == null)
            instance = new PVE_Controller(model);
        return instance;
    }

    /*
  * A contact between two objects was detected
  *
  * @param contact the detected contact
  */
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Contact","Contact detected");
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof BulletModel && bodyB.getUserData() instanceof MovingObjectModel)
            bulletCollision(bodyA, bodyB);
        else if (bodyA.getUserData() instanceof MovingObjectModel && bodyB.getUserData() instanceof BulletModel)
            bulletCollision(bodyB, bodyA);

    }

    private void  bulletCollision(Body bulletBody,Body bodyB){
        Gdx.app.log("Collision","Bullet collosion detected");
        MovingObjectModel bModel = (MovingObjectModel) bodyB.getUserData();
        BulletModel bulletModel = (BulletModel) bulletBody.getUserData();
        if (bModel != null && bulletModel != null) {
            int bulletDamage = bulletModel.getDamage();
            bModel.decreaseHP(bulletDamage);
            Gdx.app.log("Collision", "Moving Object HP =" + bModel.getHp());
            if (bModel.getHp() < 0) {
                bModel.setFlaggedForRemoval(true);
            }
            bulletModel.setFlaggedForRemoval(true);
        }

    }
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
