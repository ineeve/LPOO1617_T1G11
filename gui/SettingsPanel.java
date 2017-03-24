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
		addComponentsToJpanel();
		sliderNumberOfOgres();

		String[] personalities = {"Drunken","Rookie","Suspicious"};
		personalityChooser = new JComboBox(personalities);
		personalityChooser.setSelectedIndex(0);

		String[] maps = {"Test Map","Dungeon","Ogre's Keep"};
		levelChooser = new JComboBox(maps);
		levelChooser.setSelectedIndex(0);

		addSaveListener();
	}

	public void setConfigs(Configs config){
		conf = config;
	}

	private void addComponentsToJpanel(){
		add(lblnumOgres);
		add(numberOfOgres);
		add(lblGuardPersonality);
		add(personalityChooser);
		add(lblLevelChooser);
		add(levelChooser);
		add(save);
	}

	private void sliderNumberOfOgres(){
		numberOfOgres.setMaximum(5);
		numberOfOgres.setMinimum(1);
		numberOfOgres.setValue(numberOfOgres.getMinimum());
	}

	private void addSaveListener(){
		save.addActionListener(e -> {
			conf.setLevel(levelChooser.getSelectedIndex());
			Configs.NUMBEROFOGRES = numberOfOgres.getValue();
			Configs.GUARDPERSONALITY = personalityChooser.getSelectedIndex();
		});
	}
}
