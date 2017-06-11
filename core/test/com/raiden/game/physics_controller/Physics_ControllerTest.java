package com.raiden.game.physics_controller;

import com.badlogic.gdx.Gdx;
import com.raiden.game.Arena;
import com.raiden.game.DefaultConfig;
import com.raiden.game.Player;
import com.raiden.game.model.GameModel;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.Airplane_2_Model;
import com.raiden.game.model.entities.Airplane_3_Model;
import com.raiden.game.model.entities.CometModel;
import com.raiden.game.physics_controller.entities.AirPlane_1;
import com.raiden.game.screen.LevelManager;


import org.junit.Test;

import static com.raiden.game.physics_controller.Physics_Controller.ARENA_HEIGHT;
import static com.raiden.game.physics_controller.Physics_Controller.ARENA_WIDTH;
import static org.junit.Assert.*;

public class Physics_ControllerTest extends GameTest {

    private Physics_Controller controller = Physics_Controller.getInstance();
    private DefaultConfig cfg = DefaultConfig.getInstance();

    @Test
    public void testControllerSingleton(){
        Physics_Controller controller = Physics_Controller.getInstance();
        assertNotNull(controller);
    }


    @Test
    public void testAirplaneXBoundary(){
        Arena.getInstance().setConfigCore(cfg);
        Airplane_1_Model airplaneModel = new Airplane_1_Model(-1f,0f);
        AirPlane_1 airplaneBody = new AirPlane_1(controller.getWorld(),airplaneModel);
        controller.verifyBounds(airplaneBody.getBody(),true);
        assert(airplaneModel.getX() >= 0);
        airplaneModel.setPosition(ARENA_WIDTH+1,0);
        controller.verifyBounds(airplaneBody.getBody(),true);
        assert(airplaneModel.getX() <= ARENA_WIDTH);
    }

    @Test
    public void testEnemyAirplaneYBoundary(){
        Arena.getInstance().setConfigCore(cfg);
        Airplane_1_Model airplaneModel = new Airplane_1_Model(0,ARENA_HEIGHT+1);
        AirPlane_1 airplaneBody = new AirPlane_1(controller.getWorld(),airplaneModel);
        assert(controller.verifyBounds(airplaneBody.getBody(),true) == true);
        airplaneModel.setPosition(0,-1);
        assert(controller.verifyBounds(airplaneBody.getBody(),true) == true);
    }

    @Test
    public void testPlayerAirplaneYBoundary(){
        Arena.getInstance().setConfigCore(cfg);
        Airplane_1_Model airplaneModel = new Airplane_1_Model(0,ARENA_HEIGHT+1);
        AirPlane_1 airplaneBody = new AirPlane_1(controller.getWorld(),airplaneModel);
        controller.verifyBounds(airplaneBody.getBody(),true);
        assert( airplaneModel.getX() == 0 && airplaneModel.getY() == ARENA_HEIGHT+1 );
        airplaneModel.setPosition(0,-1);
        controller.verifyBounds(airplaneBody.getBody(),true);
        assert (airplaneModel.getX() == 0 && airplaneModel.getY() == -1);
    }



    @Test (timeout = 50)
    public void testCorrectHighscoreIncrement(){
        Arena.getInstance().setConfigCore(cfg);
        GameModel.clearInstance();
        GameModel gameModel = GameModel.getInstance();
        Player p = new Player(cfg.getmPlayerID());
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_1_Model enemy = new Airplane_1_Model(ARENA_WIDTH/2,400);
        gameModel.addEnemy(enemy);
        controller.clearInstance();
        controller = controller.getInstance();
        controller.addDynamicBody(enemy);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (p.getScore() > 0){
                return;
            }
        }
    }


    @Test (timeout = 50)
    public void testBulletMakesDamageToAirplane1(){
        Arena.getInstance().setConfigCore(cfg);
        GameModel.clearInstance();
        GameModel gameModel = GameModel.getInstance();
        Player p = new Player(cfg.getmPlayerID());
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_1_Model enemy = new Airplane_1_Model(ARENA_WIDTH/2,50);
        gameModel.addEnemy(enemy);
        float initialHP = enemy.getHp();
        controller.clearInstance();
        controller = controller.getInstance();
        controller.addDynamicBody(enemy);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (enemy.getHp() < initialHP){
                return;
            }
        }
    }

    @Test (timeout = 50)
    public void testBulletMakesDamageToAirplane2(){
        Arena.getInstance().setConfigCore(cfg);
        GameModel.clearInstance();
        GameModel gameModel = GameModel.getInstance();
        Player p = new Player(cfg.getmPlayerID());
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_2_Model enemy = new Airplane_2_Model(ARENA_WIDTH/2,50);
        gameModel.addEnemy(enemy);
        float initialHP = enemy.getHp();
        controller.clearInstance();
        controller = controller.getInstance();
        controller.addDynamicBody(enemy);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (enemy.getHp() < initialHP){
                return;
            }
        }
    }

    @Test (timeout = 50)
    public void testBulletMakesDamageToAirplane3(){
        Arena.getInstance().setConfigCore(cfg);
        GameModel.clearInstance();
        GameModel gameModel = GameModel.getInstance();
        Player p = new Player(cfg.getmPlayerID());
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_3_Model enemy = new Airplane_3_Model(ARENA_WIDTH/2,50);
        gameModel.addEnemy(enemy);
        float initialHP = enemy.getHp();
        controller.clearInstance();
        controller = controller.getInstance();
        controller.addDynamicBody(enemy);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (enemy.getHp() < initialHP){
                return;
            }
        }
    }

    @Test (timeout=50)
    public void testCometKillsPlayer(){
        Arena.getInstance().setConfigCore(cfg);
        GameModel.clearInstance();
        GameModel gameModel = GameModel.getInstance();
        Player p = new Player(cfg.getmPlayerID());
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        CometModel enemy = new CometModel(ARENA_WIDTH/2,50);
        gameModel.addEnemy(enemy);
        controller.clearInstance();
        controller = controller.getInstance();
        controller.addDynamicBody(enemy);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (LevelManager.isEndOfGame()){
                return;
            }
        }
    }

}