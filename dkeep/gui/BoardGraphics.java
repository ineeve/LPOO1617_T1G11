package dkeep.gui;


import dkeep.logic.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

	
	public void rescaleImages(){
        readImage();
		wall = wall.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_FAST);
		door = door.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		hero = hero.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		heroWithKey = heroWithKey.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		guard =  guard.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		key = key.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		lever = lever.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		ogre = ogre.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		stairs = stairs.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		floor = floor.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		club = club.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		ogreStunned = ogreStunned.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
		defaultImg = defaultImg.getScaledInstance((int) (this.getWidth()*0.1),(int) (this.getHeight()*0.1), Image.SCALE_SMOOTH);
	}

	private void readImage(){
		try {
			wall = ImageIO.read(new File("src/assets/wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			door = ImageIO.read(new File("src/assets/door.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try	{
			hero = ImageIO.read(new File("src/assets/Hero.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			heroWithKey = ImageIO.read(new File("src/assets/HeroWithKey.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			guard = ImageIO.read(new File("src/assets/drunken.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{
			key = ImageIO.read(new File("src/assets/key.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			lever = ImageIO.read(new File("src/assets/lever.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ogre = ImageIO.read(new File("src/assets/Ogre.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stairs = ImageIO.read(new File("src/assets/stairs.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			floor = ImageIO.read(new File("src/assets/floor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			club = ImageIO.read(new File("src/assets/club.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ogreStunned= ImageIO.read(new File("src/assets/OgreStunned.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			defaultImg = ImageIO.read(new File("src/assets/default.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init(){
		setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
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
        rescaleImages();
		if (map != null){
			for (int i = 0; i < map.length; i++){
				for (int j = 0; j < map[i].length;j++){
					switch (map[i][j]){
					case 'X':
						g.drawImage(wall, currentX, currentY, null);
						break;
					case ' ':
						g.drawImage(floor, currentX, currentY, null);
						break;
					case 'H':
						g.drawImage(hero, currentX, currentY, null);
						break;
					case 'A':
						g.drawImage(hero, currentX, currentY, null);
						break;
					case 'K':
						g.drawImage(heroWithKey, currentX, currentY, null);
						break;
					case '*':
						g.drawImage(club, currentX, currentY, null);
						break;
					case '8':
						g.drawImage(ogreStunned, currentX, currentY, null);
						break;
					case 'G':
						g.drawImage(guard, currentX, currentY, null);
						break;
					case 'S':
						g.drawImage(stairs, currentX, currentY, null);
						break;
					case 'k':
						g.drawImage(key, currentX, currentY, null);
						break;
					case 'O':
						g.drawImage(ogre, currentX, currentY, null);
						break;
					case 'I':
						g.drawImage(door, currentX, currentY, null);
						break;
					default:
						g.drawImage(defaultImg, currentX, currentY, null);
						break;
					}
					currentX += wall.getWidth(null);
				}
				currentX = 0;
				currentY += wall.getHeight(null);
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
