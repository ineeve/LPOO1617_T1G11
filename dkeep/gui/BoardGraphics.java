package dkeep.gui;


import dkeep.logic.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BoardGraphics extends JPanel {

	Game game;
	char[][] map = null;
	int currentX = 0;
	int currentY = 0;
	public static final int WIDTHDIMENSION = 500;
    public static final int HEIGHTDIMENSION = 500;
	Image wall = null;
	Image door = null;
	Image guard = null;
	Image hero = null;
	Image heroWithKey = null;
	Image floor = null;
	Image key = null;
	Image lever = null;
	Image ogre = null;
	Image stairs = null;
	Image club = null;
	Image ogreStunned = null;
	Image defaultImg = null;
	HashMap<Character, Image> imageMap = new HashMap<>();
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

	private void readImage(){
		try {
			wall = ImageIO.read(new File("src/assets/wall.png"));
			imageMap.put('X', wall);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			door = ImageIO.read(new File("src/assets/door.png"));
			imageMap.put('I', door);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try	{
			hero = ImageIO.read(new File("src/assets/Hero.png"));
			imageMap.put('H', hero);
			imageMap.put('A', hero);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			heroWithKey = ImageIO.read(new File("src/assets/HeroWithKey.png"));
			imageMap.put('K', hero);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			guard = ImageIO.read(new File("src/assets/drunken.png"));
			imageMap.put('G', guard);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			key = ImageIO.read(new File("src/assets/key.png"));
			imageMap.put('k', key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			lever = ImageIO.read(new File("src/assets/lever.png"));
			imageMap.put('L', lever);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ogre = ImageIO.read(new File("src/assets/Ogre.png"));
			imageMap.put('O', ogre);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stairs = ImageIO.read(new File("src/assets/stairs.png"));
			imageMap.put('S', stairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			floor = ImageIO.read(new File("src/assets/floor.png"));
			imageMap.put(' ', floor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			club = ImageIO.read(new File("src/assets/club.png"));
			imageMap.put('*', club);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ogreStunned= ImageIO.read(new File("src/assets/OgreStunned.png"));
			imageMap.put('8', ogreStunned);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			defaultImg = ImageIO.read(new File("src/assets/default.png"));
			imageMap.put('/', defaultImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init(){
		setMinimumSize(new Dimension(WIDTHDIMENSION,HEIGHTDIMENSION));
		setPreferredSize(new Dimension(WIDTHDIMENSION,HEIGHTDIMENSION));
        readImage();
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
		readImage();
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
