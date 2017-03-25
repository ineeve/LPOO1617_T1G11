package dkeep.gui;

import dkeep.logic.maps.KeepMap;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;


class CreateMapPanel extends JPanel{
	private EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel();
	private HashMap<Character,Image> imageMap = new HashMap<>();
	private MapSizeSelectorPanel mapSizePanel = new MapSizeSelectorPanel();
	final int MIN_SIZE = 5;
	final int MAX_SIZE = 60;
	final int INIT_SIZE = 9;
	
	JSlider xSizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	JSlider ySizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	
	CreateMapPanel(){
		init();
	}

	private void loadImages(){
		imageMap = readImages();
	}

	private void init(){
		loadImages();
		JPanel componentsPanel = new ImageOptionsPanel(imageMap);
		componentsPanel.setBorder(new EmptyBorder(0, 2, 2, 2));
		setLayout(new BorderLayout());
		int eastPanelSize = 200;
		componentsPanel.setPreferredSize(new Dimension(eastPanelSize, eastPanelSize));
		add(componentsPanel,BorderLayout.EAST);
		editPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(editPanel,BorderLayout.CENTER);
		initMapSizePanel();

	}
	public void initMapSizePanel(){
		Font font = new Font("Serif", Font.ITALIC, 15);
		mapSizePanel.initSlider(xSizeSlider,font);
		mapSizePanel.initSlider(ySizeSlider,font);
		xSizeSlider.setAlignmentX(Component.RIGHT_ALIGNMENT);
		ySizeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		addXSliderListener();
		addYSliderListener();
		mapSizePanel.add(xSizeSlider);
		mapSizePanel.add(ySizeSlider);
		mapSizePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(mapSizePanel,BorderLayout.NORTH);
	}
	
	public void addXSliderListener(){
		xSizeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				System.out.println(source.getValue() + " " + ySizeSlider.getValue());
				KeepMap.resize(source.getValue(),ySizeSlider.getValue());
				editPanel.redraw();
			}
		});
	}
	public void addYSliderListener(){
		ySizeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				System.out.println(xSizeSlider.getValue() + " " + source.getValue());
				KeepMap.resize(xSizeSlider.getValue(),source.getValue());
				editPanel.redraw();
			}
		});
	}

}
