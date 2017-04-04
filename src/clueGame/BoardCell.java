package clueGame;

public class BoardCell {
private int row;
private int column;
private char initial;
private DoorDirection door;
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

}
