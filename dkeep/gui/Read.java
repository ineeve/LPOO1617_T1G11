package dkeep.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class Read {
	public static HashMap<Character, Image> readImages() {
		HashMap<Character, Image> imageMap = new HashMap<>();
		try {
			imageMap.put('X', ImageIO.read(new File("src/assets/wall.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imageMap.put('I', ImageIO.read(new File("src/assets/door.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Image Hero = ImageIO.read(new File("src/assets/Hero.png"));
			imageMap.put('H', Hero);
			imageMap.put('A', Hero);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imageMap.put('K', ImageIO.read(new File("src/assets/HeroWithKey.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imageMap.put('G', ImageIO.read(new File("src/assets/drunken.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imageMap.put('k', ImageIO.read(new File("src/assets/key.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imageMap.put('L', ImageIO.read(new File("src/assets/lever.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			imageMap.put('O', ImageIO.read(new File("src/assets/Ogre.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			imageMap.put('S', ImageIO.read(new File("src/assets/stairs.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			imageMap.put(' ', ImageIO.read(new File("src/assets/floor.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			imageMap.put('*', ImageIO.read(new File("src/assets/club.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			imageMap.put('8', ImageIO.read(new File("src/assets/OgreStunned.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			imageMap.put('/', ImageIO.read(new File("src/assets/weapon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageMap;
	}
}
