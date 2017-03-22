package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JButton;

public class CreateMapPanel extends JPanel implements MouseListener {

	ArrayList<JButton> mapComponents;
	EditMapGraphicsPanel editPanel = new EditMapGraphicsPanel();
	JPanel componentsPanel = new JPanel();
	
	CreateMapPanel(){
		init();
	}
	public void init(){
		setLayout(new BorderLayout());
		add(editPanel,BorderLayout.CENTER);
		add(componentsPanel, BorderLayout.EAST);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	
}
