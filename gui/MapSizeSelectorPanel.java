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
	
	
	private KeepMap keepLevel;
	
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
	
	
	public void init(){
		//Try Grid Bag Layout
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
	}
	
}
