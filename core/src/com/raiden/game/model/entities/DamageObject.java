package com.raiden.game.model.entities;


/**
 * An interface that can be used by any object which has damage and armor
 */
public interface DamageObject {
    /**
     * Sets the damage of the object implementing this interface.
     * @param damage The damage of the object
     */
    void setDamage(int damage);

    /**
     * Retrieves the damage of the object
     * @return The damage
     */
    int getDamage();

    /**
     * Sets the armor of an object
     * @param armor the amount of armor
     */
    void setArmor(int armor);

    /**
     * Retrieves the armor of an object
     * @return the amount of armor
     */
    int getArmor();
}
