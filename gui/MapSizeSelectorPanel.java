package dkeep.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class MapSizeSelectorPanel extends JPanel {

	static int MIN_SIZE = 5;
	static int MAX_SIZE = 60;
	static int INIT_SIZE = 9;
	
	
	//Create the label.
    JLabel widthLabel = new JLabel("Map Width", JLabel.CENTER);
    JLabel heightLabel = new JLabel("Map Height", JLabel.CENTER);
	
	JSlider xSizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	JSlider ySizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	
	
	
	
	public MapSizeSelectorPanel(){
		super();
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
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		widthLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		heightLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		Font font = new Font("Serif", Font.ITALIC, 15);
		initSlider(xSizeSlider,font);
		initSlider(ySizeSlider,font);
		add(widthLabel);
		add(heightLabel);
		add(xSizeSlider,Component.LEFT_ALIGNMENT);
		add(ySizeSlider,Component.RIGHT_ALIGNMENT);
		
	}
	/**
	 * 
	 * @return a Dimension with (newXSize,newYSize)
	 */
	public Dimension getMapSize(){
		return new Dimension(xSizeSlider.getValue(),ySizeSlider.getValue());
		
	}
	
}
