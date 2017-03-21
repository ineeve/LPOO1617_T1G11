package dkeep.gui;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;


public class SimpleGraphicsPanel extends JPanel  implements MouseListener, MouseMotionListener, KeyListener { 

	char map[][] = null;
	int currentX = 0;
	int currentY = 0;
	int x1 = 0, x2, y1= 0, y2= 0;
	int imagesSize = 64;

	BufferedImage wall = null;
	BufferedImage door = null;
	BufferedImage guard = null;
	BufferedImage hero = null;
	BufferedImage floor = null;
	BufferedImage key = null;
	BufferedImage lever = null;
	BufferedImage ogre = null;
	BufferedImage stairs = null;
	BufferedImage defaultImg = null;
	// Constructor, adding mouse and keyboard listeneres 
	public SimpleGraphicsPanel() {
		setBackground(Color.black);
		addMouseListener(this); 
		addMouseMotionListener(this); 
		addKeyListener(this);
		init();
	}

	public void setMap(char [][] matrix){
		map = matrix;
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
			defaultImg = ImageIO.read(new File("src/assets/default.png"));
			defaultImg = getScaledImage(defaultImg,imagesSize,imagesSize);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Redraws the panel, only when requested by SWING
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); // cleans background 
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
				case 'D':
					g.drawImage(guard, currentX, currentY, null);
					break;
				case 'G':
					g.drawImage(guard, currentX, currentY, null);
					break;
				case 'R':
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




	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){ 
		case KeyEvent.VK_LEFT: x1--; x2--; repaint(); break; 
		case KeyEvent.VK_RIGHT: x1++; x2++; repaint(); break;  
		case KeyEvent.VK_UP: y1--; y2--; repaint(); break; 
		case KeyEvent.VK_DOWN: y1++; y2++; repaint(); break; 
		}

	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

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
}
