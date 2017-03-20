package dkeep.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import dkeep.logic.Configs;


public class SettingsPanel extends JPanel {
	JLabel lblnumOgres = new JLabel("Number of Ogres");
	JTextField numberOfOgres = new JTextField(10);
	JLabel lblGuardPersonality = new JLabel("Guard Personality");
	JLabel lblLevelChooser = new JLabel("Initial level");
	JComboBox personalityChooser;
	JComboBox levelChooser;
	JButton save = new JButton("Save Options");
	Configs conf;

	SettingsPanel(Configs config){
		conf = config;
		init();
	}
	public void init(){

		add(lblnumOgres);
		add(numberOfOgres);
		add(lblGuardPersonality);
		PlainDocument doc = (PlainDocument) numberOfOgres.getDocument();
		doc.setDocumentFilter(new MyIntFilter());

		String[] personalities = {"Drunken","Rookie","Suspicious"};
		personalityChooser = new JComboBox(personalities);
		personalityChooser.setSelectedIndex(0);
		add(personalityChooser);
		add(lblLevelChooser);
		String[] maps = {"Dungeon","Ogre's Keep","Custom Map"};
		levelChooser = new JComboBox(maps);
		levelChooser.setSelectedIndex(0);
		add(levelChooser);
		add(save);
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				conf.setLevel(levelChooser.getSelectedIndex());
				if (numberOfOgres.getText().equals("")){
					numberOfOgres.setText("1");
				}
				Configs.NUMBEROFOGRES = Integer.parseInt(numberOfOgres.getText());
				Configs.GUARDPERSONALITY = personalityChooser.getSelectedIndex();
				conf.prepareNextLevel();
			}

		});
	}
}
