package dkeep.gui;

import dkeep.logic.Configs;
import dkeep.logic.Game;

import javax.swing.*;
import java.awt.*;

class Game_GUI {
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
	private Configs config;

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
		config = new Configs(0);
		game.setConfigs(config);
		settingsPanel.setConfigs(config);
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
		playPanel.add(btnBackPlay, BorderLayout.PAGE_END);
		settingsPanel.add(btnBackSettings);
		editMapPanel.add(btnBackEditMap, BorderLayout.PAGE_END);
		containerPanel.add(menuPanel, "1"); // "1" is the identifing string
		containerPanel.add(settingsPanel, "2");
		containerPanel.add(editMapPanel, "3");
		containerPanel.add(playPanel, "4");
		cl.show(containerPanel, "1"); //which panel is set initially

		addListenersToButtons();

		mainFrame.add(containerPanel);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

	private void addListenersToButtons() {
		listenerbtnSettings();
		listenerbtnCreateMap();
		listenerbtnPlay();
        listenerbtnExit();
        listenerbtnBackPlay();
        listenerbtnBackSettings();
        listenerbtnBackEditMap();
	}

	private void listenerbtnSettings(){
        btnSettings.addActionListener(argv0 ->
                cl.show(containerPanel, "2")
        );
	}

	private void listenerbtnCreateMap(){
        btnCreateMap.addActionListener(nargv0 ->
                cl.show(containerPanel, "3")
        );
	}

	private void listenerbtnPlay(){
		btnPlay.addActionListener(arg0 -> {
			game.resetLevel();
			playPanel.addListeners();
			playPanel.resetGameStatusLabel();
			playPanel.setGame(game);
			cl.show(containerPanel, "4");
			playPanel.enableMoveButtons();
			playPanel.repaint();
		});
	}

	private void listenerbtnExit(){
        btnExit.addActionListener(arg0 ->
                System.exit(0)
        );
    }

    private void listenerbtnBackPlay(){
        btnBackPlay.addActionListener(arg0 -> {
            cl.show(containerPanel, "1");
            config.decreaseLevel();
        });
    }

    private void listenerbtnBackSettings(){
        btnBackSettings.addActionListener(arg0 -> {
            cl.show(containerPanel, "1");
            btnPlay.setEnabled(true);
        });
    }

    private void listenerbtnBackEditMap(){
        btnBackEditMap.addActionListener(arg0 -> {
            cl.show(containerPanel, "1");
            ((CreateMapPanel) editMapPanel).saveMapEdited();
        });
    }

}

