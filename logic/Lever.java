package dkeep.logic;

import java.awt.Point;

public class Lever {
	private Point coords;
	private Point[] doors;
	private char notActivatedSymbol = 'k';
	private char activatedSymbol = 'K';
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
	public char getNotActivatedSymbol(){
		return notActivatedSymbol;
	}
	public char getActivatedSymbol() {
		return activatedSymbol;
	}
	
	
}
