package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import experiments.Board;
import experiments.BoardCell;
import experiments.ComputerPlayer;
import experiments.HumanPlayer;
import experiments.Player;

public class GameActionTests {

	private static Board board;
	// private static final int NUM_CARDS = 21;
	// private static final int NUM_PLAYERS = 6;
	// private static final int NUM_ROOMS = 9;
	// private static final int NUM_WEAPONS = 6;

	@BeforeClass
	public static void initialize() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.deal();
	}

	/*
	 * Tests the computer players selecting a random target
	 */
	@Test
	public void selectTargetTest() {
		Set<ComputerPlayer> players = board.getComputerPlayers();
		
		for (ComputerPlayer p : players) {
			BoardCell target;
			if (p.getPlayerName().equals("Colonel Mustard")){ //only need to test one
				board.calcTargets(p.getRow(), p.getCol(), 4); //4 is enough to let plum get to a room
				target = p.pickLocation(board.getTargets());
				assertEquals(target, board.getCellAt(3, 18));
				int total1 = 0;
				int total2 = 0;
				for (int i = 0; i < 10; i++){		
					p.setCol(18);
					p.setRow(3);
					board.calcTargets(p.getRow(), p.getCol(), 2); //plum should now be in the room, so a roll of two only lets him get out of the room and go one either up or down
					target = p.pickLocation(board.getTargets());
					if (target.equals(board.getCellAt(2, 19))) total1++;
					if (target.equals(board.getCellAt(4, 19))) total2++;
				}
				assertTrue(total1 > 0);
				assertTrue(total2 > 0);
				
				total1 = 0;
				total2 = 0;
				int total3 = 0; 
				int total4 = 0;
				for (int i = 0; i < 25; i++){
					p.setRow(11);
					p.setCol(5); //in the ballroom
					board.calcTargets(p.getRow(),  p.getCol(), 3); //only room 3 from the ballroom is ballroom, so should be randomly selected
					target = p.pickLocation(board.getTargets());
					if (target.equals(board.getCellAt(13, 6))) total1++;
					if (target.equals(board.getCellAt(12, 17))) total2++;
					if (target.equals(board.getCellAt(10, 7))) total3++;
					if (target.equals(board.getCellAt(10, 5))) total4++;
				}
				assertTrue(total1 > 0);
				assertTrue(total2 > 0);
				assertTrue(total3 > 0); //all possibilities should be chosen within 25 iterations if random
				assertTrue(total4 > 0);
				
				p.setCol(18);
				p.setRow(3);//in the dining room
				board.calcTargets(p.getRow(),  p.getCol(), 3); //there are 2 rooms distance 3 from this spot, but one of them is the dining room, which plum is already in, so the other one should always be chosen.
				target = p.pickLocation(board.getTargets());
				assertEquals(target, board.getCellAt(4, 20));
				
				
			
			}
		}
		

	}

}
