package dkeep.gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ReadImages {

	
	HashMap<Character,Image> imageMap = new HashMap<Character,Image>();
	
	public ReadImages(){
		initMap();
	}
	private void initMap(){
		try {
			imageMap.put('H', ImageIO.read(new File("src/assets/Hero.png")));
			imageMap.put('X', ImageIO.read(new File("src/assets/Horizontal_Wall.png")));
			imageMap.put('O', ImageIO.read(new File("src/assets/Ogre.png")));
			imageMap.put('k', ImageIO.read(new File("src/assets/key.png")));
			imageMap.put(' ', ImageIO.read(new File("src/assets/floor.png")));
			imageMap.put('I', ImageIO.read(new File("src/assets/door.png")));
			
		} catch (IOException e) {
			System.out.println("Error reading error image");
		}
	}
	
}
