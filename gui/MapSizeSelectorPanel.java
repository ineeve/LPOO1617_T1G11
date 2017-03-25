package dkeep.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dkeep.logic.maps.KeepMap;

import java.awt.FlowLayout;

public class MapSizeSelectorPanel extends JPanel {
	
	
	final int MIN_SIZE = 5;
	final int MAX_SIZE = 60;
	final int INIT_SIZE = 9;
	
	JSlider xSizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	JSlider ySizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	
	KeepMap keepLevel;
	public MapSizeSelectorPanel(KeepMap keepLevel){
		super();
		this.keepLevel = keepLevel;
		init();
	}
	
	public void updateMapSize(int width, int height){
		keepLevel.resize(width, height);
	}
	
	public void initSlider(JSlider j1,Font font){
		j1.setMajorTickSpacing(10);
		j1.setMinorTickSpacing(1);
		j1.setPaintTicks(true);
		j1.setPaintLabels(true);
		j1.setFont(font);
		
	}
	public void addXSliderListener(){
		xSizeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				keepLevel.resize(source.getValue(),ySizeSlider.getValue() );
			}
		});
	}
	public void addYSliderListener(){
		ySizeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				keepLevel.resize(xSizeSlider.getValue(),source.getValue() );
			}
		});
	}
	
	public void init(){
		//Try Grid Bag Layout
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Font font = new Font("Serif", Font.ITALIC, 15);
		initSlider(xSizeSlider,font);
		initSlider(ySizeSlider,font);
		xSizeSlider.setAlignmentX(Component.RIGHT_ALIGNMENT);
		ySizeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		add(xSizeSlider);
		add(ySizeSlider);
		
	}
	/**
	 * 
	 * @return a Dimension with (newXSize,newYSize)
	 */
	public Dimension getMapSize(){
		return new Dimension(xSizeSlider.getValue(),ySizeSlider.getValue());
		
	}
	
}
