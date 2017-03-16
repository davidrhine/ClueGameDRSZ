package experiments;

import java.util.ArrayList;

public class Solution {
	private String person;
	private String room;
	private String weapon;
	private ArrayList<Card> cards;
	
	
	public Solution(String person, String room, String weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public String getPerson() {
		return person;
	}
	public String getRoom() {
		return room;
	}
	public String getWeapon() {
		return weapon;
	}
}
