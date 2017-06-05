package com.raiden.game.physics_controller;

import com.badlogic.gdx.Screen;
import com.raiden.game.model.entities.Airplane_1_Model;
import com.raiden.game.physics_controller.entities.AirPlane_1;
import com.raiden.game.screen.PVE_Screen;

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
        Airplane_1_Model airplaneModel = new Airplane_1_Model(-1f,0f);
        AirPlane_1 airplaneBody = new AirPlane_1(controller.getWorld(),airplaneModel);
        controller.verifyBounds(airplaneBody.getBody(),true);
        assert(airplaneModel.getX() >= 0);
    }

}