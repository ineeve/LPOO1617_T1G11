package dkeep.gui;


import dkeep.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;

public class BoardGraphics extends JPanel {

	Game game;
	char[][] map = null;
	int currentX = 0;
	int currentY = 0;
	public static final int WIDTHDIMENSION = 500;
    public static final int HEIGHTDIMENSION = 500;
	HashMap<Character, Image> imageMap;


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
		if (game.moveHero(heroDirection)==1){ //change To next level
			game.resetLevel();
			map = game.getMap();
			repaint();
			return 0;
		}
		map = game.getMap();
		repaint();
		int gameStatus;
		if ((gameStatus = checkForGameOver()) > 0 ){
			return gameStatus; 
		}
		else{
			game.moveBots();
			map = game.getMap();
			repaint();
		}
		if ((gameStatus = checkForGameOver()) > 0 ){
			return gameStatus;
		}
		return 0;
	}

	// Redraws the panel, only when requested by SWING
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); // cleans background
		int widthScale = this.getWidth()/map.length;
		int heightScale = this.getHeight()/map[0].length;
		imageMap = readImages();
		if (map != null){
			for (int i = 0; i < map.length; i++){
				for (int j = 0; j < map[i].length;j++){
					Image imageToDraw = imageMap.get(map[i][j]);
						g.drawImage(imageToDraw, currentX, currentY, widthScale, heightScale , null);
					currentX += widthScale;
				}
				currentX = 0;
				currentY += heightScale;
			}
			currentY = 0;
		}
	}

	public int checkForGameOver(){
		game.isGameOver(); //To update status value in game object.
		if (game.getGameStatus() == Game.status.DEFEAT){
			return 1;
		}
		else if (game.getGameStatus() == Game.status.VICTORY){
			return 2;
		}
		return 0;
	}




}
