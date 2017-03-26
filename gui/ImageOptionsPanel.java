package dkeep.gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.HashMap;

class ImageOptionsPanel extends JPanel{

	public static char buttonPressed = ' ';
	private JButton [] buttonsArray = new JButton[6];
	private GridBagLayout gbl_componentsPanel = new GridBagLayout();
	
	
	public ImageOptionsPanel(HashMap<Character,Image> images) {
		generateButtons(images);
		prepareLayout();
		addButtons();

	}
	private void prepareLayout(){
		setBorder(new EmptyBorder(0, 2, 2, 2));
		gbl_componentsPanel.columnWidths = new int[]{196, 0};
		gbl_componentsPanel.rowHeights = new int[] {45, 45, 45, 45, 45,45};
		gbl_componentsPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_componentsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_componentsPanel);
	}
	
	private void generateButtons(HashMap<Character,Image> images){
		int i = 0;
		for (HashMap.Entry<Character, Image> entry : images.entrySet() ){
			char key = entry.getKey();
			if (key == 'X' ||key == 'I' ||key == ' ' ||key == 'k' || key == 'A'){
			SpecialButton j1 = new SpecialButton(null,entry.getValue(),entry.getKey());
			j1.addActionListener(e -> {
                if (buttonPressed == j1.getType()){
                    buttonPressed = ' ';
                }
                else{
                    buttonPressed = j1.getType();
                }
            });
			buttonsArray[i++] = j1;
			}
		}
	}
	
	private void addButtons(){
		for (int i = 0; i < buttonsArray.length-1;i++){
			GridBagConstraints gbc_btn = new GridBagConstraints();
			gbc_btn.fill = GridBagConstraints.BOTH;
			gbc_btn.insets = new Insets(5, 20, 5, 20);
			gbc_btn.gridx = 0;
			gbc_btn.gridy = i;
			add(buttonsArray[i], gbc_btn);
		}
		
	}
}
