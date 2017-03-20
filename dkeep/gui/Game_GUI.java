package dkeep.gui;

import dkeep.logic.Configs;
import dkeep.logic.Game;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game_GUI {

	private JFrame frmEscapeGame;
	private JTextField numberOfOgres;
	private JComboBox personalityChooser;
	private JLabel gameStatsLlb;
	private PlayPanel playPanel;
	private InitialMenuPanel menuPanel = new InitialMenuPanel();

	//Logic Variables
	private Game game;
	private Configs config;

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
		
		menuPanel.setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmEscapeGame = new JFrame();
		frmEscapeGame.setTitle("Escape Game");
		frmEscapeGame.setBounds(100, 100, 465, 332);
		frmEscapeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frmEscapeGame.getContentPane().setLayout(null);
		frmEscapeGame.getContentPane().add(menuPanel);
		menuPanel.setVisible(true);
		
		/*
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(0, 0, 0, 0);
		frmEscapeGame.getContentPane().add(lblNumberOfOgres);

		numberOfOgres = new JTextField(10);
		numberOfOgres.setBounds(0, 0, 0, 0);
		frmEscapeGame.getContentPane().add(numberOfOgres);
		PlainDocument doc = (PlainDocument) numberOfOgres.getDocument();
		doc.setDocumentFilter(new MyIntFilter());


		JLabel lblGuardPersonality = new JLabel("Guard Personality");
		lblGuardPersonality.setBounds(0, 0, 0, 0);
		frmEscapeGame.getContentPane().add(lblGuardPersonality);

		String[] personalities = {"Drunken","Rookie","Suspicious"};
		personalityChooser = new JComboBox(personalities);
		personalityChooser.setBounds(0, 0, 0, 0);
		personalityChooser.setSelectedIndex(0);

		frmEscapeGame.getContentPane().add(personalityChooser);
*/
		/*btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameStatsLlb.setText("You can play now!");
				game = new Game();
				config = new Configs(1);
				Configs.GUARDPERSONALITY = personalityChooser.getSelectedIndex();
				if (numberOfOgres.getText().equals("")){
					numberOfOgres.setText("1");
				}
				Configs.NUMBEROFOGRES = Integer.parseInt(numberOfOgres.getText());
				config.prepareNextLevel();
				game.setMap(config.getMap());
				game.setAgents(config.getAgents());
				game.setKey(config.getKey());
				game.setKeyTaken(false);
				Game.gameStatus = Game.status.PLAYING;
				/*while (game.isGameOver() == false) {
		            displayBoard(game.getMap());
		            
		        }
				displayBoard(game.getMap());
				System.out.println("You have been captured");

			}
		});
		*/

		/*scrollPane = new JScrollPane();
		frmEscapeGame.getContentPane().add(scrollPane, "cell 0 4 5 10,grow");



		textArea = new JTextArea();
		textArea.setFont(new Font("Courier New",Font.PLAIN,14));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);*/
		
		//frmEscapeGame.pack();
		frmEscapeGame.setVisible(true);
		
		

		
		
	}
	class MyIntFilter extends DocumentFilter {
		@Override
		public void insertString(FilterBypass fb, int offset, String string,
				AttributeSet attr) throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offset, string);

			if (test(sb.toString())) {
				super.insertString(fb, offset, string, attr);
			} else {
				// warn the user and don't allow the insert
			}
		}

		private boolean test(String text) {
			try {
				int valueRead = Integer.parseInt(text);
				if (valueRead < 6 && valueRead > 0){
					return true;
				}
				return false;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text,
				AttributeSet attrs) throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.replace(offset, offset + length, text);

			if (test(sb.toString())) {
				super.replace(fb, offset, length, text, attrs);
			} else {
				// warn the user and don't allow the insert
			}

		}

		@Override
		public void remove(FilterBypass fb, int offset, int length)
				throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.delete(offset, offset + length);

			if(sb.toString().length() == 0) {
				super.replace(fb, offset, length, "", null); 
			} 
			else { 
				if (test(sb.toString())) { 
					super.remove(fb, offset, length); 
				} 
				else { 
					// warn the user and don't allow the insert } }
				}
			}

		}
	}
}
