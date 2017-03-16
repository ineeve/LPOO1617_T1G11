package dkeep.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class SimpleGraphicsPanel extends JPanel  implements MouseListener, MouseMotionListener, KeyListener { 
	// Coordinates of the elipse “bounding rectangle”
	private int x1 = 0, y1 = 0, x2 = 0, y2 = 0; 
	// Constructor, adding mouse and keyboard listeneres 
	public SimpleGraphicsPanel() { 
		addMouseListener(this); 
		addMouseMotionListener(this); 
		addKeyListener(this); 
	}

	// Redraws the panel, only when requested by SWING
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); // limpa fundo ... 
		g.setColor(Color.BLUE); 
		g.fillOval(x1, y1, x2 - x1 + 1, y2 - y1 + 1); 
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
}
