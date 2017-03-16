package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import experiments.Board;
import experiments.Card;
import experiments.CardType;
import experiments.ComputerPlayer;
import experiments.HumanPlayer;
import experiments.Player;

public class GameSetupTests {

	private static Board board;
	private static final int NUM_CARDS = 21;
	private static final int NUM_PLAYERS = 6;
	private static final int NUM_ROOMS = 9;
	private static final int NUM_WEAPONS = 6;

	@BeforeClass
	public static void initialize() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
	}

	@Test
	public void loadPeople() {
		Set<Player> players = board.getPlayers();
		assertEquals(players.size(), NUM_PLAYERS);
		for (Player p : players) {
			switch (p.getPlayerName()) {
			case "Miss Scarlett":
				assertEquals(p.getColor(), Color.RED);
				assertEquals(p.getRow(), 0);
				assertEquals(p.getCol(), 4);
				assertEquals(p.getClass(), ComputerPlayer.class);
				break;
			case "Professor Plum":
				assertEquals(p.getColor(), Color.MAGENTA);
				assertEquals(p.getRow(), 0);
				assertEquals(p.getCol(), 19);
				assertEquals(p.getClass(), HumanPlayer.class);
				break;
			case "Mrs Peacock":
				assertEquals(p.getColor(), Color.BLUE);
				assertEquals(p.getRow(), 9);
				assertEquals(p.getCol(), 22);
				assertEquals(p.getClass(), ComputerPlayer.class);
				break;
			case "Reverend Green":
				assertEquals(p.getColor(), Color.GREEN);
				assertEquals(p.getRow(), 15);
				assertEquals(p.getCol(), 22);
				assertEquals(p.getClass(), ComputerPlayer.class);
				break;
			case "Colonel Mustard":
				assertEquals(p.getColor(), Color.YELLOW);
				assertEquals(p.getRow(), 24);
				assertEquals(p.getCol(), 16);
				assertEquals(p.getClass(), ComputerPlayer.class);
				break;
			case "Mrs White":
				assertEquals(p.getColor(), Color.WHITE);
				assertEquals(p.getRow(), 24);
				assertEquals(p.getCol(), 6);
				assertEquals(p.getClass(), ComputerPlayer.class);
				break;
			default:
				assertTrue(false);
				break;
			}
		}
	}

	// not annotated as @test because we need this to run as a part of deal
	// cards. This is because once our deck is dealt,
	// there isnt an efficient way to test all the cards, since they have been
	// split up between players and the solution
	public void createDeckTest() {
		ArrayList<Card> deck = board.getDeck();

		assertEquals(deck.size(), NUM_CARDS);

		boolean containsGreen = false;
		boolean containsRope = false;
		boolean containsLounge = false;
		int numPlayers = 0;
		int numWeapons = 0;
		int numRooms = 0;

		for (Card c : deck) {
			if (c.getCardType() == CardType.PERSON) {
				numPlayers++;
				if (c.getCardName().equals("Reverend Green"))
					containsGreen = true;
			} else if (c.getCardType() == CardType.WEAPON) {
				numWeapons++;
				if (c.getCardName().equals("Rope"))
					containsRope = true;
			} else if (c.getCardType() == CardType.ROOM) {
				numRooms++;
				if (c.getCardName().equals("Lounge"))
					containsLounge = true;
			}
		}

		assertEquals(numPlayers, NUM_PLAYERS);
		assertEquals(numRooms, NUM_ROOMS);
		assertEquals(numWeapons, NUM_WEAPONS);
		assertTrue(containsGreen);
		assertTrue(containsRope);
		assertTrue(containsLounge);
	}

	@Test
	public void dealCards() {

		createDeckTest();

		board.deal();

		assertEquals(board.getDeck().size(), 0); // all cards should be dealt
		int numCards = 0;
		for (Player p : board.getPlayers()) {
			boolean b = (p.getCards().size() >= (NUM_CARDS - 3) / NUM_PLAYERS);
			assertTrue(b);
			numCards += p.getCards().size();
		}

		assertEquals(numCards, NUM_CARDS - 3); // all cards got dealt except the
												// 3 solution cards
	}

}
