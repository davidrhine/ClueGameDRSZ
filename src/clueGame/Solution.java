package clueGame;


public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	
	
	public Solution(Card solutionPerson, Card solutionRoom, Card solutionWeapon) {
		this.person = solutionPerson;
		this.room = solutionRoom;
		this.weapon = solutionWeapon;
	}
	
	public Card getPerson() {
		return person;
	}
	public Card getRoom() {
		return room;
	}
	public Card getWeapon() {
		return weapon;
	}

	@Override
	public String toString() {
		return person.getCardName() + ", " + room.getCardName() + ", " + weapon.getCardName();
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solution other = (Solution) obj;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}
}
