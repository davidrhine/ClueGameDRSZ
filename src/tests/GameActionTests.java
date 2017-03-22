package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import experiments.Board;
import experiments.BoardCell;
import experiments.Card;
import experiments.CardType;
import experiments.ComputerPlayer;
import experiments.Solution;

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
			if (p.getPlayerName().equals("Colonel Mustard")) { // only need to
																// test one
				p.setCol(19);
				p.setRow(0);
				board.calcTargets(p.getRow(), p.getCol(), 4); // 4 is enough to
																// let him get
																// to a room
				target = p.pickLocation(board.getTargets());
				assertEquals(target, board.getCellAt(3, 18));
				int total1 = 0;
				int total2 = 0;
				for (int i = 0; i < 10; i++) {
					p.setCol(18);
					p.setRow(3);
					p.setCurrentRoom(board.getCellAt(3, 18));
					board.calcTargets(p.getRow(), p.getCol(), 2); // him should
																	// now be in
																	// the room,
																	// so a roll
																	// of two
																	// only lets
																	// him get
																	// out of
																	// the room
																	// and go
																	// one
																	// either up
																	// or down
					target = p.pickLocation(board.getTargets());
					if (target.equals(board.getCellAt(2, 19)))
						total1++;
					if (target.equals(board.getCellAt(4, 19)))
						total2++;
				}
				assertTrue(total1 > 0);
				assertTrue(total2 > 0);

				total1 = 0;
				total2 = 0;
				int total3 = 0;
				int total4 = 0;
				for (int i = 0; i < 25; i++) {
					p.setRow(11);
					p.setCol(5); // in the ballroom
					p.setCurrentRoom(board.getCellAt(11, 5));
					board.calcTargets(p.getRow(), p.getCol(), 3); // only room 3
																	// from the
																	// ballroom
																	// is
																	// ballroom,
																	// so should
																	// be
																	// randomly
																	// selected
					target = p.pickLocation(board.getTargets());
					if (target.equals(board.getCellAt(13, 6)))
						total1++;
					if (target.equals(board.getCellAt(12, 7)))
						total2++;
					if (target.equals(board.getCellAt(10, 7)))
						total3++;
					if (target.equals(board.getCellAt(10, 5)))
						total4++;
				}
				assertTrue(total1 > 0);
				assertTrue(total2 > 0);
				assertTrue(total3 > 0); // all possibilities should be chosen
										// within 25 iterations if random
				assertTrue(total4 > 0);

				p.setCol(18);
				p.setRow(3);// in the dining room
				p.setCurrentRoom(board.getCellAt(3, 18));
				board.calcTargets(p.getRow(), p.getCol(), 3); // there are 2
																// rooms
																// distance 3
																// from this
																// spot, but one
																// of them is
																// the dining
																// room, which
																// plum is
																// already in,
																// so the other
																// one should
																// always be
																// chosen.
				target = p.pickLocation(board.getTargets());
				assertEquals(target, board.getCellAt(4, 20));

			}
		}

	}

	@Test
	public void accusationTests() {
		Solution accusation = board.getGameSolution(); // correct solution

		// check to make sure the correct solution works
		assertTrue(board.checkAccusation(accusation));

		accusation.setPerson(new Card("Bob", CardType.PERSON)); // wrong person
																// test
		assertTrue(board.checkAccusation(accusation));

		accusation = board.getGameSolution(); // correct solution
		accusation.setWeapon(new Card("Screwdriver", CardType.WEAPON)); // wrong
																		// weapon
																		// test
		assertTrue(board.checkAccusation(accusation));

		accusation = board.getGameSolution(); // correct solution
		accusation.setRoom(new Card("Room Room", CardType.ROOM)); // wrong room
																	// test
		assertTrue(board.checkAccusation(accusation));

	}

	@Test
	public void createSuggestionTests() {
		Set<ComputerPlayer> players = board.getComputerPlayers();

		for (ComputerPlayer p : players) {
			if (p.getPlayerName().equals("Colonel Mustard")) {
				p.move(10, 16);
				p.setCurrentRoom(board.getCellAt(10, 16));
				p.createSuggestion();
				Solution suggestion = p.getSuggestion();
				assertTrue(suggestion.getRoom().getCardName().equals("Storage Room"));

				p.setSeen(new HashSet<Card>());//currently seen 0 cards

				Set<Card> weapons = Board.getWeapons(); // set of all weapons in
														// the game
				Card weapon1 = null;
				Card weapon2 = null;
				int i = weapons.size();
				for (Card weapon : weapons) { // this should reveal all but two
												// weapons
					if (i == 2) {
						weapon2 = weapon;
					} else if (i == 1) {
						weapon1 = weapon;
					} else {
						p.revealCard(weapon);
					}
					i--;
				}
				int total1 = 0;
				int total2 = 0;
				for (i = 0; i < 10; i++) {
					p.createSuggestion();
					suggestion = p.getSuggestion();
					if (suggestion.getWeapon().equals(weapon2))
						total2++;
					if (suggestion.getWeapon().equals(weapon1))
						total1++;
				}
				assertTrue(total2 > 0);
				assertTrue(total1 > 0); // make sure that both of the unrevealed
										// weapons are suggested atleast once in
										// 10 suggestions

				Set<Card> people = Board.getPeople(); // set of all people in
														// the game
				Card person1 = null;
				Card person2 = null;
				;
				i = people.size();
				for (Card person : people) { // this should reveal all but two
												// people
					if (i == 2) {
						person2 = person;
					} else if (i == 1) {
						person1 = person;
					} else {
						p.revealCard(person);
					}
					i--;
				}
				total1 = 0;
				total2 = 0;
				for (i = 0; i < 10; i++) {
					p.createSuggestion();
					suggestion = p.getSuggestion();
					if (suggestion.getPerson().equals(person2))
						total2++;
					if (suggestion.getPerson().equals(person1))
						total1++;
				}
				assertTrue(total1 > 0); // make sure that both of the unrevealed
										// people are suggested atleast once in
										// 10 suggestions
				assertTrue(total2 > 0);

				p.revealCard(weapon2);
				p.createSuggestion();
				suggestion = p.getSuggestion();
				assertEquals(suggestion.getWeapon(), weapon1); // all but weapon
																// 1 have now
																// been
																// revealed, so
																// this should
																// be the guess

				p.revealCard(person2);
				p.createSuggestion();
				suggestion = p.getSuggestion();
				assertEquals(suggestion.getPerson(), person1); // all but person
																// 1 have now
																// been
																// revealed, so
																// this should
																// be the guess

			}
		}
	}

	@Test
	public void disproveSuggestion() {
		Set<ComputerPlayer> players = board.getComputerPlayers();

		for (ComputerPlayer p : players) {
			if (p.getPlayerName().equals("Colonel Mustard")) {
				p.setCards(new HashSet<Card>()); //get rid of all his cards
				
				Card storage = null; //for testing
				Card dining = null;
				Card white = null;
				Card green = null;
				Card candlestick = null; 
				
				for (Card c : Board.getPeople()){
					if (c.getCardName().equals("Mrs White")){
						p.getCards().add(c);
						p.revealCard(c);
						white = c;
					} else if (c.getCardName().equals("Reverend Green")) green = c;
				}
				for (Card c : Board.getRooms()){
					if (c.getCardName().equals("Dining Room")){
						p.getCards().add(c);
						p.revealCard(c);
						dining = c;
					} else if (c.getCardName().equals("Storage Room")) storage = c;
				}
				for (Card c : Board.getWeapons()){
					if (c.getCardName().equals("Dagger")){
						p.getCards().add(c);
						p.revealCard(c);
					} else if (c.getCardName().equals("Candlestick")) candlestick = c;
				} 
				//now mustard has the dagger mrs white and dining room
				
				
				Solution test1 = new Solution(green, storage, candlestick);
				Solution test2 = new Solution(green, dining, candlestick);
				Solution test3 = new Solution(white, dining, candlestick);
				
				assertEquals(p.disproveSolution(test1), null);
				assertEquals(p.disproveSolution(test2), dining);
				
				int total1 = 0;
				int total2 = 0;
				for (int i = 0; i < 10; i++){
					if (p.disproveSolution(test3).equals(dining)) total1++;
					if (p.disproveSolution(test3).equals(white)) total2++;
				}
				
				assertTrue(total1 > 0);
				assertTrue(total2 > 0);
				
			}
		}

	}

}
