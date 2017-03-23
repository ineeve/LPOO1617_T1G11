package dkeep.gui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import dkeep.logic.maps.GameMap;
import dkeep.logic.maps.KeepMap;

import java.util.ArrayList;

public class EditMapGraphicsPanel extends JPanel implements MouseListener {

	GameMap keepLevel = new KeepMap();
	char [][] map = keepLevel.getMap();
	GridLayout gl = new GridLayout(map.length,map[0].length);

	EditMapGraphicsPanel(){
		init();
	}
	public void init(){
		setLayout(gl);
		addButtons();
	}
	public void addButtons(){
		for (int i = 0; i < gl.getRows();i++){
			for (int j = 0; j < gl.getColumns(); j++){
				switch (map[i][j]){
				case ' ':
					add(new SpecialButton(new Dimension(i,j),new ImageIcon(Button.class.getResource("/assets/floor.png")),' '));
					break;
				case 'X':
					add(new SpecialButton(new Dimension(i,j),new ImageIcon(Button.class.getResource("/assets/Horizontal_Wall.png")),'X'));
					break;
				case 'I':
					add(new SpecialButton(new Dimension(i,j),new ImageIcon(Button.class.getResource("/assets/door.png")),'I'));
					break;
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
