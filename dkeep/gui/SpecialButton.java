package dkeep.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class SpecialButton extends JButton{

	Dimension position;
	Image image;
	Character typeOfButton;
	
	public SpecialButton(Dimension pos,Image img, Character type){
		super();
		typeOfButton = type;
		position = pos;
		setBorder(new LineBorder(Color.BLACK));
		//setMargin(new Insets(0, 0, 0, 0));
        image = img;
	}
	
	
	public Character getType() {
		return typeOfButton;
	}
	public void setImage(Image newImage){
		image = newImage;
	}



	Dimension getPosition(){
		return position;
	}

 public void paint( Graphics g ) {
        super.paint( g );
        g.drawImage(image,  0 , 0 , getWidth() , getHeight() , null);
    }
 }


