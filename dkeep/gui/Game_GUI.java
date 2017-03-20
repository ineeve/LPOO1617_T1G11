package dkeep.gui;

import dkeep.logic.Configs;
import dkeep.logic.Game;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game_GUI {

	Game game = new Game();
	Configs config = new Configs(0);
	
	JFrame mainFrame = new JFrame("Escape Game");
	JPanel containerPanel = new JPanel();
	JPanel menuPanel = new InitialMenuPanel();
	JPanel playPanel = new JPanel();
	JPanel settingsPanel = new SettingsPanel(config);
	CardLayout cl = new CardLayout();
	
	JButton btnSettings = new JButton("Settings");
	JButton btnCreateMap = new JButton("Create Map");
	JButton btnPlay = new JButton("Play Game");
	JButton btnExit = new JButton("Exit");
	JButton btnBack1 = new JButton("Back");
	JButton btnBack2 = new JButton("Back");
	
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
		playPanel.add(btnBack1);
		settingsPanel.add(btnBack2);
		
		playPanel.setBackground(Color.GREEN);
		settingsPanel.setBackground(Color.RED);
		
		containerPanel.add(menuPanel, "1"); // "1" is the identifing string
		containerPanel.add(settingsPanel,"2");
		containerPanel.add(playPanel, "3");
		cl.show(containerPanel, "1"); //which panel is set initially
		
		btnSettings.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "2");
			}
			
		});
		btnPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cl.show(containerPanel, "3");
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
		
		mainFrame.add(containerPanel);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		

		/*btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameStatsLlb.setText("You can play now!");
				game = new Game();
				config = new Configs(1);
				Configs.GUARDPERSONALITY = personalityChooser.getSelectedIndex();
				if (numberOfOgres.getText().equals("")){
					numberOfOgres.setText("1");
				}
				Configs.NUMBEROFOGRES = Integer.parseInt(numberOfOgres.getText());
				config.prepareNextLevel();
				game.setMap(config.getMap());
				game.setAgents(config.getAgents());
				game.setKey(config.getKey());
				game.setKeyTaken(false);
				Game.gameStatus = Game.status.PLAYING;
				/*while (game.isGameOver() == false) {
		            displayBoard(game.getMap());
		            
		        }
				displayBoard(game.getMap());
				System.out.println("You have been captured");

			}
		});
		*/
		
	}
	
}
