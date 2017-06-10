package com.raiden.game.model.entities;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipModel extends MovingObjectModel{
    //Id used for serialization.
    private static final long serialVersionUID = 9L;

    //The default weight of a ship model.
    protected static final int WEIGHT_DEFAULT = 10;

    //The default armor of a ship model.
    protected static final int ARMOR_DEFAULT = 10;

    //The default hp (Health Points) of a ship model.
    protected static final int HP_DEFAULT = 50;

    //The actual weight of this ship.
    protected int weight;

    //Flag that should only be true if this ship should be able to shoot.
    private boolean canShoot = false;

    //The bullet associated with this ship. Currently not in use.
    private BulletModel bullet;


    /**
     * Creates a new ship model in a certain position and creates a bullet for this ship.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    ShipModel(float x, float y) {
        super(x, y);
        bullet = new BulletModel(x,y);
    }

    /**
     * @param hp The amount of Health Points.
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @param bullet Sets the bullet associated with this ship.
     */
    public void setBullet(BulletModel bullet) {
        this.bullet = bullet;
    }

    /**
     * @return The Defauld Armor of any ship model.
     */
    public int getARMOR_DEFAULT() {
        return ARMOR_DEFAULT;
    }

    /**
     * @return The Default HP of any ship model.
     */
    public int getHP_DEFAULT() {
        return HP_DEFAULT;
    }

    /**
     * @param bool True if this model can shoot, false otherwise.
     */
    public void setCanShoot(boolean bool){
        canShoot = bool;
    }

    /**
     * @return True if this model can shoot, false otherwise.
     */
    public boolean canShoot(){
        return  canShoot;
    }

}
