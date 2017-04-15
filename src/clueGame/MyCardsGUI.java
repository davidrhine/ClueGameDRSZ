package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCardsGUI extends JPanel{

	ArrayList<String> personCards;
	ArrayList<String> roomCards;
	ArrayList<String> weaponCards;
	JTextArea peopleText;
	JTextArea roomsText;
	JTextArea weaponsText;

	public MyCardsGUI(Player p) {
		Set<Card> tempCards = new HashSet();
		
		tempCards = p.getCards();
		personCards = new ArrayList<String>();
		roomCards = new ArrayList<String>();
		weaponCards = new ArrayList<String>();
		for (Card c : tempCards) {
			if (c.getCardType() == CardType.PERSON) {
				personCards.add(c.getCardName());
			}
			else if (c.getCardType() == CardType.ROOM) {
				roomCards.add(c.getCardName());
			}
			else if (c.getCardType() == CardType.WEAPON) {
				weaponCards.add( c.getCardName());
			}
		}


		setLayout(new GridLayout(3,1));
		setSize(300,300);

		JPanel main = PeoplePanel();
		add(main);
		main = RoomPanel();
		add(main);
		main = WeaponPanel();
		add(main);


	}

	private JPanel WeaponPanel() {
		JPanel Card = new JPanel();
		weaponsText = new JTextArea(10,10);
		
		String weaponsOutput = "";
		for (String w : weaponCards) {
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
		for (String w : personCards) {
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
		for (String w : roomCards) {
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
