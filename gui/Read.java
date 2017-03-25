package dkeep.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

abstract class Read {
	public static HashMap<Character, Image> readImages(int level) {
		HashMap<Character, Image> imageMap = new HashMap<>();
		try {
			imageMap.put('X', ImageIO.read(new File("src/dkeep/assets/wall.png")));
			imageMap.put('I', ImageIO.read(new File("src/dkeep/assets/door.png")));
			Image Hero = ImageIO.read(new File("src/dkeep/assets/Hero.png"));
			imageMap.put('H', Hero);
			imageMap.put('A', Hero);
			if(level == 0) {
				imageMap.put('k', ImageIO.read(new File("src/dkeep/assets/lever.png")));
				imageMap.put('K', ImageIO.read(new File("src/dkeep/assets/leverPressed.png")));
			}
			else{
				imageMap.put('k', ImageIO.read(new File("src/dkeep/assets/key.png")));
				imageMap.put('K', ImageIO.read(new File("src/dkeep/assets/HeroWithKey.png")));
			}
			imageMap.put('G', ImageIO.read(new File("src/dkeep/assets/drunken.png")));
			imageMap.put('O', ImageIO.read(new File("src/dkeep/assets/Ogre.png")));
			imageMap.put('S', ImageIO.read(new File("src/dkeep/assets/stairs.png")));
			imageMap.put(' ', ImageIO.read(new File("src/dkeep/assets/floor.png")));
			imageMap.put('*', ImageIO.read(new File("src/dkeep/assets/club.png")));
			imageMap.put('8', ImageIO.read(new File("src/dkeep/assets/OgreStunned.png")));
			imageMap.put('/', ImageIO.read(new File("src/dkeep/assets/weapon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageMap;
	}
}
