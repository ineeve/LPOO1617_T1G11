package dkeep.gui;


import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JPanel;

public class ImageOptionsPanel extends JPanel{

	public static char buttonPressed;

	public ImageOptionsPanel(HashMap<Character,Image> images) {
		setLayout(new GridLayout(0, 1));
		for (HashMap.Entry<Character, Image> entry : images.entrySet() ){
			char key = entry.getKey();
			if (key == 'O' ||key == 'X' ||key == 'I' ||key == ' ' ||key == 'k' || key == 'S' || key == 'A'){
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
}
