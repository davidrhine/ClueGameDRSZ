package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import experiments.Board;
import experiments.BoardCell;
import experiments.DoorDirection;

public class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 23;

	// NOTE: I made Board static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test.
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.finalize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Man Cave", legend.get('C'));
		assertEquals("Ballroom", legend.get('B'));
		assertEquals("Theater", legend.get('H'));
		assertEquals("Storage Room", legend.get('S'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(4, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(16, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(17, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(3, 7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(14, 15);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(0, 6);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row < board.getNumRows(); row++)
			for (int col=0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(20, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('L', board.getCellAt(0, 0).getInitial());
		assertEquals('R', board.getCellAt(24, 2).getInitial());
		assertEquals('B', board.getCellAt(9, 0).getInitial());
		// Test last cell in room
		assertEquals('O', board.getCellAt(21, 12).getInitial());
		assertEquals('K', board.getCellAt(0, 10).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(20, 6).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(9,13).getInitial());
	}
	

}
