package dkeep.gui;

import dkeep.logic.Configs;

import javax.swing.*;
import java.awt.*;


class SettingsPanel extends JPanel {
	private JLabel lblnumOgres = new JLabel("Number of Ogres");
	private JLabel lblGuardPersonality = new JLabel("Guard Personality");
	private JLabel lblLevelChooser = new JLabel("Initial level");
	private JComboBox personalityChooser;
	private JComboBox levelChooser;
	private JButton save = new JButton("Save Options");
	private JSlider numberOfOgres = new JSlider();
	private Configs conf = null;

	SettingsPanel(){
		init();
	}

	private void init(){
		setBackground(Color.RED);
		add(lblnumOgres);
		add(numberOfOgres);
		add(lblGuardPersonality);
		
		numberOfOgres.setMaximum(5);
		numberOfOgres.setMinimum(1);
		numberOfOgres.setValue(numberOfOgres.getMinimum());

		String[] personalities = {"Drunken","Rookie","Suspicious"};
		personalityChooser = new JComboBox(personalities);
		personalityChooser.setSelectedIndex(0);
		add(personalityChooser);
		add(lblLevelChooser);
		String[] maps = {"Test Map","Dungeon","Ogre's Keep"};
		levelChooser = new JComboBox(maps);
		levelChooser.setSelectedIndex(0);
		add(levelChooser);
		add(save);

		save.addActionListener(e -> {
            conf.setLevel(levelChooser.getSelectedIndex());
            Configs.NUMBEROFOGRES = numberOfOgres.getValue();
            Configs.GUARDPERSONALITY = personalityChooser.getSelectedIndex();
        });
	}
	public void setConfigs(Configs config){
		conf = config;
	}
	
}
