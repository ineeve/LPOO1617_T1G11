package dkeep.gui;

import dkeep.logic.Configs;
import dkeep.logic.Game;

import javax.swing.*;
import java.awt.*;

class Game_GUI{
	private JFrame mainFrame = new JFrame("Escape Game");
	private JPanel containerPanel = new JPanel();
	private JPanel menuPanel = new InitialMenuPanel();
	private SettingsPanel settingsPanel = new SettingsPanel();
	private PlayPanel playPanel = new PlayPanel();
	private JPanel editMapPanel = new CreateMapPanel();
	private CardLayout cl = new CardLayout();
	private JButton btnSettings = new JButton("Settings");
	private JButton btnCreateMap = new JButton("Create Map");
	private JButton btnPlay = new JButton("Play Game");
	private JButton btnExit = new JButton("Exit");
	private JButton btnBackPlay = new JButton("Back");
	private JButton btnBackSettings = new JButton("Back");
	private JButton btnBackEditMap = new JButton("Back");
	private Game game = new Game();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                Game_GUI window = new Game_GUI();
                window.mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}



	/**
	 * Create the application.
	 */
	private Game_GUI() {
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
		editMapPanel.add(btnBackEditMap,BorderLayout.PAGE_END);
		containerPanel.add(menuPanel, "1"); // "1" is the identifing string
		containerPanel.add(settingsPanel,"2");
		containerPanel.add(editMapPanel, "3");
		containerPanel.add(playPanel, "4");
		cl.show(containerPanel, "1"); //which panel is set initially

		btnSettings.addActionListener(arg0 -> cl.show(containerPanel, "2"));
		btnCreateMap.addActionListener(arg0 -> cl.show(containerPanel, "3"));


		btnPlay.addActionListener(arg0 -> {
            game.resetLevel();
            playPanel.addListeners();
            playPanel.resetGameStatusLabel();
            playPanel.setGame(game);
            cl.show(containerPanel, "4");
            playPanel.enableMoveButtons();
            playPanel.repaint();
        });
		btnExit.addActionListener(arg0 -> System.exit(0));
		btnBackPlay.addActionListener(arg0 -> {
            cl.show(containerPanel, "1");
            game.getConfig().decreaseLevel();
        });
		btnBackSettings.addActionListener(arg0 -> {
            cl.show(containerPanel, "1");
            btnPlay.setEnabled(true);
        });
		btnBackEditMap.addActionListener(arg0 -> {
            cl.show(containerPanel, "1");
            ((CreateMapPanel)editMapPanel).saveMapEdited();
        });

		mainFrame.add(containerPanel);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

}
