package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	public IntBoard(){
		grid = new BoardCell[4][4];
		for(int i =0; i<4; i++){
			for(int j = 0; j<4; j++){
				grid[i][j] = new BoardCell(j,i);
			}
		}
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
	}

	public void calcAdjacencies(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if (i + 1 <= 3) {
					temp.add(grid[i + 1][j]);
				}
				if (i - 1 >= 0) {
					temp.add(grid[i - 1][j]);
				}
				if (j + 1 <= 3) {
					temp.add(grid[i][j + 1]);
				}
				if (j - 1 >= 0) {
					temp.add(grid[i][j - 1]);
				}
				adjMtx.put(grid[i][j], temp);
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
		temp = adjMtx.get(startCell);
		for(BoardCell b:temp){
			if(visited.contains(b)) continue;
			visited.add(b);
			calcTargets(b, pathLength - 1);
			visited.remove(b);
		}
	}
	
	public BoardCell getCell(int r, int c){
		return grid[c][r];
	}
	
	public Set<BoardCell> getAdjList(BoardCell b){
		return adjMtx.get(b);
	}
	public Set<BoardCell> getTargets() {
		return targets;
	}

	
}
