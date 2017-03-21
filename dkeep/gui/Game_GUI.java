package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dkeep.logic.Configs;

public class Game_GUI {

	Configs config = new Configs(0);
	
	JFrame mainFrame = new JFrame("Escape Game");
	JPanel containerPanel = new JPanel();
	JPanel menuPanel = new InitialMenuPanel();
	JPanel playPanel = new PlayPanel(config);
	JPanel settingsPanel = new SettingsPanel(config);
	JPanel createMapPanel = new CreateMapPanel();
	CardLayout cl = new CardLayout();
	
	JButton btnSettings = new JButton("Settings");
	JButton btnCreateMap = new JButton("Create Map");
	JButton btnPlay = new JButton("Play Game");
	JButton btnExit = new JButton("Exit");
	JButton btnBack1 = new JButton("Back");
	JButton btnBack2 = new JButton("Back");
	JButton btnBack3 = new JButton("Back");
	
	//Logic Variables
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_GUI window = new Game_GUI();
					window.mainFrame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the application.
	 */
	public Game_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		containerPanel.setLayout(cl);
		
		menuPanel.add(btnSettings);
		menuPanel.add(btnCreateMap);
		menuPanel.add(btnPlay);
		menuPanel.add(btnExit);
		playPanel.add(btnBack1,BorderLayout.PAGE_END);
		settingsPanel.add(btnBack2);
		createMapPanel.add(btnBack3);
		
		
		
		containerPanel.add(menuPanel, "1"); // "1" is the identifing string
		containerPanel.add(settingsPanel,"2");
		containerPanel.add(createMapPanel, "3");
		containerPanel.add(playPanel, "4");
		cl.show(containerPanel, "1"); //which panel is set initially
		
		btnSettings.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "2");
			}
			
		});
		btnCreateMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "3");
			}
			
		});
		
		
		btnPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "4");
			}
			
		});
		btnExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		btnBack1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "1");
			}
			
		});
		btnBack2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "1");
			}
			
		});
		btnBack3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "1");
			}
			
		});
		
		mainFrame.add(containerPanel);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		
	}
	
}
