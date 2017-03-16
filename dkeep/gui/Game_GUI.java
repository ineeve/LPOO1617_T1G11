package dkeep.gui;

import javax.swing.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;
import dkeep.logic.Configs;
import dkeep.logic.Game;
public class Game_GUI {

	private JFrame frmEscapeGame;
	private JTextField numberOfOgres;
	private JComboBox personalityChooser;
	private JLabel gameStatsLlb;
	private JButton btnNewGame;
	private JButton btnExit;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JPanel panel;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnDown;
	private JButton btnRight;

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


	public void displayBoard(char [][] matrix){
		textArea.setText("");
		String currentLine;
		for (int i = 0; i < matrix.length; i++) {
			currentLine = "";
			for (int j = 0; j < matrix[i].length; j++) {
				currentLine += " " + matrix[i][j];
			}
			currentLine += "\n";
			textArea.append(currentLine);
		}
	}
	
	public void moveAgents_GUI(char heroDirection){
		
		if (game.moveHero(heroDirection)==1){ //change To next level
			config.prepareNextLevel();
            game.setMap(config.getMap());
            game.setAgents(config.getAgents());
            game.setKey(config.getKey());
            game.setKeyTaken(false);
            displayBoard(game.getMap());
            return;
		}
		displayBoard(game.getMap());
		if (checkForGameOver() == true){
			disableMoveButtons();
		}
		else{
			game.moveBots();
			displayBoard(game.getMap());
		}
		if (checkForGameOver() == true){
			disableMoveButtons();
		}
	}


	public boolean checkForGameOver(){
		game.isGameOver(); //To update status value in game object.
		if (game.getGameStatus() == Game.status.DEFEAT){
			gameStatsLlb.setText("You have been captured");
			return true;
		}
		else if (game.getGameStatus() == Game.status.VICTORY){
			gameStatsLlb.setText("You have escaped, congrats!");
			return true;
		}
		return false;
	}

	public void disableMoveButtons(){
		btnUp.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
		btnDown.setEnabled(false);
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
		frmEscapeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEscapeGame.getContentPane().setLayout(new MigLayout("", "[grow][grow][][][][][][grow][][][][][]", "[][][][][grow][][grow][][][][][][][][]"));

		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		frmEscapeGame.getContentPane().add(lblNumberOfOgres, "cell 0 0,sizegroupx 1,alignx left,sizegroupy 1");

		numberOfOgres = new JTextField(10);
		frmEscapeGame.getContentPane().add(numberOfOgres, "cell 1 0");
		PlainDocument doc = (PlainDocument) numberOfOgres.getDocument();
		doc.setDocumentFilter(new MyIntFilter());


		JLabel lblGuardPersonality = new JLabel("Guard Personality");
		frmEscapeGame.getContentPane().add(lblGuardPersonality, "cell 0 1,sizegroupx 1,alignx left,sizegroupy 1");

		String[] personalities = {"Drunken","Rookie","Suspicious"};
		personalityChooser = new JComboBox(personalities);
		personalityChooser.setSelectedIndex(0);

		frmEscapeGame.getContentPane().add(personalityChooser, "cell 1 1");

		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUp.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
				btnDown.setEnabled(true);
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
		            
		        }*/
				displayBoard(game.getMap());
				System.out.println("You have been captured");

			}
		});
		frmEscapeGame.getContentPane().add(btnNewGame, "cell 6 2 5 1,sizegroupx 2,alignx center,sizegroupy 2,aligny center");

		/*scrollPane = new JScrollPane();
		frmEscapeGame.getContentPane().add(scrollPane, "cell 0 4 5 10,grow");



		textArea = new JTextArea();
		textArea.setFont(new Font("Courier New",Font.PLAIN,14));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);*/
		
		JPanel graphicsPanel = new SimpleGraphicsPanel(); 
		frmEscapeGame.getContentPane().add(graphicsPanel,"cell 0 4 5 10,grow");
		graphicsPanel.repaint();

		panel = new JPanel();
		frmEscapeGame.getContentPane().add(panel, "cell 7 5 5 4,alignx center");
		panel.setLayout(new BorderLayout(0, 0));

		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveAgents_GUI('w');
			}
		});
		btnUp.setEnabled(false);
		panel.add(btnUp, BorderLayout.NORTH);

		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveAgents_GUI('a');
			}
		});
		btnLeft.setEnabled(false);
		panel.add(btnLeft, BorderLayout.CENTER);

		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveAgents_GUI('s');
			}
		});
		btnDown.setEnabled(false);
		panel.add(btnDown, BorderLayout.SOUTH);

		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			moveAgents_GUI('d');
			}
		});
		btnRight.setEnabled(false);
		panel.add(btnRight, BorderLayout.EAST);

		gameStatsLlb = new JLabel("Choose num. Ogres and Guard Personality and press New Game");
		frmEscapeGame.getContentPane().add(gameStatsLlb, "cell 0 14 2 1");

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		frmEscapeGame.getContentPane().add(btnExit, "cell 7 14 5 1,sizegroupx 2,alignx center,sizegroupy 2");
		frmEscapeGame.pack();
		
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
