package dkeep.logic.maps;

import java.security.InvalidParameterException;

public class KeepMap extends GameMap{

	private static char[][] mapStatic = new char[][] {
		{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
		{ 'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
		{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
		{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
		{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
		{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
		{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
		{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
		{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		public KeepMap() {
			super.map = KeepMap.mapStatic;
		}

		@Override
		public GameMap nextMap() {
			return null;
		}
		public static void copyMap(char [][] tempMap){
			for (int i = 0; i < mapStatic.length; i++){
				if (i>=tempMap.length){
					break;
				}
				for (int j = 0; j < mapStatic[0].length;j++){
					if (j >= tempMap[0].length){
						break;
					}
					tempMap[i][j] = mapStatic[i][j];
				}
			}
		}

		public static void resize(int newXSize, int newYSize) {
			if (newXSize == mapStatic[0].length && newYSize == mapStatic.length){
				return;
			}
			char [][] tempMap = new char[newYSize][newXSize];
			copyMap(tempMap);
			for (int i = 0; i < newYSize; i++) {
				for (int j = 0; j < newXSize; j++) {
					if (tempMap[i][j] == 0){
						tempMap[i][j] = ' ';
					}
				}
			}
			mapStatic = tempMap;
		}

		public void editPos(int x, int y, char n) {
			if (y > map.length || y < 0 || x < 0 || x > map[0].length) {
				throw new InvalidParameterException();
			}
			map[x][y] = n;
		}

}
