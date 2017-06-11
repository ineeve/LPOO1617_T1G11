package com.raiden.game;

/**
 * Created by João on 11/06/2017.
 */

public class ConnectionWithCore implements ConfigCore {

    private static ConnectionWithCore instance;

    public static ConnectionWithCore getInstance(){
        if(instance == null)
            instance = new ConnectionWithCore();
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


    float sensibility_X = 5.5f;
    float sensibility_Y = 2;

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
        return 0;
    }

    @Override
    public float getSensibility_Y() {
        return 0;
    }


    boolean useAccelerometer = true;

    @Override
    public void setUseAccelerometer(boolean useAccelerometer) {
        this.useAccelerometer = useAccelerometer;
    }

    @Override
    public boolean isUseAccelerometer() {
        return useAccelerometer;
    }

    boolean multiplayer;


    //The player ID used by this instance of the game.
    private String mPlayerID = "I_AM_THE_REAL_MVP";

    public String getmPlayerID() {
        return mPlayerID;
    }

    public void setmPlayerID(String mPlayerID) {
        this.mPlayerID = mPlayerID;
    }

}
