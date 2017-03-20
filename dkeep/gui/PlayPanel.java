package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dkeep.logic.Configs;
import dkeep.logic.Game;

public class PlayPanel extends JPanel{
	private JLabel gameStatsLlb;
	private JPanel moveButtonsPanel = new JPanel(new BorderLayout());
	private JButton btnUp = new JButton("");
	private JButton btnLeft = new JButton("");
	private JButton btnDown = new JButton("");
	private JButton btnRight = new JButton("");
	private SimpleGraphicsPanel graphicsPanel = new SimpleGraphicsPanel();
	private Game game = new Game();
	private Configs config = null;

	PlayPanel(Configs conf){
		config = conf;
		config.prepareNextLevel();
		game.setMap(config.getMap());
        game.setAgents(config.getAgents());
        game.setKey(config.getKey());
        game.setKeyTaken(false);
        game.gameStatus = Game.status.PLAYING;
		init();
		graphicsPanel.setMap(game.getMap());
		graphicsPanel.repaint();
	}
	public void init(){
		setLayout(new BorderLayout());
		setBackground(Color.BLUE);
		add(graphicsPanel,BorderLayout.CENTER);
		
		btnUp.setIcon(new ImageIcon(Button.class.getResource("/assets/arrow_up.png")));
		btnLeft.setIcon(new ImageIcon(Button.class.getResource("/assets/arrow_left.png")));
		btnRight.setIcon(new ImageIcon(Button.class.getResource("/assets/arrow_right.png")));
		btnDown.setIcon(new ImageIcon(Button.class.getResource("/assets/arrow_down.png")));
		
		moveButtonsPanel.add(btnUp, BorderLayout.NORTH);
		moveButtonsPanel.add(btnLeft, BorderLayout.WEST);
		moveButtonsPanel.add(btnRight, BorderLayout.EAST);
		moveButtonsPanel.add(btnDown, BorderLayout.SOUTH);
		add(moveButtonsPanel,BorderLayout.EAST);
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
