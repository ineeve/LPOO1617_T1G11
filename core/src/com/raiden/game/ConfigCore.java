package com.raiden.game;


/**
 * Config core defines is an interface that is used to implement communication between the android and core modules.
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
     * @return True if this game is multiplayer, false otherwise.
     */
    boolean isMultiplayer();

    /**
     * @param multiplayer True if this game should be multiplayer, false otherwise.
     */
    void setMultiplayer(boolean multiplayer);

    /**
     * @return True if this device is the host, false otherwise.
     */
    boolean isHost();

    /**
     * Defines this device as the host or as the client.
     * @param host True if this device is the host, false if is a client.
     */
    void setHost(boolean host);

    /**
     * @return The ID of the player playing in this device.
     */
    String getmPlayerID();

    /**
     * @param mPlayerID The ID of the player playing in this device.
     */
    void setmPlayerID(String mPlayerID);
}
