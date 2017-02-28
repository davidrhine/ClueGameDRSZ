package experiments;

public class BoardCell {
private int row;
private int column;
public BoardCell(){
	row =0;
	column = 0;
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
	// TODO Auto-generated method stub
	return false;
}
public Object getDoorDirection() {
	// TODO Auto-generated method stub
	return null;
}
public Object getInitial() {
	// TODO Auto-generated method stub
	return null;
}
}
