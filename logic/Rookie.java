package dkeep.logic;

import java.awt.*;

class Rookie extends Guard {
    /**Constant char that contains the symbol of MovingAgent*/
    private final char ROOKIESYMBOL = 'G';

    /* CONSTRUCTOR */

    /** Constructor of Rookie;
     *  Initialize Rookie and set your's start position;
     *
     * @param coord - Point to set your's start position;
     */
    public Rookie(Point coord) {
        symbol = ROOKIESYMBOL;
        agentCoords = coord;
    }
}
