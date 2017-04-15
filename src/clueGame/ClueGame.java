package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{
	Board board;
	private MyCardsGUI cardsGUI;
	private DetectiveNotesDialog Detective;
	static String message = "You are Professor Plum, press Next Player to begin play";
	static String title = "Welcome to Clue";
	private ArrayList<Player> players;

	private ArrayList<String> person;
	private ArrayList<String> rooms;
	private ArrayList<String> weapon;
	JTextArea peopleText;
	JTextArea roomsText;
	JTextArea weaponsText;

	public ClueGame() {
		ControlGUI gui = new ControlGUI();
		setSize(900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Board();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.deal();
		
		players = board.getPlayers();
		
		cardsGUI = new MyCardsGUI(players.get(1));
		cardsGUI.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(FileMenu());
		add(cardsGUI, BorderLayout.EAST);
	}

	private JMenu FileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(DetectiveOption());
		menu.add(ExitOption());
		return menu;
	}

	private JMenuItem DetectiveOption() {
		JMenuItem option = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {

			@Override

			public void actionPerformed(ActionEvent arg0) {
				Detective = new DetectiveNotesDialog();
				Detective.setVisible(true);
			}
		}
		option.addActionListener(new MenuItemListener());
		return option;
	}

	private JMenuItem ExitOption() {
		JMenuItem option = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {

			@Override

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		}
		option.addActionListener(new MenuItemListener());
		return option;
	}



	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		JOptionPane pane = new JOptionPane();
		pane.showMessageDialog(game, message, title, JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);
	}

}
