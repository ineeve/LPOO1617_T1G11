package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.raiden.game.model.entities.EntityModel;

/**
 * Created by João on 27/05/2017.
 */

public class LevelManager {

    private double t = 0;

    private int dificulty = 1;

    private double timeOfSpawn = 5;

    private double lastSpawn = 0;

    private boolean finnishOfLevel = false;

    private boolean makeMore = true;

    private EnemiesFactory enemiesFactory;

    private static boolean endOfGame;

    public LevelManager(PVE_Screen screen){
        enemiesFactory = new EnemiesFactory();
    }

    void updateLevel(PVE_Screen screen, float delta){
        if(makeMore) {
            timeOfSpawn = 4 * Math.exp(-0.05*(dificulty-1)) * Math.exp(-0.0075*t);
            //if (!finnishOfLevel) {
                if (t >= lastSpawn + timeOfSpawn) {
                    lastSpawn = t;
                    Gdx.app.log("LevelManager:updateLevel", "t & spawn" + String.valueOf(t % timeOfSpawn));
                    int numberOfEnemies = ((int) Math.round(Math.random() * dificulty + 0.5f) % 11);
                    Gdx.app.log("Spawn Enemies", "Number of enemies -> " + String.valueOf(numberOfEnemies));
                    enemiesFactory.makeEnemy_Group_Horizontal(screen, EntityModel.ModelType.getRandom(), numberOfEnemies);
                }
                t += delta;
            if(t > 20 * dificulty){
                t = 0;
                lastSpawn = 0;
                dificulty++;
            }
            //} else {
                //EnemiesFactory.makeBoss(screen);
                //makeMore = false;
            //}
        }
    }

    public void setFinnishOfLevel(boolean finnishOfLevel) {
        this.finnishOfLevel = finnishOfLevel;
        makeMore = false;
    }

    public static boolean isEndOfGame() {
        return endOfGame;
    }

    public static void setEndOfGame(boolean endOfGame) {
        LevelManager.endOfGame = endOfGame;
    }
}
