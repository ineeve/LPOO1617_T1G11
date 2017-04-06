package dkeep.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class MapSizeSelectorPanel extends JPanel {

	final int MIN_SIZE = 5;
	final int MAX_SIZE = 30;
	final int INIT_SIZE = 9;
	
	JSlider xSizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	JSlider ySizeSlider = new JSlider(JSlider.HORIZONTAL,
			MIN_SIZE, MAX_SIZE, INIT_SIZE);
	
	private final JLabel lblSelectWidth = new JLabel("Select  Width");
	private final JLabel lblSelectHeight = new JLabel("Select  Height");

	
	
	public MapSizeSelectorPanel(JSlider xSizeSlider,JSlider ySizeSlider){
		this.xSizeSlider = xSizeSlider;
		this.ySizeSlider = ySizeSlider;
		init();
	}

	public void init(){
		initLayout();
		initWidthLabel();
		initHeightLabel();
		Font font = new Font("ArcadeClassic", Font.PLAIN, 20);
		initSlider(xSizeSlider,font);
		initSlider(ySizeSlider,font);
		initSliderLayout(xSizeSlider,0);
		initSliderLayout(ySizeSlider,1);
	}
	
	public void initSlider(JSlider j1,Font font){
		j1.setMinimum(MIN_SIZE);
		j1.setMaximum(MAX_SIZE);
		j1.setValue(INIT_SIZE);
		j1.setMajorTickSpacing(5);
		j1.setMinorTickSpacing(1);
		j1.setPaintTicks(true);
		j1.setPaintLabels(true);
		j1.setBackground(Color.WHITE);
		j1.setFont(font);
		j1.setAlignmentX(Component.LEFT_ALIGNMENT);	
	}

	private void initSliderLayout(JSlider slider,int gridX) {
		GridBagConstraints gbc_SizeSlider = new GridBagConstraints();
		gbc_SizeSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_SizeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_SizeSlider.gridx = gridX;
		gbc_SizeSlider.gridy = 1;
		add(slider, gbc_SizeSlider);
		
	}

	private void initHeightLabel() {
		GridBagConstraints gbc_lblSelectHeight = new GridBagConstraints();
		gbc_lblSelectHeight.weightx = 1.0;
		gbc_lblSelectHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectHeight.gridx = 1;
		gbc_lblSelectHeight.gridy = 0;
		lblSelectHeight.setFont(new Font("ArcadeClassic", Font.PLAIN, 25));
		add(lblSelectHeight, gbc_lblSelectHeight);
		
	}

	private void initWidthLabel() {
		GridBagConstraints gbc_lblSelectWidth = new GridBagConstraints();
		gbc_lblSelectWidth.weightx = 1.0;
		gbc_lblSelectWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectWidth.gridx = 0;
		gbc_lblSelectWidth.gridy = 0;
		lblSelectWidth.setFont(new Font("ArcadeClassic", Font.PLAIN, 25));
		add(lblSelectWidth, gbc_lblSelectWidth);
	}

	private void initLayout() {
		GridBagLayout gbl_mapSizePanel = new GridBagLayout();
		gbl_mapSizePanel.columnWidths = new int[] {200, 200};
		gbl_mapSizePanel.rowHeights = new int[] {0, 0};
		gbl_mapSizePanel.columnWeights = new double[]{0.0, 0.0};
		gbl_mapSizePanel.rowWeights = new double[]{0.0, 0.0};
		setBackground(Color.WHITE);
		setLayout(gbl_mapSizePanel);
		setBorder(new EmptyBorder(0, 0, 10, 0));
		
	}
	
}
