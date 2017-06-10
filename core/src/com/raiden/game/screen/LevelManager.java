package com.raiden.game.screen;

import com.badlogic.gdx.Gdx;
import com.raiden.game.model.entities.EntityModel;

/**
 * A class responsible for managing the frequency in which enemy waves are generated.
 */
public class LevelManager {

    private double t = 0;

    private int difficulty = 1;

    private double lastSpawn = 0;

    private boolean finnishOfLevel = false;

    private EnemiesFactory enemiesFactory;

    private static boolean endOfGame = false;

    public LevelManager(){
        enemiesFactory = new EnemiesFactory();
    }

    void updateLevel(PVE_Screen screen, float delta){
        if(!endOfGame) {
            double timeOfSpawn = 4 * Math.exp(-0.05 * (difficulty - 1)) * Math.exp(-0.0075 * t);
            //if (!finnishOfLevel) {
                if (t >= lastSpawn + timeOfSpawn) {
                    lastSpawn = t;
                    Gdx.app.log("LevelManager:updateLevel", "t & spawn" + String.valueOf(t % timeOfSpawn));
                    int numberOfEnemies = ((int) Math.round(Math.random() * difficulty + 0.5f) % 11);
                    Gdx.app.log("Spawn Enemies", "Number of enemies -> " + String.valueOf(numberOfEnemies));
                    enemiesFactory.makeEnemy_Group_Horizontal(screen, EntityModel.ModelType.getRandom(), numberOfEnemies);
                }
                t += delta;
                if(t > 20 * difficulty){
                    t = 0;
                    lastSpawn = 0;
                    difficulty++;
                }
            //} else {
                //EnemiesFactory.makeBoss(screen);
                //endOfFame = false;
            //}
        }
    }

    public void setFinnishOfLevel(boolean finnishOfLevel) {
        this.finnishOfLevel = finnishOfLevel;
    }

    public static boolean isEndOfGame() {
        return endOfGame;
    }

    public static void setEndOfGame(boolean endOfGame) {
        LevelManager.endOfGame = endOfGame;
    }
}
