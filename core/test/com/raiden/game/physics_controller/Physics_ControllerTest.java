package com.raiden.game.physics_controller;

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

    }

}