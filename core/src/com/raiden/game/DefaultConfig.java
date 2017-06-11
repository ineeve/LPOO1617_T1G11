package com.raiden.game;


/**
 * Class that implements a default config to be used in the tests.
 */
public class DefaultConfig implements ConfigCore {


    private float sensibility_X = 5.5f;
    private float sensibility_Y = 2;
    boolean useAccelerometer = true;
    //Flag to define this is a multiplayer game.
    private boolean multiplayer = false;
    //Flag to define if this instance will be the host or the client.
    private boolean host = false;
    //The player ID used by this instance of the game.
    private String mPlayerID = "I_AM_THE_REAL_MVP";

    private static DefaultConfig instance;

    public static DefaultConfig getInstance(){
        if(instance == null)
            instance = new DefaultConfig();
        return instance;
    }


    Broadcast broadcast;

    @Override
    public Broadcast getBroadcast() {
        return broadcast;
    }

    @Override
    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    @Override
    public void setSensibility_X(float sensibility_X) {
        this.sensibility_X = sensibility_X;
    }

    @Override
    public void setSensibility_Y(float sensibility_Y) {
        this.sensibility_Y = sensibility_Y;
    }

    @Override
    public float getSensibility_X() {
        return sensibility_X;
    }

    @Override
    public float getSensibility_Y() {
        return sensibility_Y;
    }

    @Override
    public void setUseAccelerometer(boolean useAccelerometer) {
        this.useAccelerometer = useAccelerometer;
    }
    @Override
    public boolean isUseAccelerometer() {
        return useAccelerometer;
    }

    public String getmPlayerID() {
        return mPlayerID;
    }

    public void setmPlayerID(String mPlayerID) {
        this.mPlayerID = mPlayerID;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }


    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }


    public boolean isHost() {
        return host;
    }


    public void setHost(boolean host) {
        this.host = host;
    }

}
