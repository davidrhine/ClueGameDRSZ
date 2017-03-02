package experiments;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
private int numRows;
private int numColumns;
public static final int MAX_BOARD_SIZE = 50;
private BoardCell [][] board;
private Map<Character, String> legend;
private Map<BoardCell, Set<BoardCell>> adjMatrix;
private Set<BoardCell> targets;
private Set<BoardCell> visited;
private String boardconfigFile;
private String roomConfigFile;

	public static Board getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		boardconfigFile = string;
		roomConfigFile = string2;
	}
	public void loadRoomConfig(){
		
	}
	public void loadBoardConfig(){
		
	}

	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
		return legend;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}

	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
   
	public void calcAdjacencies(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if (i + 1 <= 3) {
					temp.add(board[i + 1][j]);
				}
				if (i - 1 >= 0) {
					temp.add(board[i - 1][j]);
				}
				if (j + 1 <= 3) {
					temp.add(board[i][j + 1]);
				}
				if (j - 1 >= 0) {
					temp.add(board[i][j - 1]);
				}
				adjMatrix.put(board[i][j], temp);
			}
		}
	}
	public void calcTargets(BoardCell startCell, int pathLength){
		visited.add(startCell);
		if(pathLength == 0){
			targets.add(startCell);
			return;
		}
		Set<BoardCell> temp = new HashSet<BoardCell>();
		temp = adjMatrix.get(startCell);
		for(BoardCell b:temp){
			if(visited.contains(b)) continue;
			visited.add(b);
			calcTargets(b, pathLength - 1);
			visited.remove(b);
		}
	}

	public void finalize(){
		
	}
}
