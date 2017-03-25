package dkeep.logic;

import java.awt.Point;

public class Lever {
	private Point coords;
	private Point[] doors;
	public Lever(Point coords, Point[] doors) {
		super();
		this.coords = coords;
		this.doors = doors;
	}
	public Point getCoord() {
		return coords;
	}
	public Point[] getDoors() {
		return doors;
	}
	public void setCoords(Point coords){
		this.coords = coords;
	}
	
	
}
