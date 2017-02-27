package dkeep.logic;

import java.awt.Point;

import dkeep.logic.Guard;

public class Rookie extends Guard {
	public Rookie() {
		symbol = 'R';
	}

	public Rookie(Point coord) {
		symbol = 'R';
		agentCoords = coord;
	}
        

}
