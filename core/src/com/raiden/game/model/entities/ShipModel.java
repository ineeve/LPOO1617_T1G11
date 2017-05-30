package com.raiden.game.model.entities;

/**
 * Created by João on 19/05/2017.
 */

public abstract class ShipModel extends MovingObjectModel{
    protected static final int WEIGHT_DEFAULT = 10;

    protected static final int ARMOR_DEFAULT = 10;

    protected static final int HP_DEFAULT = 50;

    protected int weight;

    protected int armor;

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


    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getArmor() {return armor;
    }

    public int getARMOR_DEFAULT() {
        return ARMOR_DEFAULT;
    }

    public int getHP_DEFAULT() {
        return HP_DEFAULT;
    }
}
