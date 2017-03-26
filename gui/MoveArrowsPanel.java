package dkeep.gui;

import java.awt.Button;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MoveArrowsPanel extends JPanel {

	JButton arrow_left;
	JButton arrow_right;
	JButton arrow_down;
	JButton arrow_up;
	GridBagLayout gridBagLayout = new GridBagLayout();
	public MoveArrowsPanel(JButton arrow_left, JButton arrow_right, JButton arrow_down, JButton arrow_up) {
		super();
		this.arrow_left = arrow_left;
		this.arrow_right = arrow_right;
		this.arrow_down = arrow_down;
		this.arrow_up = arrow_up;
		init();
	}

	public void init(){
		setLayoutConfiguration();
		setIconsOnArrows();
	}


	private void setLayoutConfiguration() {
		gridBagLayout.columnWidths = new int[]{50,50,50};
		gridBagLayout.rowHeights = new int[] {50,50,50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
	}

	private void setIconsOnArrows(){
		arrow_up.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_up.png")));
		arrow_left.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_left.png")));
		arrow_right.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_right.png")));
		arrow_down.setIcon(new ImageIcon(Button.class.getResource("/dkeep/assets/arrow_down.png")));
	}


}
