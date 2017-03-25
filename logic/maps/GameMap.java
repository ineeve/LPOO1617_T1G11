package dkeep.logic.maps;


import java.awt.*;


public abstract class GameMap {
	/** An array with arrays of chars representative of the map. Should only contain Walls('X') and Doors('I') */
	protected char[][] map;

	/**
	 * The map returned by this method contains only Walls and Doors
	 * @return An array with arrays of chars.
	 */
	public char[][] getMap() {
		return map;
	}

	/**
	 * isFree checks if a given Point in the map is free to move into.
	 * This method doesn't check if there are Moving Agents in the cell neither Weapons,Keys or Levers,
	 * as they are not stored in the map.
	 * @param coord Coordinates to check if they are free.
	 * @return 0-Out of map; 1 - ' '; 2 - 'S',3-'I';
	 */
	public int isFree(Point coord) {
		if (coord.y >= map.length || coord.y < 0) {
			return 0;
		} else if (coord.x >= map[coord.y].length || coord.x < 0) {
			return 0;
		} else if ('X' == map[coord.y][coord.x]) {
			return 0;
		} else if ('I' == map[coord.y][coord.x]) {
			return 3;
		} else if (' ' == map[coord.y][coord.x]) {
			return 1;
		} else if ('S' == map[coord.y][coord.x]) {
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * Abstract Method to return the next Map.
	 * @return Reference to the Next Map
	 */
	public abstract GameMap nextMap();
	
	/**
	 * Changes the map, so that all doors passed as parameter are changed to stairs.
	 * @param doors An array of Points with the Position of the Doors to Be Changed To Stairs
	 */
	public void changeDoorsToStairs(Point[] doors) {
		for (int i = 0; i < doors.length;i++){
			map[doors[i].y][doors[i].x] = 'S';
		}
	}




}

