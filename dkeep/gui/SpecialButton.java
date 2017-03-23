package dkeep.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;


import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SpecialButton extends JButton{

	Dimension position;
	Image image;
	Character typeOfButton;
	
	public SpecialButton(Dimension pos,ImageIcon icon, Character type){
		super();
		typeOfButton = type;
		position = pos;
		setMargin(new Insets(0, 0, 0, 0));
        image = icon.getImage();
	}
	
	
	public Character getType() {
		return typeOfButton;
	}




	Dimension getPosition(){
		return position;
	}

 public void paint( Graphics g ) {
        super.paint( g );
        g.drawImage(image,  0 , 0 , getWidth() , getHeight() , null);
    }
 }


