package dkeep.gui;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

class InitialMenuPanel extends JPanel{


	private BufferedImage background;
	private SpecialButton btnSettings;
	private SpecialButton btnEditMap;
	private SpecialButton btnPlay;
	private SpecialButton btnExit;

	public InitialMenuPanel(SpecialButton btnSettings,SpecialButton btnCreateMap,SpecialButton btnPlay,SpecialButton btnExit){
		setBorder(new EmptyBorder(400, 0, 0, 0));
		this.btnSettings = btnSettings; this.btnEditMap = btnCreateMap; this.btnPlay = btnPlay; this.btnExit = btnExit;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		prepareBtnPlay();

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setEnabled(false);
		add(rigidArea);

		prepareBtnSettings();

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_1.setEnabled(false);
		add(rigidArea_1);

		prepareBtnEditMap();
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_2.setEnabled(false);
		add(rigidArea_2);
		
		prepareBtnExit();

		init();
	}

	private void prepareBtnPlay() {
		this.btnPlay.setBackground(Color.BLACK);
		this.btnPlay.setIcon(new ImageIcon(InitialMenuPanel.class.getResource("/dkeep/assets/playBtn.png")));
		this.btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(this.btnPlay);
	}

	private void prepareBtnSettings() {
		this.btnSettings.setBackground(Color.BLACK);
		this.btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.btnSettings.setIcon(new ImageIcon(InitialMenuPanel.class.getResource("/dkeep/assets/settingsBtn.png")));
		add(this.btnSettings);

	}
	
	private void prepareBtnEditMap() {
		this.btnEditMap.setBackground(Color.BLACK);
		this.btnEditMap.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.btnEditMap.setIcon(new ImageIcon(InitialMenuPanel.class.getResource("/dkeep/assets/editMapBtn.png")));
		add(this.btnEditMap);
	}
	
	private void prepareBtnExit() {
		this.btnExit.setBackground(Color.BLACK);
		this.btnExit.setIcon(new ImageIcon(InitialMenuPanel.class.getResource("/dkeep/assets/exitBtn.png")));
		this.btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnExit);
	}



	private void init(){
		try {
			background = ImageIO.read(new File("src/dkeep/assets/wallpaper.jpg"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return background == null ? new Dimension(400, 300): new Dimension(background.getWidth(), background.getHeight());
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
