package dkeep.gui;

import dkeep.logic.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsPanel extends JPanel {
	JLabel lblnumOgres = new JLabel("Number of Ogres");
	JLabel lblGuardPersonality = new JLabel("Guard Personality");
	JLabel lblLevelChooser = new JLabel("Initial level");
	JComboBox personalityChooser;
	JComboBox levelChooser;
	JButton save = new JButton("Save Options");
	JSlider numberOfOgres = new JSlider();
	Configs conf = null;

	SettingsPanel(){
		init();
	}

	public void init(){
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
		String[] maps = {"Test Map","Dungeon","Ogre's Keep","Custom Map"};
		levelChooser = new JComboBox(maps);
		levelChooser.setSelectedIndex(0);
		add(levelChooser);
		add(save);

		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				conf.setLevel(levelChooser.getSelectedIndex());
				Configs.NUMBEROFOGRES = numberOfOgres.getValue();
				Configs.GUARDPERSONALITY = personalityChooser.getSelectedIndex();
			}
		});
	}
	public void setConfigs(Configs config){
		conf = config;
	}
	
	public Configs getConf(){
		return conf;
	}
	
}
