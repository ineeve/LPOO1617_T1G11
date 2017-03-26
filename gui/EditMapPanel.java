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
	private MapSizeSelectorPanel mapSizePanel = new MapSizeSelectorPanel();
	final int MIN_SIZE = 5;
	final int MAX_SIZE = 30;
	final int INIT_SIZE = 9;
	
	JSlider xSizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	JSlider ySizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	private final JLabel lblSelectWidth = new JLabel("Select  Width");
	private final JLabel lblSelectHeight = new JLabel("Select  Height");
	private JPanel componentsPanel;
	private final JButton btnNewButton = new JButton("New button");
	private final JButton btnNewButton_1 = new JButton("New button");
	
	EditMapPanel(){
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

	private void init(){
		loadImages();
		editPanel = new EditMapGraphicsPanel(imageMap);
		setLayout(new BorderLayout());
		initComponentsPanel();
		
		
		editPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(editPanel,BorderLayout.CENTER);
		initMapSizePanel();
	}
	
	public void initComponentsPanel(){
		componentsPanel = new ImageOptionsPanel(imageMap);
		add(componentsPanel,BorderLayout.EAST);
		
	}
	
	public void initMapSizePanel(){
		Font font = new Font("Serif", Font.ITALIC, 15);
		addXSliderListener();
		addYSliderListener();
		GridBagLayout gbl_mapSizePanel = new GridBagLayout();
		gbl_mapSizePanel.columnWidths = new int[] {200, 200};
		gbl_mapSizePanel.rowHeights = new int[] {0, 0};
		gbl_mapSizePanel.columnWeights = new double[]{0.0, 0.0};
		gbl_mapSizePanel.rowWeights = new double[]{0.0, 0.0};
		mapSizePanel.setBackground(Color.WHITE);
		mapSizePanel.setLayout(gbl_mapSizePanel);
		mapSizePanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		add(mapSizePanel,BorderLayout.NORTH);
		
		GridBagConstraints gbc_lblSelectWidth = new GridBagConstraints();
		gbc_lblSelectWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectWidth.gridx = 0;
		gbc_lblSelectWidth.gridy = 0;
		lblSelectWidth.setFont(new Font("ArcadeClassic", Font.PLAIN, 25));
		mapSizePanel.add(lblSelectWidth, gbc_lblSelectWidth);
		
		GridBagConstraints gbc_lblSelectHeight = new GridBagConstraints();
		gbc_lblSelectHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectHeight.gridx = 1;
		gbc_lblSelectHeight.gridy = 0;
		lblSelectHeight.setFont(new Font("ArcadeClassic", Font.PLAIN, 25));
		mapSizePanel.add(lblSelectHeight, gbc_lblSelectHeight);
		mapSizePanel.initSlider(xSizeSlider,font);
		xSizeSlider.setBackground(Color.WHITE);
		xSizeSlider.setFont(new Font("ArcadeClassic", Font.PLAIN, 20));
		xSizeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc_xSizeSlider = new GridBagConstraints();
		gbc_xSizeSlider.weightx = 1.0;
		gbc_xSizeSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_xSizeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_xSizeSlider.gridx = 0;
		gbc_xSizeSlider.gridy = 1;
		mapSizePanel.add(xSizeSlider, gbc_xSizeSlider);
		mapSizePanel.initSlider(ySizeSlider,font);
		GridBagConstraints gbc_ySizeSlider = new GridBagConstraints();
		gbc_ySizeSlider.weightx = 1.0;
		gbc_ySizeSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_ySizeSlider.insets = new Insets(0, 0, 5, 0);
		gbc_ySizeSlider.gridx = 1;
		gbc_ySizeSlider.gridy = 1;
		ySizeSlider.setBackground(Color.WHITE);
		ySizeSlider.setFont(new Font("ArcadeClassic", Font.PLAIN, 20));
		mapSizePanel.add(ySizeSlider, gbc_ySizeSlider);
		ySizeSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
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
