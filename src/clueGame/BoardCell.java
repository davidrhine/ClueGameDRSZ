package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;



public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private boolean isATarget = false;
	private DoorDirection door;
	//BorderLayout blackLine = BorderFactory.createLineBorder(Color.black);
	public BoardCell(){
		row = 0;
		column = 0;
	}

	public BoardCell(int row, int column, char initial, DoorDirection door) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.door = door;
	}

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public void drawBoardCell(Graphics g) {
		
		switch (this.initial) {
		case 'W':
			
			g.setColor(Color.CYAN);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
			g.fillRect(this.column * 25, this.row * 25, 25, 25);
			g.setColor(Color.black);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
			break;
		case 'X':
			g.setColor(Color.RED);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
			g.fillRect(this.column * 25, this.row * 25, 25, 25);
			g.setColor(Color.black);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
			break;
		}
		if (this.door != DoorDirection.NONE) {	
			
			if (this.door == DoorDirection.UP) {
				g.setColor(Color.green);
				g.drawRect(this.column * 25, this.row * 25, 25, 3);
				g.fillRect(this.column * 25, this.row * 25, 25, 3);
			}
			if (this.door == DoorDirection.LEFT) {
				g.setColor(Color.green);
				g.drawRect(this.column * 25, this.row * 25, 3, 25);
				g.fillRect(this.column * 25, this.row * 25, 3, 25);
			}
			if (this.door == DoorDirection.RIGHT) {
				g.setColor(Color.green);
				g.drawRect(this.column * 25 + 22, this.row * 25, 3, 25);
				g.fillRect(this.column * 25 + 22, this.row * 25, 3, 25);
			}
			if (this.door == DoorDirection.DOWN) {
				g.setColor(Color.green);
				g.drawRect(this.column * 25, this.row * 25 + 22, 25, 3);
				g.fillRect(this.column * 25, this.row * 25 + 22, 25, 3);
			}
		}
		if (isATarget) {
			g.setColor(Color.ORANGE);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
			g.fillRect(this.column * 25, this.row * 25, 25, 25);
			g.setColor(Color.black);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
		}
	}

	public int getRow() {
		return row;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public boolean isDoorway() {

		if(door == DoorDirection.NONE) return false;
		else return true;
	}

	public boolean isWalkway() {

		if(initial == 'W') return true;
		else return false;
	}

	public boolean isRoom() {

		if(initial == 'W' || initial == 'X' ) return false;
		else return true;
	}

	public DoorDirection getDoorDirection() {
		return door;


	}

	public char getInitial() {
		return initial;

	}

	public boolean isATarget() {
		return isATarget;
	}

	public void setATarget(boolean isATarget) {
		this.isATarget = isATarget;
	}

}
