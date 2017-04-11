package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	
	private String playerName;
	private int row, col;
	private Color color;
	private Set<Card> cards = new HashSet<Card>();
	
	
	public Player(String name, String color, String row, String col) {
		playerName = name;
		this.row = Integer.parseInt(row);
		this.col = Integer.parseInt(col);
		this.color = convertColor(color);
	}
	
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim().toUpperCase());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}
	
	public void drawPlayer(Graphics g) {
		g.setColor(this.color);
		g.drawOval(col * 25, row * 25, 25, 25);
		g.fillOval(col * 25, row * 25, 25, 25);
	}


	public Card disproveSolution(Solution suggestion){
		Card person = suggestion.getPerson();
		Card weapon = suggestion.getWeapon();
		Card room = suggestion.getRoom();
		
		ArrayList<Card> cardsHas = new ArrayList<Card>();
		if (getCards().contains(person)) cardsHas.add(person);
		if (getCards().contains(weapon)) cardsHas.add(weapon);
		if (getCards().contains(room)) cardsHas.add(room);
		
		if (cardsHas.size() != 0){
			return cardsHas.get(new Random().nextInt(cardsHas.size()));
		} else {
			return null;
		}

		
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
	
	public void move(int row, int col){
		this.row = row;
		this.col = col;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	
	
	
}
