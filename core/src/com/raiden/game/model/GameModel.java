package com.raiden.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.raiden.game.Arena;
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
    protected ArrayList<EntityModel> entityModels = new ArrayList<EntityModel>(0);

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


    public BulletModel createBullet(ShipModel ship) {
        BulletModel bullet = (BulletModel) PoolManager.getInstance().obtain(EntityModel.ModelType.BULLET);
        bullet.setPosition(
                ship.getX() + (float) Math.sin(ship.getRotation()) * 2f,
                ship.getY() + (float) Math.cos(ship.getRotation()) * 2f);
        entityModels.add(bullet);
        return bullet;
    }

    public ArrayList<EntityModel> getEntityModels(){
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
        for (Player player : players){
            if(player.getID().compareTo(senderParticipantId) == 0){
                Body ship = Physics_Controller.getInstance().getBodyByModel(player.getMyShip());
                ship.setTransform(playerUpdated.getX(),playerUpdated.getY(), ship.getAngle());
                return;
            }
        }
    }


    public static void clearInstance(){
        instance = null;
    }

    public void updateModel(GameModel modelReceived) {
        for(int i = 0; i < modelReceived.getEntityModels().size(); i++){
            if(i < entityModels.size()){
                if(entityModels.get(i).getID() == modelReceived.getEntityModels().get(i).getID()){
                    entityModels.get(i).setPosition(modelReceived.getEntityModels().get(i).getX(), modelReceived.getEntityModels().get(i).getY());
                }
                else if (entityModels.get(i).getID() < modelReceived.getEntityModels().get(i).getID()){
                    entityModels.remove(i);
                }
                else {
                    entityModels.add(i, modelReceived.getEntityModels().get(i));
                }
            }
            else {
                entityModels.add(modelReceived.getEntityModels().get(i));
            }
        }
    }
}