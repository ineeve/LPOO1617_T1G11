package dkeep.gui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.KeepMap;

import java.util.HashMap;

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
		ReadImages r1 = new ReadImages();
		imageMap = r1.getImageMap();
	}
	public void addButtons(){
		System.out.println(gl.getRows() + " " + gl.getColumns());
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
