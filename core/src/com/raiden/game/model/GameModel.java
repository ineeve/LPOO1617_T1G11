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
 * A model representing a game, contains all the data about the current game.
 */
public class GameModel implements Serializable {

    //Id used for serialization.
    private static final long serialVersionUID = 11L;

    //The actual instance of the game model.
    private static GameModel instance;

    //A list containing all the models
    List<EntityModel> entityModels =
            Collections.synchronizedList(new ArrayList<EntityModel>());

    //A ArrayList of the players playing this game.
    private List<Player> players = Collections.synchronizedList(new ArrayList<Player>());


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

    /**
     * Sets the instance to null.
     */
    public static void clearInstance(){
        instance = null;
    }

    /**
     * @return An array with all the players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param player The player that will join the game.
     * @param x The x coordinate of the player ship in the world.
     * @param y The y coordinate of the player ship in the world.
     */
    public void addPlayer(Player player,float x, float y){
        Airplane_1_Model myShip = new Airplane_1_Model(x,y);
        if((Arena.getInstance().getConfigCore().isHost()
                && Arena.getInstance().getConfigCore().isMultiplayer())
                || !Arena.getInstance().getConfigCore().isMultiplayer())
            myShip.setCanShoot(true);
        player.setShip(myShip);
        synchronized(entityModels) {
            entityModels.add(player.getShip());
        }
        synchronized(players) {
            this.players.add(player);
        }
    }

    /**
     * Adds a list of players, setting their ship positions automatically.
     * @param players The ArrayList of the players to add
     */
    public void addPlayers(ArrayList<Player> players){
        float xStart = (Physics_Controller.ARENA_WIDTH - (players.size() - 1) * 5f) / 2f;
        for(Player player : players){
            addPlayer(player, xStart, 5);
            xStart += 5;
        }
    }

    /**
     * @return the player which is running this instance of the game.
     */
    public Player getMyPlayer() {
        synchronized (players) {
            for (Player player : players) {
                if (player.getID().compareTo(Arena.getInstance().getConfigCore().getmPlayerID()) == 0) {
                    return player;
                }
            }
        }return null;
    }

    /**
     * @return A player that running the game on other device, or null if it does not exist.
     */
    public Player getOtherPlayer() {
        synchronized(players) {
            for (Player player : players) {
                if (player.getID().compareTo(Arena.getInstance().getConfigCore().getmPlayerID()) != 0) {
                    return player;
                }
            }
        }return null;
    }

    /**
     * Creates a bullet, sets its position and assigns it to a ship model.
     * @param ship The ship that will get the bullet.
     * @return The bullet model that was just created.
     */
    public BulletModel createBullet(ShipModel ship) {
        BulletModel bullet = (BulletModel) PoolManager.getInstance().obtain(EntityModel.ModelType.BULLET);
        bullet.setPosition(
                ship.getX() + (float) Math.sin(ship.getRotation()) * 2f,
                ship.getY() + (float) Math.cos(ship.getRotation()) * 2f);
        synchronized(entityModels) {
            entityModels.add(bullet);
        }
        return bullet;
    }

    /**
     *
     * @return A list of all the models in the current game.
     */
    public List<EntityModel> getEntityModels(){
        return entityModels;
    }

    /**
     * Adds an enemy to this game model and sets its rotation to 180º.
     * @param newEnemy The enemy to be added.
     */
    public void addEnemy(MovingObjectModel newEnemy){
        newEnemy.setRotation((float) Math.PI);
        synchronized(entityModels) {
            entityModels.add(newEnemy);
        }
    }

    /**
     * Adds a list of enemies and sets their rotations to 180º.
     * @param newEnemies The list of enemies to be added.
     */
    public void addEnemies(ArrayList <MovingObjectModel> newEnemies){
        for (MovingObjectModel newEnemy : newEnemies)
            this.addEnemy(newEnemy);
    }

    /**
     * Deletes a model from this game.
     * @param model The model to be removed.
     */
    public void deleteEntityModel(EntityModel model){
        if(model != null) {
            if(getMyPlayer().getShip() == model)
                getMyPlayer().setStillPlaying(false);
            if(getOtherPlayer() != null && getOtherPlayer().getShip() == model) {
                getOtherPlayer().setStillPlaying(false);
            }
            synchronized(entityModels) {
                entityModels.remove(model);
            }
            if(model instanceof BulletModel){
                PoolManager.getInstance().free(model);
            }else {
                model.setFlaggedForRemoval(false);
                EnemiesFactory.getInstance().getPoolManager().free(model);
            }
        }
    }

    /**
     * Removes a list of entity models from this game.
     * @param models The models to be removed.
     */
    public void deleteEntitiesModel(ArrayList<EntityModel> models){
        for(EntityModel model : models) {
            if (model != null)
                synchronized(entityModels) {
                    entityModels.remove(model);
                }
        }
    }

    /**
     * Updates the coordinates of the client player.
     * @param playerUpdated A ship model with the updated coordinates.
     */
    public void updatePlayerCoords(ShipModel playerUpdated) {
        Physics_Controller.getInstance().getAirPlane2().setTransform(
                playerUpdated.getX(), playerUpdated.getY(), Physics_Controller.getInstance().getAirPlane2().getBody().getAngle());
    }

    /**
     * Updates the player score, destroies the player his ship died, removes all existing models except the player ship,
     * and adds the new models that are received as argument.
     * @param modelsReceived The new models to add to this game.
     * @param playerScore The last score of the player
     * @param playerIsAlive True if the the player is alive, false otherwise.
     */
    public void updateModel(ArrayList<Broadcast.StructToSend> modelsReceived, int playerScore, boolean playerIsAlive) {
        if(getMyPlayer() != null) {
            getMyPlayer().setScore(playerScore);
            if (!playerIsAlive && playerScore != 0) {
                getMyPlayer().setStillPlaying(false);
                Physics_Controller.getInstance().destroyDynamicBody(Physics_Controller.getInstance().getAirPlane1());
                GameModel.getInstance().deleteEntityModel(GameModel.getInstance().getMyPlayer().getShip());
            }
        }
        synchronized(entityModels) {
            for (int i = 0; i < entityModels.size(); i++) {
                if (getMyPlayer().getShip() != entityModels.get(i)) {
                    entityModels.remove(i);
                    i--;
                }
            }
        }
        for (int i = 0; i < modelsReceived.size(); i++) {
            EntityModel model = PoolManager.getInstance().obtain(modelsReceived.get(i).type, modelsReceived.get(i).x, modelsReceived.get(i).y);
            model.setRotation(modelsReceived.get(i).angle);
            synchronized(entityModels) {
                entityModels.add(model);
            }
        }
    }
}