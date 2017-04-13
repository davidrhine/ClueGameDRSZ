package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyCardsGUI extends JPanel{

	String person;
	String rooms;
	String weapon;
	JTextField peopleText;
	JTextField roomsText;
	JTextField weaponsText;
	
	public MyCardsGUI(Player p) {
		Set<Card> tempCards = new HashSet();
		tempCards = p.getCards();
		for (Card c : tempCards) {
			if (c.getCardType() == CardType.PERSON) {
				person = c.getCardName();
			}
			else if (c.getCardType() == CardType.ROOM) {
				rooms = c.getCardName();
			}
			else if (c.getCardType() == CardType.WEAPON) {
				weapon = c.getCardName();
			}
		}
	
	
	setLayout(new GridLayout(3,1));
	setSize(300,300);
	
//	JPanel main = PeoplePanel();
//	add(main);
//	main = RoomPanel();
//	add(main);
//	main = WeaponPanel();
//	add(main);
	
	
	}
	
//	private JPanel WeaponPanel() {
//		JPanel Card = new JPanel();
//	 	weaponsText = new JTextField(20);
//	 	weaponsText.setText(weapon);
//	 	weaponsText.setEditable(true);
//	 	weaponsText.setPreferredSize(new Dimension(20, 20));
//		
//		return Card;	
//	}
//	
//	private JPanel PeoplePanel() {
//		JPanel people = new JPanel();
//	 	peopleText = new JTextField(20);
//	 	peopleText.setText(person);
//	 	peopleText.setEditable(true);
//	 	peopleText.setPreferredSize(new Dimension(20, 20));
//		return people;
//		
//	}
//	
//	private JPanel RoomPanel() {
//		JPanel room = new JPanel();
//	 	roomsText = new JTextField(20);
//	 	roomsText.setText(rooms);
//	 	roomsText.setEditable(true);
//	 	roomsText.setPreferredSize(new Dimension(20, 20));
//		return room;
//	}
//	
//	public static void main(String[] args) {
//		
//		MyCardsGUI pane = new MyCardsGUI();
//		pane.setVisible(true);
//	}

}
