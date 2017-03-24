package dkeep.gui;


import dkeep.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;

class BoardGraphics extends JPanel {

	private Game game;
	private char[][] map = null;
	private int currentX = 0;
	private int currentY = 0;
	private static final int WIDTHDIMENSION = 500;
    private static final int HEIGHTDIMENSION = 500;
	private HashMap<Character, Image> imageMap;


	// Constructor, adding mouse and keyboard listeneres 
	public BoardGraphics() {
		setBackground(Color.black);
		init();
	}

	public void setGame(Game g){
		game = g;
		map = game.getMap();
		repaint();
	}

	private void init(){
		setMinimumSize(new Dimension(WIDTHDIMENSION,HEIGHTDIMENSION));
		setPreferredSize(new Dimension(WIDTHDIMENSION,HEIGHTDIMENSION));
        imageMap = readImages();
	}

	public int moveAgents_GUI(char heroDirection){
		int gameStatus = game.moveAllAgents(heroDirection);
		if(gameStatus == 1){
			game.resetLevel();
			gameStatus = 0;
		}
		map = game.getMap();
		repaint();
		return gameStatus;
	}

	// Redraws the panel, only when requested by SWING
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); // cleans background
		int widthScale = this.getWidth()/map.length;
		int heightScale = this.getHeight()/map[0].length;
		imageMap = readImages();
		if (map != null){
			for (char[] aMap : map) {
				for (char anAMap : aMap) {
					Image imageToDraw = imageMap.get(anAMap);
					g.drawImage(imageToDraw, currentX, currentY, widthScale, heightScale, null);
					currentX += widthScale;
				}
				currentX = 0;
				currentY += heightScale;
			}
			currentY = 0;
		}
	}
}
