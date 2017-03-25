package dkeep.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static dkeep.gui.Read.readImages;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dkeep.logic.maps.KeepMap;


class CreateMapPanel extends JPanel{
	private KeepMap keepLevel = new KeepMap();
	private EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel(keepLevel);
	private HashMap<Character,Image> imageMap = new HashMap<>();
	private MapSizeSelectorPanel mapSizePanel = new MapSizeSelectorPanel(keepLevel);
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
				mapSizePanel.updateMapSize(source.getValue(),ySizeSlider.getValue() );
				editPanel.redraw();
				editPanel.repaint();
			}
		});
	}
	public void addYSliderListener(){
		ySizeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				System.out.println(source.getValue() + " " + ySizeSlider.getValue());
				mapSizePanel.updateMapSize(source.getValue(),ySizeSlider.getValue() );
				editPanel.redraw();
				editPanel.repaint();
			}
		});
	}

}
