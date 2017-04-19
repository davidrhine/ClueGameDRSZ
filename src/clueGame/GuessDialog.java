package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GuessDialog extends JDialog implements ActionListener{
	
	private JTextField currentRoom;
	private JTextField person;
	private JTextField weapon;
	String savedRoomGuess;
	String savedPersonGuess;
	String savedWeaponGuess;
	JComboBox<String> playersDropdown;
	JComboBox<String> weaponsDropdown;
	JComboBox<String> roomsDropdown;
	JButton submit;
	
	Board board;
	
	class GetSelectedItem implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == playersDropdown) {
				savedPersonGuess = (String)playersDropdown.getSelectedItem();
				System.out.println(savedPersonGuess);
			}
			if (e.getSource() == roomsDropdown) {
				savedRoomGuess = (String)roomsDropdown.getSelectedItem();
			}
			if (e.getSource() == weaponsDropdown) {
				savedWeaponGuess = (String)weaponsDropdown.getSelectedItem(); 
		
			}
		//board.handleSuggestion(s, players.get())
		}
		
		
		
	}
	
	public GuessDialog(String roomEntered) {
		setSize(300,300);
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		setLayout(new GridLayout(4,2));
		
		JLabel roomLabel = new JLabel("Your room");
		currentRoom = new JTextField(20);
		currentRoom.setText(roomEntered);
		currentRoom.setEditable(false);
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		
		playersDropdown = new JComboBox<String>();
		playersDropdown = createPlayersDropdown(board.getPlayers());
		
		weaponsDropdown = new JComboBox<String>();
		weaponsDropdown = createWeaponsDropdown(board.getWeapons());
		
		submit = new JButton();
		submit = submitPanel();
		
		JButton cancel = new JButton();
		cancel = cancelPanel();
		
		submit.addActionListener(new GetSelectedItem());
		add(roomLabel);
		add(currentRoom);
		add(personLabel);
		add(playersDropdown);
		add(weaponLabel);
		add(weaponsDropdown);
		add(submit);
		add(cancel);
		
	}
	
	public GuessDialog() {
		setSize(300,300);
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		setLayout(new GridLayout(4,2));
		
		JLabel roomLabel = new JLabel("Your room");
		
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		
		JComboBox<String> playersDropdown = new JComboBox<String>();
		playersDropdown = createPlayersDropdown(board.getPlayers());
		
		JComboBox<String> weaponsDropdown = new JComboBox<String>();
		weaponsDropdown = createWeaponsDropdown(board.getWeapons());
		
		roomsDropdown = new JComboBox<String>();
		roomsDropdown = createRoomsDropdown(board.getRooms());
		
		JButton submit = new JButton();
		submit = submitPanel();
		
		JButton cancel = new JButton();
		cancel = cancelPanel();
		
		add(roomLabel);
		add(roomsDropdown);
		add(personLabel);
		add(playersDropdown);
		add(weaponLabel);
		add(weaponsDropdown);
		add(submit);
		add(cancel);
		
	}
	
	private JButton cancelPanel() {
		JButton cancel = new JButton("Cancel");
				
				
		return cancel;
	}

	private JButton submitPanel() {
		JButton submit = new JButton("Submit");		
		return submit;
	}
	
	public JComboBox<String> createPlayersDropdown(ArrayList<Player> players ){

		JComboBox<String> people = new JComboBox<String>();
		for (Player p : players) {
			people.addItem(p.getPlayerName());
		}

		return people;
	}
	
	public JComboBox<String> createWeaponsDropdown(Set<Card> weapons){

		JComboBox<String> weaponsDropdown = new JComboBox<String>();
		for (Card p : weapons) {
			weaponsDropdown.addItem(p.getCardName());
		}

		return weaponsDropdown;
	}
	
	public JComboBox<String> createRoomsDropdown(Set<Card> rooms){

		JComboBox<String> roomsDropdown = new JComboBox<String>();
		for (Card p : rooms) {
			roomsDropdown.addItem(p.getCardName());
		}

		return roomsDropdown;
	}
	
	public static void main(String[] args){
		GuessDialog main = new GuessDialog();
		main.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
