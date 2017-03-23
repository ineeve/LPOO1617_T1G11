package dkeep.gui;

import dkeep.logic.maps.KeepMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;


class CreateMapPanel extends JPanel{

	private EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel();
	private HashMap<Character,Image> imageMap = new HashMap<>();

	CreateMapPanel(){
		init();
	}

	private void loadImages(){
		imageMap = readImages();
	}

	private void init(){
		loadImages();
		JPanel componentsPanel = new ImageOptionsPanel(imageMap);
		setLayout(new BorderLayout());

		int eastPanelSize = 200;
		componentsPanel.setPreferredSize(new Dimension(eastPanelSize, eastPanelSize));
		add(componentsPanel,BorderLayout.EAST);
		add(editPanel,BorderLayout.CENTER);
	}

	public void saveMapEdited(){
		KeepMap.setMapEdited(editPanel.getMapEdited());
	}

}
