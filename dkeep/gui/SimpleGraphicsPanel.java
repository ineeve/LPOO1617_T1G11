package dkeep.gui;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;

import dkeep.logic.Configs;
import dkeep.logic.Game;

public class SimpleGraphicsPanel extends JPanel { 

	Game game;
	char[][] map = null;
	int currentX = 0;
	int currentY = 0;
	int x1 = 0, x2, y1= 0, y2= 0;
	Dimension imageSize = new Dimension(100,100);

	BufferedImage wall = null;
	BufferedImage door = null;
	BufferedImage guard = null;
	BufferedImage hero = null;
	BufferedImage heroWithKey = null;
	BufferedImage floor = null;
	BufferedImage key = null;
	BufferedImage lever = null;
	BufferedImage ogre = null;
	BufferedImage stairs = null;
	BufferedImage club = null;
	BufferedImage ogreStunned = null;
	BufferedImage defaultImg = null;
	// Constructor, adding mouse and keyboard listeneres 
	public SimpleGraphicsPanel() {
		setBackground(Color.black);
		init();
	}

	public void setGame(Game g){
		game = g;
		map = game.getMap();
		repaint();
	}

	
	public void rescaleImages(){
		wall = getScaledImage(wall,imageSize.width,imageSize.height);
		door = getScaledImage(door,imageSize.width,imageSize.height);
		hero = getScaledImage(hero,imageSize.width,imageSize.height);
		heroWithKey = getScaledImage(heroWithKey,imageSize.width,imageSize.height);
		guard = getScaledImage(guard,imageSize.width,imageSize.height);
		key = getScaledImage(key,imageSize.width,imageSize.height);
		lever = getScaledImage(lever,imageSize.width,imageSize.height);
		ogre = getScaledImage(ogre,imageSize.width,imageSize.height);
		stairs = getScaledImage(stairs,imageSize.width,imageSize.height);
		floor = getScaledImage(floor,imageSize.width,imageSize.height);
		club = getScaledImage(club,imageSize.width,imageSize.height);
		ogreStunned = getScaledImage(ogreStunned,imageSize.width,imageSize.height);
		defaultImg = getScaledImage(defaultImg,imageSize.width,imageSize.height);
	}

	private void init(){
		
		setPreferredSize(new Dimension(500,500));
		try {
			wall = ImageIO.read(new File("src/assets/Horizontal_Wall.png"));
			
			door = ImageIO.read(new File("src/assets/door.png"));
			
			hero = ImageIO.read(new File("src/assets/Hero.png"));
			
			heroWithKey = ImageIO.read(new File("src/assets/HeroWithKey.png"));
			guard = ImageIO.read(new File("src/assets/drunken.png"));
			key = ImageIO.read(new File("src/assets/key.png"));
			lever = ImageIO.read(new File("src/assets/lever.png"));
			ogre = ImageIO.read(new File("src/assets/Ogre.png"));
			stairs = ImageIO.read(new File("src/assets/stairs.png"));
			floor = ImageIO.read(new File("src/assets/floor.png"));
			club = ImageIO.read(new File("src/assets/club.png"));
			ogreStunned= ImageIO.read(new File("src/assets/OgreStunned.png"));
			defaultImg = ImageIO.read(new File("src/assets/default.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		rescaleImages();
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
					currentX += imageSize.width;
				}
				currentX = 0;
				currentY+=imageSize.height;
			}
			currentY = 0;
		}
	}




	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * @param srcImg - source image to scale
	 * @param w - desired width
	 * @param h - desired height
	 * @return - the new resized image
	 */
	private BufferedImage getScaledImage(BufferedImage src, int w, int h){
		int finalw = w;
		int finalh = h;
		double factor = 1.0d;
		if(src.getWidth() > src.getHeight()){
			factor = ((double)src.getHeight()/(double)src.getWidth());
			finalh = (int)(finalw * factor);                
		}else{
			factor = ((double)src.getWidth()/(double)src.getHeight());
			finalw = (int)(finalh * factor);
		}   

		BufferedImage resizedImg = new BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(src, 0, 0, finalw, finalh, null);
		g2.dispose();
		return resizedImg;
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
