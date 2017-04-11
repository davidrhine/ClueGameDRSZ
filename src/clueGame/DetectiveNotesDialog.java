package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog{



	public DetectiveNotesDialog() {
		setSize(600,600);
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		setLayout(new GridLayout(3,2));

		JPanel people = new JPanel();
		people = peoplePanel(board.getPlayers());
		JPanel peopleGuess = new JPanel();
		peopleGuess = peopleDropDown(board.getPlayers());
		JPanel weapons = new JPanel();
		weapons = weaponsPanel(board.getWeapons());
		JPanel weaponsGuess = new JPanel();
		weaponsGuess = weaponDropDown(board.getWeapons());
		JPanel room = new JPanel();
		room = roomPanel(board.getRooms());
		JPanel roomGuess = new JPanel();
		roomGuess = roomDropDown(board.getRooms());
		add(people);
		add(peopleGuess);
		add(weapons);
		add(weaponsGuess);
		
		add(room);
		
		add(roomGuess);



	}
	public JPanel peoplePanel (ArrayList<Player> players) {
		JPanel people = new JPanel();
		ArrayList<JCheckBoxMenuItem> peopleList = new ArrayList<JCheckBoxMenuItem>();
		for (Player p : players) {
			peopleList.add(new JCheckBoxMenuItem(p.getPlayerName()));
		}
		for (JCheckBoxMenuItem w : peopleList) {
			people.add(w);
		}
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		return people;
	}

	public JPanel weaponsPanel (Set<Card> weapons) {
		JPanel weapon = new JPanel();
		ArrayList<JCheckBoxMenuItem> weaponList = new ArrayList<JCheckBoxMenuItem>();
		for (Card w : weapons) {
			weaponList.add(new JCheckBoxMenuItem(w.getCardName()));
		}
		for (JCheckBoxMenuItem w : weaponList) {
			weapon.add(w);
		}
		weapon.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		return weapon;
	}

	public JPanel roomPanel (Set<Card> rooms) {
		JPanel room = new JPanel();
		for (Card r : rooms) {
			room.add(new JCheckBoxMenuItem(r.getCardName()));
		}
		room.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		return room;
	}
	
	public JPanel peopleDropDown(ArrayList<Player> players) {
		JPanel peopleDropDown = new JPanel();
		JComboBox<String> people = new JComboBox<String>();
		for (Player p : players) {
			people.addItem(p.getPlayerName());
		}
		peopleDropDown.add(people);
		peopleDropDown.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		
		return peopleDropDown;
	}
	
	public JPanel weaponDropDown(Set<Card> weapons) {
		JPanel weaponDropDown = new JPanel();
		JComboBox<String> weaponsBox = new JComboBox<String>();
		for (Card w : weapons) {
			weaponsBox.addItem(w.getCardName());
		}
		weaponDropDown.add(weaponsBox);
		weaponDropDown.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		
		return weaponDropDown;
	}
	
	public JPanel roomDropDown(Set<Card> rooms) {
		JPanel roomDropDown = new JPanel();
		JComboBox<String> roomsBox = new JComboBox<String>();
		for (Card r : rooms) {
			roomsBox.addItem(r.getCardName());
		}
		roomDropDown.add(roomsBox);
		roomDropDown.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		
		return roomDropDown;
	}


	public static void main(String[] args){
		DetectiveNotesDialog menu = new DetectiveNotesDialog();
		menu.setVisible(true);
	}


}
