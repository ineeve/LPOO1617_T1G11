package dkeep.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class SpecialButton extends JButton{

	private Dimension position;
	private Image image;
	private Character typeOfButton;

	public SpecialButton(Dimension pos,Image img, Character type){
		super();
		typeOfButton = type;
		position = pos;
		image = img;
	}
	public SpecialButton(){
		super();
	}

	public Character getType() {
		return typeOfButton;
	}
	public void setImage(Image newImage){
		image = newImage;
	}
	public Image getImage(){
		return image;
	}

	Dimension getPosition(){
		return position;
	}

	public void paint( Graphics g ) {
		super.paint( g );
		g.drawImage(image,  0 , 0 , getWidth() , getHeight() , null);
	}
	
	
}


