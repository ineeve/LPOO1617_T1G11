package com.raiden.game;

/**
 * Created by João on 11/06/2017.
 */

public interface ConfigCore {

    float getSensibility_X();

    float getSensibility_Y();

    void setSensibility_X(float sensibility_X);

    void setSensibility_Y(float sensibility_Y);

    Broadcast getBroadcast();

    void setBroadcast(Broadcast broadcast);

    /**
     * Sets accelerometer flag
     * @param useAccelerometer Set true to use the accelerometer, false to use the touch as input
     */
    void setUseAccelerometer(boolean useAccelerometer);

    /**
     * Gets accelerometer switch state.
     * @return True if accelerometer switch is enabled, false otherwise
     */
    boolean isUseAccelerometer();

    /**
     * @return The ID of the player playing in this device.
     */
    String getmPlayerID();

    /**
     * @param mPlayerID The ID of the player playing in this device.
     */
    void setmPlayerID(String mPlayerID);
}
