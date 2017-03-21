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

	public BoardCell pickLocation(Set<BoardCell> targets){
		for (BoardCell b : targets){
			if (b.isRoom() && (currentRoom == null || b.getInitial() != currentRoom.getInitial())){
				if (b.getColumn() == 18 && b.getRow() == 4){
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
		for (BoardCell b : targets){
			if (i == item) {
				if (b.isRoom()){
					currentRoom = b;
				}
				move (b.getRow(), b.getColumn());
				return b;
			}
			i++;
		}
		return null; //should never happen
		
		
	}
	
	public void revealCard(Card c){
		seen.add(c);
	}
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(){
		
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
	
	

	
	
}
