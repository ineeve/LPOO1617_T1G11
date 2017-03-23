package dkeep.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ImageOptionsPanel extends JPanel{

	public static char buttonPressed;

	public ImageOptionsPanel(HashMap<Character,ImageIcon> images) {
		setLayout(new GridLayout(0, 1));
		for (HashMap.Entry<Character, ImageIcon> entry : images.entrySet() ){
			SpecialButton j1 = new SpecialButton(null,entry.getValue(),entry.getKey());
			j1.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (buttonPressed == j1.getType()){
						buttonPressed = '#';
					}
					else{
						buttonPressed = j1.getType();
					}
				}});
			add(j1);
		}

	}
}
