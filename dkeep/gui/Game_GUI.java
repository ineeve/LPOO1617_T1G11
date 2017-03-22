package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dkeep.logic.Configs;
import dkeep.logic.Game;

public class Game_GUI{

	JFrame mainFrame = new JFrame("Escape Game");
	JPanel containerPanel = new JPanel();
	JPanel menuPanel = new InitialMenuPanel();
	SettingsPanel settingsPanel = new SettingsPanel();
	PlayPanel playPanel = new PlayPanel();
	JPanel editMapPanel = new CreateMapPanel();
	CardLayout cl = new CardLayout();

	JButton btnSettings = new JButton("Settings");
	JButton btnCreateMap = new JButton("Create Map");
	JButton btnPlay = new JButton("Play Game");
	JButton btnExit = new JButton("Exit");
	JButton btnBackPlay = new JButton("Back");
	JButton btnBackSettings = new JButton("Back");
	JButton btnBackEditMap = new JButton("Back");
	Game game = new Game();


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
		game.setConfigs(new Configs(0));
		settingsPanel.setConfigs(game.getConfig());
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
		playPanel.add(btnBackPlay,BorderLayout.PAGE_END);
		settingsPanel.add(btnBackSettings);
		editMapPanel.add(btnBackEditMap);
<<<<<<<
		
		
		
=======


>>>>>>>
		containerPanel.add(menuPanel, "1"); // "1" is the identifing string
		containerPanel.add(settingsPanel,"2");
		containerPanel.add(editMapPanel, "3");
		containerPanel.add(playPanel, "4");
		cl.show(containerPanel, "1"); //which panel is set initially

		btnSettings.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "2");
			}
<<<<<<<

=======

>>>>>>>
		});
		btnCreateMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "3");
			}
<<<<<<<

=======

>>>>>>>
		});


		btnPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.resetLevel();
				playPanel.addListeners();
				playPanel.resetGameStatusLabel();
				playPanel.setGame(game);
				cl.show(containerPanel, "4");
				playPanel.enableMoveButtons();
				playPanel.repaint();
			}
<<<<<<<

=======

>>>>>>>
		});
		btnExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
<<<<<<<

=======

>>>>>>>
		});
		btnBackPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "1");
				game.getConfig().decreaseLevel();
			}
<<<<<<<

=======

>>>>>>>
		});
		btnBackSettings.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "1");
				btnPlay.setEnabled(true);
			}
<<<<<<<

=======

>>>>>>>
		});
		btnBackEditMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "1");
			}
<<<<<<<

=======

>>>>>>>
		});


		mainFrame.add(containerPanel);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);



	}

}
