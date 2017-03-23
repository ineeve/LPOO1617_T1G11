package dkeep.gui;

import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.KeepMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static dkeep.gui.Read.*;

public class EditMapGraphicsPanel extends JPanel{

	GameMap keepLevel = new KeepMap();
	char [][] map = keepLevel.getMap();
	GridLayout gl = new GridLayout(map.length,map[0].length);
	HashMap<Character,Image> imageMap;
	
	EditMapGraphicsPanel(){
		init();
	}
	public void init(){
		getImages();
		setLayout(gl);
		addButtons();
	}
	private void getImages() {
		imageMap = readImages();
	}
	public void addButtons(){
		for (int i = 0; i < gl.getRows();i++){
			for (int j = 0; j < gl.getColumns(); j++){
				SpecialButton j1 = new SpecialButton(new Dimension(i,j),imageMap.get(map[i][j]), map[i][j]);
				j1.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
					map[j1.getPosition().width][j1.getPosition().height] = ImageOptionsPanel.buttonPressed;
					j1.setImage(imageMap.get(ImageOptionsPanel.buttonPressed));
					}});
				add(j1);
			}
		}
	}


}
