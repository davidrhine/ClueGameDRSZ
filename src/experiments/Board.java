package experiments;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public final class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell [][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String boardConfigFile;
	private String roomConfigFile;
	private static final Board instance = new Board();
	private Board() {}
	public static  Board getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		boardConfigFile = string;
		roomConfigFile = string2;
	}
	public void loadRoomConfig() throws BadConfigFormatException {
		FileReader reader = null;
		try {
			reader = new FileReader(roomConfigFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner in = new Scanner(reader);
		while (in.hasNext()) {
			String s = in.next();
			char c = s.charAt(0);
			s = in.nextLine();
			String check = s.substring(s.indexOf(','), s.length());
			s = s.substring(1, s.indexOf(','));
			legend.put(c, s);
			if (check != "Other" && check != "Card") throw new BadConfigFormatException();

		}
	}
	public void loadBoardConfig() throws BadConfigFormatException{
		FileReader reader = null;
		try {
			reader = new FileReader(boardConfigFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner in = new Scanner(reader);
		int rows = 0;
		while (in.hasNextLine()) {
			String s = in.nextLine();
			int cols = 0;
			for (int i = 0; i < s.length(); i+= 2) {
				char c = s.charAt(i);
				if(!legend.containsKey(c)) throw new BadConfigFormatException();
				if(i != s.length() -1){
					if (s.charAt(i+1) != ',') {
						char d = s.charAt(i + 1);
						if(d == 'U'){
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.UP);
						}
						else if (d == 'R') {
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.RIGHT);
						}
						else if (d == 'L') {
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.LEFT);
						}
						else if (d == 'D') {
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.DOWN);
						}
						i++;
					}
					else {
						board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.NONE);
					}
				}
				if(board[rows][cols] == null) {
					board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.NONE);
				}
				cols++;
			}
			rows++;
			if(numColumns != 0 && cols != numColumns) throw new BadConfigFormatException();
			numColumns = cols;
		}
		numRows = rows;
	}

	public Map<Character, String> getLegend() {

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
		return board[i][j];
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

	public void finalize() {
		loadRoomConfig();
		loadBoardConfig();
	}
}