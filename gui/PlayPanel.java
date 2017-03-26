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
	private BoardGraphics graphicsPanel;
	ImageIcon defeatGIF = createImageIcon("../assets/defeat_banner.gif");
	ImageIcon victoryGIF = createImageIcon("../assets/victory_banner.gif");
	ImageIcon runGIF = createImageIcon("../assets/run_banner.gif");
	private JLabel gameStatsLlb = new JLabel(runGIF);
	
	PlayPanel(){
		init();
	}

	void addListeners(){
		addKeyListener(this);
		addMouseListener(this);
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}

	public void resetGameStatusLabel(){
		gameStatsLlb.setIcon(runGIF);
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
				gameStatsLlb.setIcon(defeatGIF);
			}
			else if(val == 2){
				gameStatsLlb.setIcon(victoryGIF);
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
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

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
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
