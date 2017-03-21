package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dkeep.logic.Configs;
import dkeep.logic.Game;

public class PlayPanel extends JPanel{
	JPanel moveButtonsPanel = new JPanel(new BorderLayout());
	JPanel northPanel = new JPanel (new FlowLayout());
	JButton btnUp = new JButton("");
	JButton btnLeft = new JButton("");
	JButton btnDown = new JButton("");
	JButton btnRight = new JButton("");
	JButton btnNewGame = new JButton("New Game");
	JLabel gameStatsLlb = new JLabel("Game Status");
	SimpleGraphicsPanel graphicsPanel = new SimpleGraphicsPanel();
	Configs config = new Configs(0);

	PlayPanel(){
		init();
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

		northPanel.add(btnNewGame);
		northPanel.add(gameStatsLlb);
		add(northPanel,BorderLayout.NORTH);

		btnNewGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameStatsLlb.setText("You can play now");
				graphicsPanel.setConfig(config);
				
				btnNewGame.setEnabled(false);
			}

		});
		btnUp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameStatsLlb.setText("Moving Agents");
				checkGameStatus(graphicsPanel.moveAgents_GUI('w'));
				gameStatsLlb.setText("Your turn");
			}

		});
		btnRight.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameStatsLlb.setText("Moving Agents");
				checkGameStatus(graphicsPanel.moveAgents_GUI('d'));
				gameStatsLlb.setText("Your turn");
			}

		});
		btnLeft.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameStatsLlb.setText("Moving Agents");
				checkGameStatus(graphicsPanel.moveAgents_GUI('a'));
				gameStatsLlb.setText("Your turn");
			}

		});
		btnDown.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameStatsLlb.setText("Moving Agents");
				checkGameStatus(graphicsPanel.moveAgents_GUI('s'));
				gameStatsLlb.setText("Your turn");
			}

		});

		
		graphicsPanel.requestFocusInWindow();
	}

	public void checkGameStatus(int val){
		if (val > 0){
			disableMoveButtons();
			if (val == 1){
				gameStatsLlb.setText("You have been captured, press New Game to Try Again");
			}else{
				gameStatsLlb.setText("You have escaped, congrats!");
				
			}
			;
		}
	}

	public void disableMoveButtons(){

		btnUp.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
		btnDown.setEnabled(false);
	}
	public void enableMoveButtons(){
		btnUp.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
		btnDown.setEnabled(true);
		graphicsPanel.repaint();
	}

	public void setConfigs(Configs conf){
		config = conf;
	}






}
