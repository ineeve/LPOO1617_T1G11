package dkeep.logic.maps;


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
    
}
