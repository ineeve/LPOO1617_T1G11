package dkeep.gui;

import dkeep.logic.Configs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.border.EmptyBorder;


class SettingsPanel extends JPanel {
	private JLabel lblnumOgres = new JLabel("Number of Ogres");
	private JLabel lblGuardPersonality = new JLabel("Guard Personality");
	private JLabel lblLevelChooser = new JLabel("Initial level");
	private JComboBox personalityChooser;
	private JComboBox levelChooser;
	private JButton save = new JButton("");
	
	private Configs conf = null;
	private final Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
	private String[] personalities = {"Rookie","Drunken","Suspicious"};
	private String[] maps = {"Dungeon","Ogre Keep"};
	GridBagLayout gridBagLayout = new GridBagLayout();
	private final JButton btnBack;
	private BufferedImage background;
	private final Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
	private final Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
	
	private static final int MIN_OGRES = 1;
	private static final int MAX_OGRES = 5;
	private static final int INIT_OGRES = 1;
	private JSlider numberOfOgres = new JSlider(JSlider.HORIZONTAL,MIN_OGRES,MAX_OGRES,INIT_OGRES);
	
	SettingsPanel(JButton btnBack){
		this.btnBack = btnBack;
		init();
	}

	private void init(){
		try {
			background = ImageIO.read(new File("src/dkeep/assets/settingsWallpaper.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		repaint();
		sliderNumberOfOgres();
		setLayoutConfiguration();
        addComponentsToJpanel();
		addSaveListener();
	}

	public void setConfigs(Configs config){
		conf = config;
	}

    private void sliderNumberOfOgres(){
    	numberOfOgres.setFont(new Font("ArcadeClassic", Font.PLAIN, 20));
    	numberOfOgres.setForeground(Color.WHITE);
    	numberOfOgres.setBackground(new Color(0, 0, 0));
    	numberOfOgres.setMajorTickSpacing(4);
    	numberOfOgres.setMinorTickSpacing(1);
    	numberOfOgres.setPaintTicks(true);
    	numberOfOgres.setPaintLabels(true);
    }
    private void setLayoutConfiguration(){
    	gridBagLayout.columnWidths = new int[]{433, 82, 0, 200, 0, 85, 74, 51, 83, 97, 0};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30, 0, 0, 0, 30, 30, 30, 30, 30, 30, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
    }
    
    private void addLblNumOgres(){
		lblnumOgres.setForeground(Color.WHITE);
		lblnumOgres.setFont(new Font("ArcadeClassic", Font.PLAIN, 30));
    }
    
    private void addOgreSlider(){
    	GridBagConstraints gbc_lblLevelChooser = new GridBagConstraints();
    	gbc_lblLevelChooser.fill = GridBagConstraints.HORIZONTAL;
    	gbc_lblLevelChooser.insets = new Insets(5, 5, 5, 5);
    	gbc_lblLevelChooser.gridx = 3;
    	gbc_lblLevelChooser.gridy = 4;
    	add(lblLevelChooser, gbc_lblLevelChooser);
    }
    
    private void addGuardPersonalityLabel(){
		levelChooser = new JComboBox(maps);
		levelChooser.setFont(new Font("ArcadeClassic", Font.PLAIN, 20));
		levelChooser.setSelectedIndex(0);
		GridBagConstraints gbc_levelChooser = new GridBagConstraints();
		gbc_levelChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_levelChooser.insets = new Insets(0, 0, 5, 5);
		gbc_levelChooser.gridx = 5;
		gbc_levelChooser.gridy = 4;
		add(levelChooser, gbc_levelChooser);
		{
			GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
			gbc_rigidArea_1.insets = new Insets(0, 0, 5, 5);
			gbc_rigidArea_1.gridx = 3;
			gbc_rigidArea_1.gridy = 5;
			add(rigidArea_1, gbc_rigidArea_1);
		}
		lblGuardPersonality.setForeground(Color.WHITE);
		lblGuardPersonality.setFont(new Font("ArcadeClassic", Font.PLAIN, 30));
		GridBagConstraints gbc_lblGuardPersonality = new GridBagConstraints();
    	gbc_lblGuardPersonality.fill = GridBagConstraints.HORIZONTAL;
    	gbc_lblGuardPersonality.insets = new Insets(5, 5, 5, 5);
    	gbc_lblGuardPersonality.gridx = 3;
    	gbc_lblGuardPersonality.gridy = 6;
    	add(lblGuardPersonality, gbc_lblGuardPersonality);
    }

    private void addRigidArea(){
    	{
    		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
    		gbc_rigidArea_2.insets = new Insets(0, 0, 5, 5);
    		gbc_rigidArea_2.gridx = 3;
    		gbc_rigidArea_2.gridy = 7;
    		add(rigidArea_2, gbc_rigidArea_2);
    	}
    	GridBagConstraints gbc_rigidArea = new GridBagConstraints();
    	gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
    	gbc_rigidArea.gridx = 4;
    	gbc_rigidArea.gridy = 7;
    	rigidArea.setEnabled(false);
    	add(rigidArea, gbc_rigidArea);
    }
    
    private void addPeronalityComboBox(){
    	personalityChooser = new JComboBox(personalities);
    	personalityChooser.setFont(new Font("ArcadeClassic", Font.PLAIN, 20));
    	personalityChooser.setSelectedIndex(0);
    	GridBagConstraints gbc_personalityChooser = new GridBagConstraints();
    	gbc_personalityChooser.fill = GridBagConstraints.HORIZONTAL;
    	gbc_personalityChooser.insets = new Insets(0, 0, 5, 5);
    	gbc_personalityChooser.gridx = 5;
    	gbc_personalityChooser.gridy = 6;
    	add(personalityChooser, gbc_personalityChooser);
    }
    
    private void addInitialLevelLabel(){
		lblLevelChooser.setForeground(Color.WHITE);
		lblLevelChooser.setFont(new Font("ArcadeClassic", Font.PLAIN, 30));
    }
    
    private void addInitialLevelComboBox(){
    	GridBagConstraints gbc_lblnumOgres = new GridBagConstraints();
    	gbc_lblnumOgres.fill = GridBagConstraints.HORIZONTAL;
    	gbc_lblnumOgres.insets = new Insets(5, 5, 5, 5);
    	gbc_lblnumOgres.gridx = 3;
    	gbc_lblnumOgres.gridy = 8;
    	add(lblnumOgres, gbc_lblnumOgres);
    }
    
    private void addSaveButton(){
		GridBagConstraints gbc_numberOfOgres = new GridBagConstraints();
		gbc_numberOfOgres.fill = GridBagConstraints.HORIZONTAL;
		gbc_numberOfOgres.insets = new Insets(0, 0, 5, 5);
		gbc_numberOfOgres.gridx = 5;
		gbc_numberOfOgres.gridy = 8;
		add(numberOfOgres, gbc_numberOfOgres);
		
		GridBagConstraints gbc_save = new GridBagConstraints();
		gbc_save.fill = GridBagConstraints.HORIZONTAL;
		gbc_save.insets = new Insets(0, 0, 5, 5);
		gbc_save.gridx = 3;
		gbc_save.gridy = 12;
		save.setBackground(Color.BLACK);
		save.setIcon(new ImageIcon(SettingsPanel.class.getResource("/dkeep/assets/saveBtn.png")));
		add(save, gbc_save);
    }
    
    private void addBackBtn(){
    	GridBagConstraints gbc_backBtn = new GridBagConstraints();
    	gbc_backBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_backBtn.insets = new Insets(0, 0, 0, 5);
		gbc_backBtn.gridx =5;
		gbc_backBtn.gridy = 12;
		btnBack.setBackground(Color.BLACK);
		btnBack.setIcon(new ImageIcon(SettingsPanel.class.getResource("/dkeep/assets/backBtn.png")));
		add(btnBack, gbc_backBtn);
    }
    
	private void addComponentsToJpanel(){
		addLblNumOgres();
		addOgreSlider();
		addGuardPersonalityLabel();
		addRigidArea();
		addPeronalityComboBox();
		addInitialLevelLabel();
		addInitialLevelComboBox();
		addSaveButton();
		addBackBtn();
		
	}

	private void addSaveListener(){
		save.addActionListener(e -> {
			conf.setLevel(levelChooser.getSelectedIndex()+1);
			conf.NUMBEROFOGRES = numberOfOgres.getValue();
			conf.GUARDPERSONALITY = personalityChooser.getSelectedIndex();
		});
	}
	

	@Override
	public Dimension getPreferredSize() {
		return background == null ? new Dimension(800, 1000): new Dimension(background.getWidth(), background.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		Graphics2D g2 = (Graphics2D) g;
		g2.fill( new Rectangle(0, 0, d.width, d.height) );
		g.drawImage(background, 0, 0, d.width, d.height, null);
	}
	
	
}
