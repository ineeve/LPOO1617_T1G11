package dkeep.gui;

import javax.swing.*;
import java.awt.*;

public class MapSizeSelectorPanel extends JPanel {

	
	public MapSizeSelectorPanel(){
		init();
	}
	
	public void initSlider(JSlider j1,Font font){
		j1.setMajorTickSpacing(10);
		j1.setMinorTickSpacing(1);
		j1.setPaintTicks(true);
		j1.setPaintLabels(true);
		j1.setFont(font);
		
	}

	public void init(){
		//Try Grid Bag Layout
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	
}
