package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dkeep.logic.Configs;
import dkeep.logic.Game;

public class PlayPanel extends JPanel implements MouseListener, KeyListener{
	JPanel moveButtonsPanel = new JPanel(new BorderLayout());
	JPanel northPanel = new JPanel (new FlowLayout());
	JButton btnUp = new JButton("");
	JButton btnLeft = new JButton("");
	JButton btnDown = new JButton("");
	JButton btnRight = new JButton("");
	JLabel gameStatsLlb = new JLabel("Try to Escape");
	SimpleGraphicsPanel graphicsPanel = new SimpleGraphicsPanel();
	Game game;

	PlayPanel(){

		init();
	}
	
	void addListeners(){
		addKeyListener(this);
		addMouseListener(this);
	}
	
	void resetGameStatusLabel(){
		gameStatsLlb.setText("Try To Escape");
		gameStatsLlb.setBackground(Color.LIGHT_GRAY);
	}
	
	
	public void init(){
		gameStatsLlb.setOpaque(true);
		graphicsPanel.setFocusable(true);
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


		northPanel.add(gameStatsLlb);
		add(northPanel,BorderLayout.NORTH);
		btnUp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkGameStatus(graphicsPanel.moveAgents_GUI('w'));
				requestFocusInWindow();
			}

		});
		btnRight.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkGameStatus(graphicsPanel.moveAgents_GUI('d'));
				requestFocusInWindow();
			}

		});
		btnLeft.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkGameStatus(graphicsPanel.moveAgents_GUI('a'));
				requestFocusInWindow();
			}

		});
		btnDown.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkGameStatus(graphicsPanel.moveAgents_GUI('s'));
				requestFocusInWindow();
			}

		});
	}

	public void checkGameStatus(int val){
		if (val > 0){
			disableMoveButtons();
			removeKeyListener(this);
			removeMouseListener(this);
			if (val == 1){
				gameStatsLlb.setBackground(Color.RED);
				gameStatsLlb.setText("You have been captured, go Back to Try Again");
			}else{
				gameStatsLlb.setBackground(Color.GREEN);
				gameStatsLlb.setText("You have escaped, congrats!");
			}
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

	public void setGame(Game g){
		game = g;
		graphicsPanel.setGame(game);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		requestFocusInWindow();

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		requestFocusInWindow();

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {

		switch(e.getKeyCode()){ 
		case KeyEvent.VK_LEFT:
			checkGameStatus(graphicsPanel.moveAgents_GUI('a'));;
			break; 
		case KeyEvent.VK_RIGHT:
			checkGameStatus(graphicsPanel.moveAgents_GUI('d'));; break;  
		case KeyEvent.VK_UP:
			checkGameStatus(graphicsPanel.moveAgents_GUI('w'));;break; 
		case KeyEvent.VK_DOWN:
			checkGameStatus(graphicsPanel.moveAgents_GUI('s'));; break; 
		}


	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}







}
