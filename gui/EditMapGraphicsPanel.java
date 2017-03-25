package dkeep.gui;

import dkeep.logic.maps.KeepMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;

class EditMapGraphicsPanel extends JPanel{
	private char [][] map;
	private GridLayout gl;
	private HashMap<Character,Image> imageMap;
	
	EditMapGraphicsPanel(KeepMap keepLevel){
		map = keepLevel.getMap();
		gl = new GridLayout(map.length,map[0].length);
		init();
	}
	private void init(){
		getImages();
		setLayout(gl);
		addButtons();
	}
  
	private void getImages() {
		imageMap = readImages();
	}
	private void addButtons(){
		for (int i = 0; i < gl.getRows();i++){
			for (int j = 0; j < gl.getColumns(); j++){
				SpecialButton j1 = new SpecialButton(new Dimension(i,j),imageMap.get(map[i][j]), map[i][j]);
				j1.addActionListener(arg0 -> {
					map[j1.getPosition().width][j1.getPosition().height] = ImageOptionsPanel.buttonPressed;
					j1.setImage(imageMap.get(ImageOptionsPanel.buttonPressed));
					});
				add(j1);
			}
		}
	}
	
	


	public char[][] getMapEdited() {
		return map;
	}
}
