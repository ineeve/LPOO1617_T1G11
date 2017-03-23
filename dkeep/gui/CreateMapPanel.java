package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;



public class CreateMapPanel extends JPanel implements MouseListener {

	EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel();
	JPanel componentsPanel;
	JButton heroBtn = new JButton();
	JButton wallBtn = new JButton();
	JButton ogreBtn = new JButton();
	JButton keyBtn = new JButton();
	//public enum icon {HERO,WALL,OGRE,KEY};
	//public static icon IconSelected;
	final int EastPanelSize = 200;
	HashMap<Character,ImageIcon> imageMap = new HashMap<Character,ImageIcon>();

	CreateMapPanel(){
		init();
	}

	public void loadImages(){
		try {
			imageMap.put('H', new ImageIcon(ImageIO.read(new File("src/assets/Hero.png"))));
			imageMap.put('X', new ImageIcon(ImageIO.read(new File("src/assets/Horizontal_Wall.png"))));
			imageMap.put('O', new ImageIcon(ImageIO.read(new File("src/assets/Ogre.png"))));
			imageMap.put('k', new ImageIcon(ImageIO.read(new File("src/assets/key.png"))));
			imageMap.put(' ', new ImageIcon(ImageIO.read(new File("src/assets/floor.png"))));
			imageMap.put('I', new ImageIcon(ImageIO.read(new File("src/assets/door.png"))));
			
		} catch (IOException e) {
			System.out.println("Error reading error image");
		}
	}



	public void init(){
		loadImages();
		componentsPanel = new ImageOptionsPanel(imageMap);
		//IconSelected = icon.HERO;
		setLayout(new BorderLayout());
		
		componentsPanel.setPreferredSize(new Dimension(EastPanelSize,EastPanelSize));
		add(componentsPanel,BorderLayout.EAST);
		add(editPanel,BorderLayout.CENTER);
		

		heroBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//	IconSelected = icon.HERO;
			}
		});
		wallBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//IconSelected = icon.WALL;

			}
		});
		ogreBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//IconSelected = icon.WALL;
			}});
		keyBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//IconSelected = icon.KEY;
			}});
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {

		}

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
