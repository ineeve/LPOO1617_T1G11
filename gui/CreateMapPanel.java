package dkeep.gui;

import dkeep.logic.maps.KeepMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;


class CreateMapPanel extends JPanel{
	private KeepMap keepLevel = new KeepMap();
	private EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel(keepLevel);
	private HashMap<Character,Image> imageMap = new HashMap<>();
	private MapSizeSelectorPanel mapSizePanel = new MapSizeSelectorPanel(keepLevel);
	
	CreateMapPanel(){
		init();
	}

	private void loadImages(){
		imageMap = readImages();
	}

	private void init(){
		loadImages();
		JPanel componentsPanel = new ImageOptionsPanel(imageMap);
		componentsPanel.setBorder(new EmptyBorder(0, 2, 2, 2));
		setLayout(new BorderLayout());
		int eastPanelSize = 200;
		componentsPanel.setPreferredSize(new Dimension(eastPanelSize, eastPanelSize));
		add(componentsPanel,BorderLayout.EAST);
		mapSizePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(mapSizePanel,BorderLayout.NORTH);
		editPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(editPanel,BorderLayout.CENTER);
		
		
	}

	public void saveMapEdited(){
		KeepMap.setMapEdited(editPanel.getMapEdited());
	}

}
