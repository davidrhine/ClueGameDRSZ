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
	private JPanel cardsGUI;
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
		
		cardsGUI = new JPanel();
		cardsGUI.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(FileMenu());

		Set<Card> tempCards = new HashSet();
		Player p = players.get(1);
		tempCards = p.getCards();
		
		person = new ArrayList<String>();
		rooms = new ArrayList<String>();
		weapon = new ArrayList<String>();
		for (Card c : tempCards) {
			if (c.getCardType() == CardType.PERSON) {
				person.add(c.getCardName());
			}
			else if (c.getCardType() == CardType.ROOM) {
				rooms.add(c.getCardName());
			}
			else if (c.getCardType() == CardType.WEAPON) {
				weapon.add( c.getCardName());
			}
		}


		JPanel main = PeoplePanel();
		cardsGUI.add(main);
		main = RoomPanel();
		cardsGUI.add(main);
		main = WeaponPanel();
		cardsGUI.add(main);
		cardsGUI.setLayout(new GridLayout(3,1));
		cardsGUI.setSize(10,300);
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



	private JPanel WeaponPanel() {
		JPanel Card = new JPanel();
		weaponsText = new JTextArea(10,10);
		
		String weaponsOutput = "";
		for (String w : weapon) {
			weaponsOutput += w + "\n";
		}
		
		weaponsText.setText(weaponsOutput);
		weaponsText.setEditable(true);
		weaponsText.setPreferredSize(new Dimension(20, 20));
		Card.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		Card.add(weaponsText);
		
		return Card;	
	}

	private JPanel PeoplePanel() {
		JPanel people = new JPanel();
		peopleText = new JTextArea(10,10);
		String peopleOutput = "";
		for (String w : person) {
			peopleOutput += w + "\n";
		}
		
		peopleText.setText(peopleOutput);
		peopleText.setEditable(true);
		peopleText.setPreferredSize(new Dimension(20, 20));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		people.add(peopleText);
		
		return people;

	}

	private JPanel RoomPanel() {
		JPanel room = new JPanel();
		roomsText = new JTextArea(10,10);
		String roomsOutput = "";
		for (String w : rooms) {
			roomsOutput += w + "\n";
		}
		roomsText.setText(roomsOutput);
		roomsText.setEditable(true);
		roomsText.setPreferredSize(new Dimension(20, 20));
		room.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		room.add(roomsText);
		return room;
	}



















}
