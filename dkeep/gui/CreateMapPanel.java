package dkeep.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;


public class CreateMapPanel extends JPanel{

	EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel();
	JPanel componentsPanel;
	//public enum icon {HERO,WALL,OGRE,KEY};
	//public static icon IconSelected;
	final int EastPanelSize = 200;
	HashMap<Character,Image> imageMap = new HashMap<>();

	CreateMapPanel(){
		init();
	}

	public void loadImages(){
		imageMap = readImages();
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
