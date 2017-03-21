package experiments;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	BoardCell currentRoom;
	Set<Card> seen = new HashSet<Card>();
	Solution suggestion;

	public ComputerPlayer(String name, String color, String row, String col) {
		super(name, color, row, col);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell b : targets) {
			if (b.isRoom() && (currentRoom == null || b.getInitial() != currentRoom.getInitial())) {
				if (b.getColumn() == 18 && b.getRow() == 4) {
					System.out.println(currentRoom);
					System.out.println(currentRoom.getInitial());
					System.out.println(b.getInitial());
				}
				move(b.getRow(), b.getColumn());
				currentRoom = b;
				return b;
			}
		}

		int item = new Random().nextInt(targets.size());
		int i = 0;
		for (BoardCell b : targets) {
			if (i == item) {
				if (b.isRoom()) {
					currentRoom = b;
				}
				move(b.getRow(), b.getColumn());
				return b;
			}
			i++;
		}
		return null; // should never happen

	}

	public void revealCard(Card c) {
		seen.add(c);
	}

	public void makeAccusation() {

	}

	public void createSuggestion() {
		Card room = Board.getRoomWithInitial(currentRoom.getInitial());
		Card weapon = null;
		Card person = null;

		Set<Card> weaponsSeen = getWeaponsSeen();
		Set<Card> weapons = new HashSet<Card>(Board.getWeapons()); // make a
																	// copy
		weapons.removeAll(weaponsSeen);
		int item = new Random().nextInt(weapons.size());
		int i = 0;
		for (Card c : weapons) {
			if (i == item) {
				weapon = c;
				break;
			}
			i++;
		}

		Set<Card> peopleSeen = getPeopleSeen();
		Set<Card> people = new HashSet<Card>(Board.getPeople()); // make a copy
		people.removeAll(peopleSeen);
		item = new Random().nextInt(people.size());
		i = 0;
		for (Card c : people) {
			if (i == item) {
				person = c;
				break;
			}
			i++;
		}

		suggestion = new Solution(person, room, weapon);

	}

	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSeen(Set<Card> seen) {
		this.seen = seen;
	}

	public void setCurrentRoom(BoardCell currentRoom) {
		this.currentRoom = currentRoom;
	}

	public Set<Card> getPeopleSeen() {
		Set<Card> ret = new HashSet<Card>();
		for (Card c : seen) {
			if (c.getCardType() == CardType.PERSON)
				ret.add(c);
		}
		return ret;
	}

	public Set<Card> getWeaponsSeen() {
		Set<Card> ret = new HashSet<Card>();
		for (Card c : seen) {
			if (c.getCardType() == CardType.WEAPON)
				ret.add(c);
		}
		return ret;
	}

}
