package dkeep.gui;

import dkeep.logic.maps.KeepMap;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

class EditMapGraphicsPanel extends JPanel{
	private char [][] map;
	private GridLayout gl;

	private HashMap<Character,Image> imageMap;

	private boolean hasKey;
	private boolean hasHero;
	private boolean hasWeapon;
	private Point heroPos;
	private Point keyPos;
	private Point weaponPos;
	private SpecialButton [][] buttonsArray;


	public Point getHeroPos() {
		if (heroPos != null){
			map[heroPos.y][heroPos.x] = ' ';
			return (Point) heroPos.clone();
		}
		return null;
	}
	public Point getKeyPos() {
		if (keyPos != null){
			map[keyPos.y][keyPos.x] = ' ';
			return (Point) keyPos.clone();
		}
		return null;
	}
	public Point getWeaponPos(){
		if (weaponPos != null){
			map[weaponPos.y][weaponPos.x] = ' ';
			return (Point) weaponPos.clone();
		}
		return null;
	}

	EditMapGraphicsPanel(HashMap<Character,Image> hashImages){
		hasWeapon = false;
		hasKey = false;
		hasHero = false;
		imageMap = hashImages;
		map = (new KeepMap()).getMap();
		buttonsArray = new SpecialButton[map.length][map[0].length];
		gl = new GridLayout(map.length,map[0].length);
		init();
	}
	private void init(){
		setLayout(gl);
		addButtons();
	}

	private void addButtons(){
		for (int i = 0; i < gl.getRows();i++){
			for (int j = 0; j < gl.getColumns(); j++){
				SpecialButton j1 = new SpecialButton(new Dimension(i,j),imageMap.get(map[i][j]), map[i][j]);
				final Point tempPoint = new Point(j,i);
				j1.addActionListener(arg0 -> {
					checkValidMap();
					map[j1.getPosition().width][j1.getPosition().height] = ImageOptionsPanel.buttonPressed;
					if (ImageOptionsPanel.buttonPressed == 'H'){heroPos = tempPoint;}
					else if (ImageOptionsPanel.buttonPressed == 'k'){keyPos = tempPoint;}
					else if (ImageOptionsPanel.buttonPressed == '/'){weaponPos = tempPoint;}
					j1.setImage(imageMap.get(ImageOptionsPanel.buttonPressed));
				}); add(j1); buttonsArray[i][j] = j1;
			}
		}
	}

	private void checkValidMap() {
		char buttonPressed = ImageOptionsPanel.buttonPressed;
		if (ImageOptionsPanel.buttonPressed == 'H'){
			if (hasHero){
				clearPreviousPosition(buttonPressed);
				clearPreviousButton(buttonPressed);
			}else hasHero = true;
		}else if (ImageOptionsPanel.buttonPressed == 'k'){
			if (hasKey){
				clearPreviousPosition(buttonPressed);
				clearPreviousButton(buttonPressed);
			}else hasKey = true;
		}else if (ImageOptionsPanel.buttonPressed == '/'){
			if (hasWeapon){
				clearPreviousPosition(buttonPressed);
				clearPreviousButton(buttonPressed);
			}else hasWeapon = true;
		}
	}

	private void clearPreviousButton(char charToClear){
		for (int y = 0; y < buttonsArray.length; y++){
			for (int x = 0; x < buttonsArray[y].length; x++){
				if (buttonsArray[y][x].getImage() == imageMap.get(charToClear)){
					buttonsArray[y][x].setImage(imageMap.get(' '));
					repaint();
				}
			}
		}
	}
	private void clearPreviousPosition(char charToClear) {
		for (int y = 0; y < map.length; y++){
			for (int x = 0; x < map[0].length; x++){
				if (map[y][x] == charToClear){
					map[y][x] = ' ';
					repaint();
					return;
				}
			}
		}

	}
	public void redraw(){
		removeAll();
		map = (new KeepMap()).getMap();
		gl = new GridLayout(map.length,map[0].length);
		buttonsArray = new SpecialButton[map.length][map[0].length];
		setLayout(gl);
		addButtons();
		revalidate();
		repaint();
	}
}
