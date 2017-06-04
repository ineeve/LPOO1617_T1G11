package com.raiden.game.physics_controller;

import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.model.entities.EntityModel;
import com.raiden.game.model.entities.ShipModel;
import com.raiden.game.physics_controller.entities.AirPlane_1;
import com.raiden.game.physics_controller.entities.ShipBody;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ineeve on 04-06-2017.
 */
public class Physics_ControllerTest extends GameTest {
    @Test
    public void testBodyPosition(){
        Physics_Controller controller = Physics_Controller.getInstance();
        assertNotNull(controller);
        ShipModel shipModel = new Airplane_1_Model(10,10);
        ShipBody shipBody = new AirPlane_1(controller.getWorld(),shipModel);

    }

}