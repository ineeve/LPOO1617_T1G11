package dkeep.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dkeep.logic.Configs;
import dkeep.logic.Game;

public class PlayPanel extends JPanel{
	private JLabel gameStatsLlb;
	private JButton btnExit;
	private JPanel moveButtonsPanel;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnDown;
	private JButton btnRight;
	private JPanel graphicsPanel;
	private Game game;
	private Configs config;

	PlayPanel(Configs conf){
		config = conf;
		game = new Game();
		init();
	}
	public void init(){
		setLayout(new FlowLayout());
		graphicsPanel = new SimpleGraphicsPanel();
		add(graphicsPanel);
		graphicsPanel.setVisible(true);
		
	}
	
	
	public void moveAgents_GUI(char heroDirection){

		if (game.moveHero(heroDirection)==1){ //change To next level
			config.prepareNextLevel();
			game.setMap(config.getMap());
			game.setAgents(config.getAgents());
			game.setKey(config.getKey());
			game.setKeyTaken(false);
			//displayBoard(game.getMap());
			return;
		}
		//displayBoard(game.getMap());
		if (checkForGameOver() == true){
			disableMoveButtons();
		}
		else{
			game.moveBots();
			//displayBoard(game.getMap());
		}
		if (checkForGameOver() == true){
			disableMoveButtons();
		}
	}


	public boolean checkForGameOver(){
		game.isGameOver(); //To update status value in game object.
		if (game.getGameStatus() == Game.status.DEFEAT){
			gameStatsLlb.setText("You have been captured");
			return true;
		}
		else if (game.getGameStatus() == Game.status.VICTORY){
			gameStatsLlb.setText("You have escaped, congrats!");
			return true;
		}
		return false;
	}

	public void disableMoveButtons(){

		btnUp.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
		btnDown.setEnabled(false);
	}
}
