package com.raiden.game.model;

import com.raiden.game.Arena;
import com.raiden.game.Broadcast;
import com.raiden.game.Player;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.BulletModel;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.MovingObjectModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.Physics_Controller;
import com.raiden.game.screen.EnemiesFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A model representing a game.
 */

public class GameModel implements Serializable {
    private static final long serialVersionUID = 11L;

    private static GameModel instance;

    /**
     * Returns a singleton instance of the game model
     *
     * @return the singleton instance
     */
    public static GameModel getInstance() {
        if (instance == null)
            instance = new GameModel();
        return instance;
    }

    public static void setInstance(GameModel instance) {
        GameModel.instance = instance;
    }

    /**
     * The space ship controlled by the user in this game.
     */
    List<EntityModel> entityModels =
            Collections.synchronizedList(new ArrayList<EntityModel>());

    private ArrayList<Player> players = new ArrayList<Player>();


    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param x The x coordinate of the player ship in the world.
     * @param y The y coordinate of the player ship in the world.
     */
    public void addPlayer(Player player,float x, float y){
        Airplane_1_Model myShip = new Airplane_1_Model(x,y);
        if((Arena.getInstance().isHost() && Arena.getInstance().isMultiplayer()) || !Arena.getInstance().isMultiplayer())
            myShip.setCanShoot(true);
        myShip.setPlayer(true);
        player.setMyShip(myShip);
        entityModels.add(player.getMyShip());
        this.players.add(player);
    }


    public void addPlayers(ArrayList<Player> players){
        float xStart = (Physics_Controller.ARENA_WIDTH - (players.size() - 1) * 5f) / 2f;
        for(Player player : players){
            addPlayer(player, xStart, 5);
            xStart += 5;
        }
    }

    /**
     * Returns the player space ship.
     *
     * @return the space ship.
     */
    public Player getMyPlayer() {
        for(Player player : players){
            if(player.getID().compareTo(Arena.getInstance().getmPlayerID()) == 0){
                return player;
            }
        }return null;
    }

    public Player getOtherPlayer() {
        for(Player player : players){
            if(player.getID().compareTo(Arena.getInstance().getmPlayerID()) != 0){
                return player;
            }
        }return null;
    }

    public BulletModel createBullet(ShipModel ship) {
        BulletModel bullet = (BulletModel) PoolManager.getInstance().obtain(EntityModel.ModelType.BULLET);
        bullet.setPosition(
                ship.getX() + (float) Math.sin(ship.getRotation()) * 2f,
                ship.getY() + (float) Math.cos(ship.getRotation()) * 2f);
        entityModels.add(bullet);
        return bullet;
    }

    public List<EntityModel> getEntityModels(){
        return entityModels;
    }

    public void addEnemy(MovingObjectModel newEnemy){
        newEnemy.setRotation((float) Math.PI);
        entityModels.add(newEnemy);
    }

    public void addEnemies(ArrayList <MovingObjectModel> newEnemies){
        for (MovingObjectModel newEnemy : newEnemies)
            this.addEnemy(newEnemy);
    }

    public void deleteEntityModel(EntityModel model){
        if(model != null) {
            entityModels.remove(model);
            if(model instanceof BulletModel){
                PoolManager.getInstance().free((BulletModel)model);
            }else {
                model.setFlaggedForRemoval(false);
                EnemiesFactory.getInstance().getPoolManager().free(model);
            }
        }
    }

    public void deleteEntitiesModel(ArrayList<EntityModel> models){
        for(EntityModel model : models) {
            if (model != null)
                entityModels.remove(model);
        }
    }

    public void updatePlayerCoords(ShipModel playerUpdated, String senderParticipantId) {
        Physics_Controller.getInstance().getAirPlane2().setTransform(
                playerUpdated.getX(), playerUpdated.getY(), Physics_Controller.getInstance().getAirPlane2().getBody().getAngle());
    }


    public static void clearInstance(){
        instance = null;
    }

    public void updateModel(ArrayList<Broadcast.StructToSend> modelsReceived) {
        for (int i = 0; i < entityModels.size(); i++) {
            if(getMyPlayer().getMyShip() != entityModels.get(i)) {
                entityModels.remove(i);
                i--;
            }
        }
        for (int i = 0; i < modelsReceived.size(); i++)
        entityModels.add(PoolManager.getInstance().obtain(modelsReceived.get(i).type, modelsReceived.get(i).x, modelsReceived.get(i).y));
    }

}