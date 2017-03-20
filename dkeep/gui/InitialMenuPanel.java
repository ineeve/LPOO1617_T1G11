package dkeep.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InitialMenuPanel extends JPanel{
	public JButton btnSettings;
	public JButton btnCreateMap;
	public JButton btnPlay;
	public JButton btnExit;
	
	
	public InitialMenuPanel(){
		init();
	}
	public void init(){
		setLayout(new GridLayout(4,3));
		btnSettings = new JButton("Settings");
		btnCreateMap= new JButton("Create Map");
		btnPlay= new JButton("Play Game");
		btnExit= new JButton("Exit");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCreateMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(btnSettings);
		add(btnCreateMap);
		add(btnPlay);
		add(btnExit);
		btnSettings.setVisible(true);
		btnCreateMap.setVisible(true);
		btnPlay.setVisible(true);
		btnExit.setVisible(true);
		setVisible(true);
	}
}
