package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		board.initialize();
	}
	
	// Tests of just walkways, 4 steps
		// These are BROWN on the planning spreadsheet
		@Test
		public void testTargetsFourSteps() {
			board.calcTargets(24, 6, 4);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCellAt(22, 6)));
			assertTrue(targets.contains(board.getCellAt(22, 8)));
			assertTrue(targets.contains(board.getCellAt(20, 6)));
			assertTrue(targets.contains(board.getCellAt(21, 7)));
			assertTrue(targets.contains(board.getCellAt(23, 7)));
			assertTrue(targets.contains(board.getCellAt(23, 5)));
			// Includes a path that doesn't have enough length
			board.calcTargets(9, 20, 4);
			targets= board.getTargets();
			System.out.println(targets);
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCellAt(9, 16)));	
			assertTrue(targets.contains(board.getCellAt(8, 17)));		
			assertTrue(targets.contains(board.getCellAt(9, 18)));	
			assertTrue(targets.contains(board.getCellAt(7, 18)));
			assertTrue(targets.contains(board.getCellAt(6, 19)));
			assertTrue(targets.contains(board.getCellAt(8, 19)));	
		}	

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(1, 1);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(17, 8);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(8, 20);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(20, 20);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(3, 17);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(24, 5);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(11, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(16, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 16)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(6, 10);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 10)));
		//TEST DOORWAY UP
		testList = board.getAdjList(17, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 5)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(4, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 3)));

	}

	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(4, 3);
		assertTrue(testList.contains(board.getCellAt(4, 2)));
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(7, 15);
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertTrue(testList.contains(board.getCellAt(7, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 16)));
		assertTrue(testList.contains(board.getCellAt(8, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(17, 16);
		assertTrue(testList.contains(board.getCellAt(16, 16)));
		assertTrue(testList.contains(board.getCellAt(18, 16)));
		assertTrue(testList.contains(board.getCellAt(17, 17)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(16, 11);
		assertTrue(testList.contains(board.getCellAt(16, 10)));
		assertTrue(testList.contains(board.getCellAt(16, 12)));
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		assertTrue(testList.contains(board.getCellAt(17, 11)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are CYAN on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertEquals(1, testList.size());

		// Test on left edge of board, two walkway pieces
		testList = board.getAdjList(5, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(5, 1)));
		assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(15, 3);
		assertTrue(testList.contains(board.getCellAt(15, 2)));
		assertTrue(testList.contains(board.getCellAt(15, 4)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(16, 7);
		assertTrue(testList.contains(board.getCellAt(15, 7)));
		assertTrue(testList.contains(board.getCellAt(17, 7)));
		assertTrue(testList.contains(board.getCellAt(16, 8)));
		assertTrue(testList.contains(board.getCellAt(16, 6)));
		assertEquals(4, testList.size());

		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(24, 7);
		assertTrue(testList.contains(board.getCellAt(24, 6)));
		assertTrue(testList.contains(board.getCellAt(23, 7)));
		assertEquals(2, testList.size());

		// Test on right edge of board, next to 2 room pieces
		testList = board.getAdjList(15, 22);
		assertTrue(testList.contains(board.getCellAt(15, 21)));
		assertEquals(1, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 2);
		assertTrue(testList.contains(board.getCellAt(5, 1)));
		assertTrue(testList.contains(board.getCellAt(5, 3)));
		assertEquals(2, testList.size());
	}


	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(24, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 6)));
		assertTrue(targets.contains(board.getCellAt(24, 7)));	

		board.calcTargets(15, 0, 1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 1)));			
	}

	// Tests of just walkways, 2 steps
	// These are BROWN on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(24, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 16)));

		board.calcTargets(24, 6, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 6)));
		assertTrue(targets.contains(board.getCellAt(23, 7)));
		assertTrue(targets.contains(board.getCellAt(23, 5)));
	}
	
	

	// Tests of just walkways plus one door, 6 steps
	// These are DARK RED on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(3, 11, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 10)));
		assertTrue(targets.contains(board.getCellAt(7, 9)));	
		assertTrue(targets.contains(board.getCellAt(8, 10)));	
		assertTrue(targets.contains(board.getCellAt(8, 12)));	
		assertTrue(targets.contains(board.getCellAt(7, 13)));		
	}	

	// Test getting into a room
	// These are DARK RED on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(8, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly left and right
		assertTrue(targets.contains(board.getCellAt(8, 14)));
		assertTrue(targets.contains(board.getCellAt(8, 18)));
		// directly down
		assertTrue(targets.contains(board.getCellAt(10, 16)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(7, 17)));
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		assertTrue(targets.contains(board.getCellAt(9, 17)));
		assertTrue(targets.contains(board.getCellAt(9, 15)));
	}

	// Test getting into room, doesn't require all steps
	// These are DARK RED on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{ //HERE
		board.calcTargets(9, 6, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(11, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(6, 6)));
		assertTrue(targets.contains(board.getCellAt(12, 6)));
		// right then up
		assertTrue(targets.contains(board.getCellAt(7, 7)));
		assertTrue(targets.contains(board.getCellAt(8, 8)));
		// down then right/left
		assertTrue(targets.contains(board.getCellAt(11, 7)));
		//up and down
		assertTrue(targets.contains(board.getCellAt(8, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 6)));
		// to the right
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(9, 5)));
		assertTrue(targets.contains(board.getCellAt(10, 5)));		
		// 
		assertTrue(targets.contains(board.getCellAt(11, 5)));				

	}

	// Test getting out of a room
	// These are DARK RED on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(3, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		// Take two steps
		board.calcTargets(3, 7, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		assertTrue(targets.contains(board.getCellAt(4, 6)));
	}

}

