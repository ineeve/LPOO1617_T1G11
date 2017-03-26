package dkeep.gui;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class InitialMenuPanel extends JPanel{
	
	private BufferedImage image;
	public InitialMenuPanel(){
		init();
	}
	private void init(){
		try {
            image = ImageIO.read(new File("src/dkeep/assets/wallpaper.jpg"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
	}
	
	@Override
    public Dimension getPreferredSize() {
        return image == null ? new Dimension(400, 300): new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
