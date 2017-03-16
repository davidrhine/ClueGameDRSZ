package experiments;


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
}
