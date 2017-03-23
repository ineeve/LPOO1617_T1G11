package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
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



public class CreateMapPanel extends JPanel{

	EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel();
	JPanel componentsPanel;
	JButton heroBtn = new JButton();
	JButton wallBtn = new JButton();
	JButton ogreBtn = new JButton();
	JButton keyBtn = new JButton();
	//public enum icon {HERO,WALL,OGRE,KEY};
	//public static icon IconSelected;
	final int EastPanelSize = 200;
	HashMap<Character,Image> imageMap = new HashMap<Character,Image>();

	CreateMapPanel(){
		init();
	}

	public void loadImages(){
		ReadImages r1 = new ReadImages();
		imageMap = r1.getImageMap();
	}



	public void init(){
		loadImages();
		componentsPanel = new ImageOptionsPanel(imageMap);
		setLayout(new BorderLayout());
		
		componentsPanel.setPreferredSize(new Dimension(EastPanelSize,EastPanelSize));
		add(componentsPanel,BorderLayout.EAST);
		add(editPanel,BorderLayout.CENTER);
	}


}
