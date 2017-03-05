/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkeep.logic.maps;

import dkeep.logic.Guard;
import dkeep.logic.Hero;
import dkeep.logic.Rookie;
import java.awt.Point;

/**
 *
 * @author ineeve
 */
public class Task1TestMap extends GameMap{
    public Task1TestMap(char [][]map){
        this.map = map;
        agents.add(new Hero(new Point(1,1)));
        agents.add(new Rookie(new Point(3,1)));
    }

    @Override
    public GameMap nextMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
