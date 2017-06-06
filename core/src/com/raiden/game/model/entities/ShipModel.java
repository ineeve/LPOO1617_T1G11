package com.raiden.game.model.entities;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipModel extends MovingObjectModel{
    private static final long serialVersionUID = 9L;

    protected static final int WEIGHT_DEFAULT = 10;

    protected static final int ARMOR_DEFAULT = 10;

    protected static final int HP_DEFAULT = 50;

    protected int weight;

    private boolean canShoot = false;

    private BulletModel bullet;

    /**
     * Creates a new ship model in a certain position and having a certain rotation.
     *
     * @param x        the x-coordinate in meters
     * @param y        the y-coordinate in meters
     */
    ShipModel(float x, float y) {
        super(x, y);
        bullet = new BulletModel(x,y);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }



    public void setBullet(BulletModel bullet) {
        this.bullet = bullet;
    }

    public int getARMOR_DEFAULT() {
        return ARMOR_DEFAULT;
    }

    public int getHP_DEFAULT() {
        return HP_DEFAULT;
    }

    public void setCanShoot(boolean bool){
        canShoot = bool;
    }

    public boolean canShoot(){
        return  canShoot;
    }


}
