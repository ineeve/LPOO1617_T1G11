package dkeep.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MoveArrowsPanel extends JPanel {

	BufferedImage background;
	
	JButton arrow_left;
	JButton arrow_right;
	JButton arrow_down;
	JButton arrow_up;
	GridBagLayout gridBagLayout = new GridBagLayout();
	
	
	public MoveArrowsPanel(JButton arrow_up, JButton arrow_left, JButton arrow_right, JButton arrow_down) {
		super();
		this.setMinimumSize(new Dimension (300,0));
		this.arrow_left = arrow_left;
		this.arrow_right = arrow_right;
		this.arrow_down = arrow_down;
		this.arrow_up = arrow_up;
		init();
	}

	public void init(){
		readBackground();
		setLayoutConfiguration();
		setIconsOnArrows();
		setArrowUpLayout();
		setArrowLeftLayout();
		setArrowRightLayout();
		setArrowDownLayout();
		setButtonsBackgrounds();
	}
	private void setButtonsBackgrounds() {
		arrow_left.setBackground(Color.BLACK);
		arrow_up.setBackground(Color.BLACK);
		arrow_right.setBackground(Color.BLACK);
		arrow_down.setBackground(Color.BLACK);
	}

	private void readBackground(){
		try {
			background = ImageIO.read(new File("src/dkeep/assets/Controller.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void setArrowDownLayout() {
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.fill = GridBagConstraints.BOTH;
		gbc_btnDown.insets = new Insets(0, 0, 0, 5);
		gbc_btnDown.gridx = 1;
		gbc_btnDown.gridy = 2;
		add(arrow_down, gbc_btnDown);
	}

	private void setArrowRightLayout() {
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.fill = GridBagConstraints.BOTH;
		gbc_btnRight.insets = new Insets(0, 0, 5, 0);
		gbc_btnRight.gridx = 2;
		gbc_btnRight.gridy = 1;
		add(arrow_right, gbc_btnRight);
		
	}

	private void setArrowLeftLayout() {
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.fill = GridBagConstraints.BOTH;
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.gridx = 0;
		gbc_btnLeft.gridy = 1;
		add(arrow_left, gbc_btnLeft);
		
	}

	private void setArrowUpLayout() {
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.BOTH;
		gbc_btnUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnUp.gridx = 1;
		gbc_btnUp.gridy = 0;
		add(arrow_up, gbc_btnUp);
	}

	private void setLayoutConfiguration() {
		gridBagLayout.columnWidths = new int[]{50,50,50};
		gridBagLayout.rowHeights = new int[] {50,50,50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		
	}

	private void setIconsOnArrows(){
		arrow_up.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_up.png")));
		arrow_left.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_left.png")));
		arrow_right.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_right.png")));
		arrow_down.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_down.png")));
	}

	@Override
	public Dimension getPreferredSize() {
		return background == null ? new Dimension(400, 300): new Dimension(background.getWidth() + 100, background.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		Graphics2D g2 = (Graphics2D) g;
		g2.fill( new Rectangle(0, 0, d.width, d.height) );
		g.drawImage(background, 0, 0, d.width, d.height, null);
	}

}
