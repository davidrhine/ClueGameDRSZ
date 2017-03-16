package experiments;

import java.awt.Color;
import java.util.Set;

public class Player {
	
	private String playerName;
	private int row, col;
	private Color color;
	private Set<Card> cards;
	
	
	public Card disproveSolution(Solution suggestion){
		return null;
	}


	public Set<Card> getCards() {
		return cards;
	}


	public String getPlayerName() {
		return playerName;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getCol() {
		return col;
	}


	public void setCol(int col) {
		this.col = col;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
}
