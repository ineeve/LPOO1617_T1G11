package dkeep.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dkeep.logic.Configs;
import dkeep.logic.Game;


public class SimpleGraphicsPanel extends JPanel  implements MouseListener, MouseMotionListener, KeyListener { 

	Game game = new Game();
	Configs config = null;
	char[][] map = null;
	int currentX = 0;
	int currentY = 0;
	int x1 = 0, x2, y1= 0, y2= 0;
	int imagesSize = 64;

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
		addMouseListener(this); 
		addMouseMotionListener(this); 
		addKeyListener(this);
		init();
	}

	public void setConfig(Configs conf){
		config = conf;
		game.setMap(config.getMap());
        game.setAgents(config.getAgents());
        game.setKey(config.getKey());
        game.setKeyTaken(false);
        game.gameStatus = Game.status.PLAYING;
		map = game.getMap();
		repaint();
	}

	private void init(){
		setPreferredSize(new Dimension(400,400));
		try {
			wall = ImageIO.read(new File("src/assets/Horizontal_Wall.png"));
			wall = getScaledImage(wall,imagesSize,imagesSize);
			door = ImageIO.read(new File("src/assets/door.png"));
			door = getScaledImage(door,imagesSize,imagesSize);
			hero = ImageIO.read(new File("src/assets/Hero.png"));
			hero = getScaledImage(hero,imagesSize,imagesSize);
			heroWithKey = ImageIO.read(new File("src/assets/HeroWithKey.png"));
			heroWithKey = getScaledImage(heroWithKey,imagesSize,imagesSize);
			guard = ImageIO.read(new File("src/assets/drunken.png"));
			guard = getScaledImage(guard,imagesSize,imagesSize);
			key = ImageIO.read(new File("src/assets/key.png"));
			key = getScaledImage(key,imagesSize,imagesSize);
			lever = ImageIO.read(new File("src/assets/lever.png"));
			lever = getScaledImage(lever,imagesSize,imagesSize);
			ogre = ImageIO.read(new File("src/assets/Ogre.png"));
			ogre = getScaledImage(ogre,imagesSize,imagesSize);
			stairs = ImageIO.read(new File("src/assets/stairs.png"));
			stairs = getScaledImage(stairs,imagesSize,imagesSize);
			floor = ImageIO.read(new File("src/assets/floor.png"));
			floor = getScaledImage(floor,imagesSize,imagesSize);
			club = ImageIO.read(new File("src/assets/club.png"));
			club = getScaledImage(club,imagesSize,imagesSize);
			ogreStunned= ImageIO.read(new File("src/assets/OgreStunned.png"));
			ogreStunned = getScaledImage(ogreStunned,imagesSize,imagesSize);
			defaultImg = ImageIO.read(new File("src/assets/default.png"));
			defaultImg = getScaledImage(defaultImg,imagesSize,imagesSize);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int moveAgents_GUI(char heroDirection){

		if (game.moveHero(heroDirection)==1){ //change To next level
			config.prepareNextLevel();
			game.setMap(config.getMap());
			game.setAgents(config.getAgents());
			game.setKey(config.getKey());
			game.setKeyTaken(false);
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
				currentX += imagesSize;
			}
			currentX = 0;
			currentY+=imagesSize;
		}
		currentY = 0;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){ 
		case KeyEvent.VK_LEFT: moveAgents_GUI('a'); break; 
		case KeyEvent.VK_RIGHT:moveAgents_GUI('d'); break;  
		case KeyEvent.VK_UP: moveAgents_GUI('w');break; 
		case KeyEvent.VK_DOWN: moveAgents_GUI('s'); break; 
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		

	}
	@Override
	public void keyTyped(KeyEvent e) {
		

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();  
		y2 = e.getY();  
		repaint();

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		x2 = x1 = e.getX();  
		y2 = y1 = e.getY(); 
		repaint();

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
