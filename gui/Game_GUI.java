package dkeep.gui;

import dkeep.logic.Configs;
import dkeep.logic.Game;

import javax.swing.*;
import java.awt.*;

class Game_GUI {
	private JFrame mainFrame = new JFrame("Escape Game");
	private JPanel containerPanel = new JPanel();
	private JButton btnBackSettings = new JButton("");
	private JButton btnBackEditMap = new JButton("");
	private JButton btnBackPlay = new JButton();
	private SettingsPanel settingsPanel = new SettingsPanel(btnBackSettings);
	private EditMapPanel editMapPanel = new EditMapPanel(btnBackEditMap);
	private PlayPanel playPanel = new PlayPanel(btnBackPlay);
	
	private CardLayout cl = new CardLayout();
	private SpecialButton btnSettings = new SpecialButton();
	private SpecialButton btnCreateMap = new SpecialButton();
	private SpecialButton btnPlay = new SpecialButton();
	private SpecialButton btnExit = new SpecialButton();
	private JPanel menuPanel = new InitialMenuPanel(btnSettings,btnCreateMap,btnPlay,btnExit);
	
	
	
	
	private Game game;
	private Configs config;

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
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
		game = new Game(config);
		settingsPanel.setConfigs(config);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		
		containerPanel.setLayout(cl);
		containerPanel.add(menuPanel, "1"); // "1" is the identifing string
		containerPanel.add(settingsPanel, "2");
		containerPanel.add(editMapPanel, "3");
		containerPanel.add(playPanel, "4");
		cl.show(containerPanel, "1"); //which panel is set initially

		addListenersToButtons();
		
		mainFrame.setMinimumSize(new Dimension(1000,800));
		mainFrame.getContentPane().add(containerPanel);
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
			game.setConfig(config);
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
		System.exit(0));
	}

	private void listenerbtnBackPlay(){
		btnBackPlay.addActionListener(arg0 -> {
			playPanel.disableListeners();
			playPanel.removeKeyListener();
			cl.show(containerPanel, "1");
			config.decreaseLevel();
			config.setKeepHeroKeyWeapon(editMapPanel.getHeroPos(), editMapPanel.getKeyPos(),editMapPanel.getWeaponPos());
		});
	}

	private void listenerbtnBackSettings(){
		btnBackSettings.addActionListener(arg0 -> {
			cl.show(containerPanel, "1");
			btnPlay.setEnabled(true);
		});
	}

	private void listenerbtnBackEditMap(){
		btnBackEditMap.addActionListener(arg0 ->{
			cl.show(containerPanel, "1");
			config.setKeepHeroKeyWeapon(editMapPanel.getHeroPos(), editMapPanel.getKeyPos(),editMapPanel.getWeaponPos());

		});
	}

}

