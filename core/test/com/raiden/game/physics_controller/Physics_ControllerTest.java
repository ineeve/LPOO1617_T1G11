package com.raiden.game.physics_controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

/**
 * Created by ineeve on 04-06-2017.
 */
public class Physics_ControllerTest extends GameTest {

    private Physics_Controller controller = Physics_Controller.getInstance();


    @Test
    public void testControllerSingleton(){
        Physics_Controller controller = Physics_Controller.getInstance();
        assertNotNull(controller);
    }

    @Test
    public void testAirplaneXBoundary(){
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
        Airplane_1_Model airplaneModel = new Airplane_1_Model(0,ARENA_HEIGHT+1);
        AirPlane_1 airplaneBody = new AirPlane_1(controller.getWorld(),airplaneModel);
        assert(controller.verifyBounds(airplaneBody.getBody(),true) == true);
        airplaneModel.setPosition(0,-1);
        assert(controller.verifyBounds(airplaneBody.getBody(),true) == true);
    }

    @Test
    public void testPlayerAirplaneYBoundary(){
        Airplane_1_Model airplaneModel = new Airplane_1_Model(0,ARENA_HEIGHT+1);
        AirPlane_1 airplaneBody = new AirPlane_1(controller.getWorld(),airplaneModel);
        controller.verifyBounds(airplaneBody.getBody(),true);
        assert( airplaneModel.getX() == 0 && airplaneModel.getY() == ARENA_HEIGHT+1 );
        airplaneModel.setPosition(0,-1);
        controller.verifyBounds(airplaneBody.getBody(),true);
        assert (airplaneModel.getX() == 0 && airplaneModel.getY() == -1);
    }

    @Test (timeout = 100)
    public void testCometKillsPlayer(){
        Player p = new Player("I_AM_THE_REAL_MVP");
        GameModel instance = GameModel.getInstance();
        instance.addPlayer(p,ARENA_WIDTH/2f,5);
        CometModel cometModel = new CometModel(ARENA_WIDTH/2,150);
        instance.addEnemy(cometModel);
        controller.createBodiesFromModel(instance);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (LevelManager.isEndOfGame()){
                return;
            }
        }

    }

    @Test (timeout = 100)
    public void testCorrectHighscoreIncrement(){
        controller.clearInstance();
        controller = controller.getInstance();
        Player p = new Player("I_AM_THE_REAL_MVP");
        GameModel gameModel = GameModel.getInstance();
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_2_Model enemy = new Airplane_2_Model(ARENA_WIDTH/2,150);
        gameModel.addEnemy(enemy);
        controller.createBodiesFromModel(gameModel);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (p.getScore() > 0){
                return;
            }
        }
    }


    @Test (timeout = 100)
    public void testBulletMakesDamageToAirplane1(){
        controller.clearInstance();
        controller = controller.getInstance();
        Player p = new Player("I_AM_THE_REAL_MVP");
        GameModel gameModel = GameModel.getInstance();
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_1_Model enemy = new Airplane_1_Model(ARENA_WIDTH/2,150);
        gameModel.addEnemy(enemy);
        float initialHP = enemy.getHp();
        controller.createBodiesFromModel(gameModel);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (enemy.getHp() < initialHP){
                return;
            }
        }
    }

    @Test (timeout = 100)
    public void testBulletMakesDamageToAirplane2(){
        controller.clearInstance();
        controller = controller.getInstance();
        Player p = new Player("I_AM_THE_REAL_MVP");
        GameModel gameModel = GameModel.getInstance();
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_2_Model enemy = new Airplane_2_Model(ARENA_WIDTH/2,150);
        gameModel.addEnemy(enemy);
        float initialHP = enemy.getHp();
        controller.createBodiesFromModel(gameModel);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (enemy.getHp() < initialHP){
                return;
            }
        }
    }

    @Test (timeout = 100)
    public void testBulletMakesDamageToAirplane3(){
        controller.clearInstance();
        controller = controller.getInstance();
        Player p = new Player("I_AM_THE_REAL_MVP");
        GameModel gameModel = GameModel.getInstance();
        gameModel.addPlayer(p,ARENA_WIDTH/2f,5);
        Airplane_3_Model enemy = new Airplane_3_Model(ARENA_WIDTH/2,150);
        gameModel.addEnemy(enemy);
        float initialHP = enemy.getHp();
        controller.createBodiesFromModel(gameModel);
        while(true){
            controller.update(Gdx.graphics.getDeltaTime());
            if (enemy.getHp() < initialHP){
                return;
            }
        }
    }







}