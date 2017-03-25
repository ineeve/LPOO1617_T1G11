package dkeep.gui;

import dkeep.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PlayPanel extends JPanel implements MouseListener, KeyListener{
	private JPanel moveButtonsPanel = new JPanel(new BorderLayout());
	private JPanel northPanel = new JPanel (new FlowLayout());
	private JButton btnUp = new JButton("");
	private JButton btnLeft = new JButton("");
	private JButton btnDown = new JButton("");
	private JButton btnRight = new JButton("");
	private JLabel gameStatsLlb = new JLabel("Try to Escape");
	private BoardGraphics graphicsPanel;

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
	
	
	private void init(){
		graphicsPanel = new BoardGraphics();
		gameStatsLlb.setOpaque(true);
		graphicsPanel.setFocusable(true);
		setLayout(new BorderLayout());
		setBackground(Color.BLUE);
		add(graphicsPanel,BorderLayout.CENTER);

		btnUp.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_up.png")));
		btnLeft.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_left.png")));
		btnRight.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_right.png")));
		btnDown.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_down.png")));

		moveButtonsPanel.add(btnUp, BorderLayout.NORTH);
		moveButtonsPanel.add(btnLeft, BorderLayout.WEST);
		moveButtonsPanel.add(btnRight, BorderLayout.EAST);
		moveButtonsPanel.add(btnDown, BorderLayout.SOUTH);
		add(moveButtonsPanel,BorderLayout.EAST);


		northPanel.add(gameStatsLlb);
		add(northPanel,BorderLayout.NORTH);

		initListeners();
	}

	private void initListeners(){
		btnUp.addActionListener(arg0 -> {
			checkGameStatus(graphicsPanel.moveAgents_GUI('w'));
			requestFocusInWindow();
		});
		btnRight.addActionListener(arg0 -> {
			checkGameStatus(graphicsPanel.moveAgents_GUI('d'));
			requestFocusInWindow();
		});
		btnLeft.addActionListener(arg0 -> {
			checkGameStatus(graphicsPanel.moveAgents_GUI('a'));
			requestFocusInWindow();
		});
		btnDown.addActionListener(arg0 -> {
			checkGameStatus(graphicsPanel.moveAgents_GUI('s'));
			requestFocusInWindow();
		});
	}

	private void checkGameStatus(int val){
		if (val > 0){
			disableMoveButtons();
			removeKeyListener(this);
			removeMouseListener(this);
			if (val == 3){
				gameStatsLlb.setBackground(Color.RED);
				gameStatsLlb.setText("You have been captured, go Back to Try Again");
			}
			else if(val == 2){
				gameStatsLlb.setBackground(Color.GREEN);
				gameStatsLlb.setText("You have escaped, congrats!");
			}
		}
	}

	private void disableMoveButtons(){

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
		graphicsPanel.setGame(g);
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
			checkGameStatus(graphicsPanel.moveAgents_GUI('a'));
			break; 
		case KeyEvent.VK_RIGHT:
			checkGameStatus(graphicsPanel.moveAgents_GUI('d'));
			break;
		case KeyEvent.VK_UP:
			checkGameStatus(graphicsPanel.moveAgents_GUI('w'));
			break;
		case KeyEvent.VK_DOWN:
			checkGameStatus(graphicsPanel.moveAgents_GUI('s'));
			break;
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
