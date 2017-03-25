package dkeep.logic.maps;

import java.security.InvalidParameterException;

public class KeepMap extends GameMap{
	private static char[][] mapEdited;

	public KeepMap() {
		map = new char[][] {
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
			{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
	}
	@Override
	public char [][] getMap(){
		if(mapEdited != null){
			return mapEdited;
		}
		return map;
	}

	@Override
	public GameMap nextMap() {
		return null;
	}

	public static void setMapEdited(char[][] map){
		mapEdited = map;
	}
	public void copyMap(char [][] tempMap){
		for (int i = 0; i < map.length; i++){
			if (i>=tempMap.length){
				break;
			}
			for (int j = 0; j < map[0].length;j++){
				if (j >= tempMap[0].length){
					break;
				}
				tempMap[i][j] = map[i][j];
			}
		}
	}

	public void resize(int newXSize, int newYSize) {
		char [][] tempMap = new char[newYSize][newXSize];
		copyMap(tempMap);
		for (int i = 0; i < newYSize; i++) {
			for (int j = 0; j < newXSize; j++) {
				if (map[i][j] != 0){
					if (i == 0 || i == newYSize - 1 || j == 0 || j == newXSize - 1) {
						map[i][j] = 'X';
					} else {
						map[i][j] = ' ';
					}
				}
			}
		}
		map = tempMap;
	}

	public void editPos(int x, int y, char n) {
		if (y > map.length || y < 0 || x < 0 || x > map[0].length) {
			throw new InvalidParameterException();
		}
		map[x][y] = n;
	}

}
