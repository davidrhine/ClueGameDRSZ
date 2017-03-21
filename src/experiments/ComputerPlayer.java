package experiments;

import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	BoardCell currentRoom;
	
	
	
	public ComputerPlayer(String name, String color, String row, String col) {
		super(name, color, row, col);
	}

	public BoardCell pickLocation(Set<BoardCell> targets){
		for (BoardCell b : targets){
			if (b.isRoom() && (currentRoom == null || b.getInitial() != currentRoom.getInitial())){
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
	
	public void makeAccusation(){
		
	}
	
	public void createSuggestion(){
		
	}
	
	
}
