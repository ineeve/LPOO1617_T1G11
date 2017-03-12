package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

public class Game_GUI {

	private JFrame frmEscapeGame;
	private JTextField textField;
	private JComboBox comboBox;
	private JLabel gameStatsLlb;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel panel;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnDown;
	private JButton btnRight;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_GUI window = new Game_GUI();
					window.frmEscapeGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Game_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEscapeGame = new JFrame();
		frmEscapeGame.setTitle("Escape Game");
		frmEscapeGame.setBounds(100, 100, 575, 358);
		frmEscapeGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEscapeGame.getContentPane().setLayout(new MigLayout("", "[grow][grow][][][][][][grow][][][][][]", "[][][][][grow][][grow][][][][][][][][]"));
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		frmEscapeGame.getContentPane().add(lblNumberOfOgres, "cell 0 0,sizegroupx 1,alignx left,sizegroupy 1");
		
		textField = new JTextField();
		frmEscapeGame.getContentPane().add(textField, "cell 1 0");
		textField.setColumns(10);
		
		JLabel lblGuardPersonality = new JLabel("Guard Personality");
		frmEscapeGame.getContentPane().add(lblGuardPersonality, "cell 0 1,sizegroupx 1,alignx left,sizegroupy 1");
		
		comboBox = new JComboBox();
		frmEscapeGame.getContentPane().add(comboBox, "cell 1 1");
		
		btnNewButton = new JButton("New Game");
		frmEscapeGame.getContentPane().add(btnNewButton, "cell 6 2 5 1,sizegroupx 2,alignx center,sizegroupy 2,aligny center");
		
		scrollPane = new JScrollPane();
		frmEscapeGame.getContentPane().add(scrollPane, "cell 0 4 5 10,grow");
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		panel = new JPanel();
		frmEscapeGame.getContentPane().add(panel, "cell 7 5 5 4,alignx center");
		panel.setLayout(new BorderLayout(0, 0));
		
		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		panel.add(btnUp, BorderLayout.NORTH);
		
		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		panel.add(btnLeft, BorderLayout.CENTER);
		
		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		panel.add(btnDown, BorderLayout.SOUTH);
		
		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		panel.add(btnRight, BorderLayout.EAST);
		
		gameStatsLlb = new JLabel("Game Status");
		frmEscapeGame.getContentPane().add(gameStatsLlb, "cell 0 14 2 1");
		
		btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		frmEscapeGame.getContentPane().add(btnNewButton_1, "cell 7 14 5 1,sizegroupx 2,alignx center,sizegroupy 2");
	}
}
