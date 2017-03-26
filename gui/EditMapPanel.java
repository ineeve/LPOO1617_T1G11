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


class EditMapPanel extends JPanel{
	private EditMapGraphicsPanel editPanel;
	private HashMap<Character,Image> imageMap = new HashMap<>();
	private MapSizeSelectorPanel mapSizePanel;
	private JButton backBtn;
	JSlider xSizeSlider = new JSlider();
	JSlider ySizeSlider = new JSlider();
	
	private JPanel componentsPanel;

	
	EditMapPanel(JButton backBtn){
		this.backBtn = backBtn;
		init();
	}

	private void loadImages(){
		imageMap = readImages(3);
	}
	public Point getKeyPos(){
		return editPanel.getKeyPos();
	}
	public Point getHeroPos(){
		return editPanel.getHeroPos();
	}
	public Point getWeaponPos(){
		return editPanel.getWeaponPos();
	}

	private void init(){
		loadImages();
		setLayout(new BorderLayout());
		initEditPanel();
		initComponentsPanel();
		initMapSizePanel();
		initBackButton();
	}
	
	private void initBackButton() {
		backBtn.setBackground(Color.BLACK);
		backBtn.setIcon(new ImageIcon(SettingsPanel.class.getResource("/dkeep/assets/backBtn.png")));
		add(backBtn,BorderLayout.SOUTH);
		
	}

	public void initEditPanel(){
		editPanel = new EditMapGraphicsPanel(imageMap);
		editPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(editPanel,BorderLayout.CENTER);
	}
	
	public void initComponentsPanel(){
		componentsPanel = new ImageOptionsPanel(imageMap);
		add(componentsPanel,BorderLayout.EAST);
		
	}
	
	public void initMapSizePanel(){
		mapSizePanel = new MapSizeSelectorPanel(xSizeSlider,ySizeSlider);
		addXSliderListener();
		addYSliderListener();
		add(mapSizePanel,BorderLayout.NORTH);
	}
	
	public void addXSliderListener(){
		xSizeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
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
				KeepMap.resize(xSizeSlider.getValue(),source.getValue());
				editPanel.redraw();
			}
		});
	}

}
