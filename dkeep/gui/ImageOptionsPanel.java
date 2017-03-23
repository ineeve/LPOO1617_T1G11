package dkeep.gui;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

class ImageOptionsPanel extends JPanel{

	public static char buttonPressed = ' ';

	public ImageOptionsPanel(HashMap<Character,Image> images) {
		setLayout(new GridLayout(0, 1));
		for (HashMap.Entry<Character, Image> entry : images.entrySet() ){
			char key = entry.getKey();
			if (key == 'O' ||key == 'X' ||key == 'I' ||key == ' ' ||key == 'k' || key == 'S' || key == 'A'){
			SpecialButton j1 = new SpecialButton(null,entry.getValue(),entry.getKey());
			j1.addActionListener(e -> {
                if (buttonPressed == j1.getType()){
                    buttonPressed = ' ';
                }
                else{
                    buttonPressed = j1.getType();
                }
            });
			add(j1);
			}
		}

	}
}
