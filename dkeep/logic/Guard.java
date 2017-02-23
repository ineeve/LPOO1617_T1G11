package dkeep.logic;

public class Guard extends MovingAgent {
    private static char [] path = new char []{'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	private static int pathIterator = 0;

    public Guard() {
    }

    public static char[] getPath() {
        return path;
    }

    public static int getPathIterator() {
        return pathIterator;
    }

    public static void setPath(char[] path) {
        Guard.path = path;
    }

    public static void setPathIterator(int pathIterator) {
        Guard.pathIterator = pathIterator;
    }
}
