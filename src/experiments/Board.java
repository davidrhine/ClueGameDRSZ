package experiments;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import sun.security.jca.GetInstance;

public final class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> legend = new HashMap<Character, String>();
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private Solution gameSolution;
	private Set<Card> weapons = new HashSet<Card>();;
	private Set<Card> rooms = new HashSet<Card>();
	private Set<Card> people = new HashSet<Card>();
	private Set<Card> allCards;

	private static final Board instance = new Board();

	private Board() {
	}

	public static Board getInstance() {
		return instance;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<ComputerPlayer> getComputerPlayers() {
		ArrayList<ComputerPlayer> ret = new ArrayList<ComputerPlayer>();
		for (Player p : players) {
			if (p.getClass() == ComputerPlayer.class)
				ret.add((ComputerPlayer) p);
		}
		return ret;
	}

	public void setConfigFiles(String boardFile, String roomFile) {
		boardConfigFile = boardFile;
		roomConfigFile = roomFile;
	}

	public void setConfigFiles(String boardFile, String roomFile, String playerFile, String weaponFile) {
		boardConfigFile = boardFile;
		roomConfigFile = roomFile;
		weaponConfigFile = weaponFile;
		playerConfigFile = playerFile;
	}

	public void loadRoomConfig() throws BadConfigFormatException {
		FileReader reader = null;
		try {
			reader = new FileReader(roomConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner in = new Scanner(reader);
		while (in.hasNext()) {
			String s = in.next();
			char c = s.charAt(0);
			s = in.nextLine();
			String check = s.substring(s.indexOf(',') + 2, s.length());
			s = s.substring(1, s.indexOf(','));
			legend.put(c, s);
			if (!check.equals("Other") && !check.equals("Card")) {
				throw new BadConfigFormatException();
			}

			if (!check.equalsIgnoreCase("other")) {
				Card card = new Card(s, CardType.ROOM);
				deck.add(card);
				rooms.add(card);
			}

		}
	}

	public void loadBoardConfig() throws BadConfigFormatException {
		FileReader reader = null;
		try {
			reader = new FileReader(boardConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner in = new Scanner(reader);
		int rows = 0;
		while (in.hasNextLine()) {
			String s = in.nextLine();
			int cols = 0;
			for (int i = 0; i < s.length(); i += 2) {

				char c = s.charAt(i);
				if (!legend.containsKey(c))
					throw new BadConfigFormatException();
				if (i != s.length() - 1) {
					if (s.charAt(i + 1) != ',') {
						char d = s.charAt(i + 1);
						if (d == 'U') {
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.UP);
						} else if (d == 'R') {
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.RIGHT);
						} else if (d == 'L') {
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.LEFT);
						} else if (d == 'D') {
							board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.DOWN);
						}
						i++;
					} else {
						board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.NONE);
					}
				}
				if (board[rows][cols] == null) {
					board[rows][cols] = new BoardCell(rows, cols, c, DoorDirection.NONE);
				}
				cols++;
			}
			rows++;
			if (numColumns != 0 && cols != numColumns)
				throw new BadConfigFormatException();
			numColumns = cols;
		}
		numRows = rows;
	}

	public Map<Character, String> getLegend() {

		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}

	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if (i + 1 <= numRows - 1) {
					if (board[i + 1][j].isDoorway() && board[i][j].isDoorway())
						;
					else if (board[i][j].isRoom() && !board[i][j].isDoorway())
						;
					else if ((board[i + 1][j].isDoorway() && board[i + 1][j].getDoorDirection() == DoorDirection.UP)
							|| (board[i + 1][j].isWalkway() && !board[i][j].isDoorway())
							|| (board[i][j].getDoorDirection() == DoorDirection.DOWN && board[i + 1][j].isWalkway())) {
						temp.add(board[i + 1][j]);
					}
				}
				if (i - 1 >= 0) {
					if (board[i - 1][j].isDoorway() && board[i][j].isDoorway())
						;
					else if (board[i][j].isRoom() && !board[i][j].isDoorway())
						;
					else if ((board[i - 1][j].isDoorway() && board[i - 1][j].getDoorDirection() == DoorDirection.DOWN)
							|| (board[i - 1][j].isWalkway() && !board[i][j].isDoorway())
							|| (board[i][j].getDoorDirection() == DoorDirection.UP && board[i - 1][j].isWalkway())) {
						temp.add(board[i - 1][j]);
					}
				}
				if (j + 1 <= numColumns - 1) {
					if (board[i][j + 1].isDoorway() && board[i][j].isDoorway())
						;
					else if (board[i][j].isRoom() && !board[i][j].isDoorway())
						;
					else if ((board[i][j + 1].isDoorway() && board[i][j + 1].getDoorDirection() == DoorDirection.LEFT)
							|| (board[i][j + 1].isWalkway() && !board[i][j].isDoorway())
							|| (board[i][j].getDoorDirection() == DoorDirection.RIGHT && board[i][j + 1].isWalkway())) {
						temp.add(board[i][j + 1]);
					}
				}
				if (j - 1 >= 0) {
					if (board[i][j - 1].isDoorway() && board[i][j].isDoorway())
						;
					else if (board[i][j].isRoom() && !board[i][j].isDoorway())
						;
					else if ((board[i][j - 1].isDoorway() && board[i][j - 1].getDoorDirection() == DoorDirection.RIGHT)
							|| (board[i][j - 1].isWalkway() && !board[i][j].isDoorway())
							|| (board[i][j].getDoorDirection() == DoorDirection.LEFT && board[i][j - 1].isWalkway())) {
						temp.add(board[i][j - 1]);
					}
				}
				adjMatrix.put(board[i][j], temp);
			}
		}
	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		int r = startCell.getRow();
		int c = startCell.getColumn();
		visited.add(board[r][c]);
		Set<BoardCell> temp = new HashSet<BoardCell>();
		temp = adjMatrix.get(board[r][c]);
		for (BoardCell b : temp) {
			if (visited.contains(b))
				continue;
			if (pathLength == 1 || b.isDoorway() && !visited.contains(b)) {
				targets.add(b);
			} else {
				visited.add(b);
				calcTargets(b, pathLength - 1);
				visited.remove(b);
			}

		}
	}

	public void initialize() {
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			loadPlayersConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			loadWeaponsConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}

		calcAdjacencies();
	}

	private void loadWeaponsConfig() throws BadConfigFormatException {
		if (weaponConfigFile == null)
			return;
		FileReader reader;
		Scanner scanner;
		try {
			reader = new FileReader(weaponConfigFile);
			scanner = new Scanner(reader);
		} catch (FileNotFoundException e) {
			throw new BadConfigFormatException();
		}

		while (scanner.hasNextLine()) {
			String name = scanner.nextLine();
			Card c = new Card(name, CardType.WEAPON);
			deck.add(c);
			weapons.add(c);
		}
		scanner.close();

	}

	private void loadPlayersConfig() throws BadConfigFormatException {
		if (playerConfigFile == null)
			return;
		FileReader reader;
		Scanner scanner;
		try {
			reader = new FileReader(playerConfigFile);
			scanner = new Scanner(reader);
		} catch (FileNotFoundException e) {
			throw new BadConfigFormatException();
		}
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");
			Player p;
			if (line[0].equals("Professor Plum")) {
				p = new HumanPlayer(line[0], line[1], line[2], line[3]);
				players.add(0, p);
			} else {
				p = new ComputerPlayer(line[0], line[1], line[2], line[3]);
				players.add(p);
			}
			
			Card c = new Card(line[0], CardType.PERSON);
			deck.add(c);
			people.add(c);
		}
		scanner.close();

	}

	public static Set<Card> getWeapons() {
		return getInstance().weapons;
	}

	public static Set<Card> getRooms() {
		return getInstance().rooms;
	}

	public static Set<Card> getPeople() {
		return getInstance().people;
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}

	public void calcTargets(int i, int j, int k) {
		targets.clear();
		calcTargets(board[i][j], k);

	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void deal() {
		Card solutionWeapon = null;
		Card solutionRoom = null;
		Card solutionPerson = null;

		while (true) {
			Card c = deck.get((int) (Math.random() * deck.size()));

			if (solutionWeapon == null && c.getCardType() == CardType.WEAPON) {
				solutionWeapon = c;
				deck.remove(c);
			} else if (solutionPerson == null && c.getCardType() == CardType.PERSON) {
				solutionPerson = c;
				deck.remove(c);

			} else if (solutionRoom == null && c.getCardType() == CardType.ROOM) {
				solutionRoom = c;
				deck.remove(c);
			}

			if (solutionRoom != null && solutionPerson != null && solutionWeapon != null) {
				break;
			}

		}
		setGameSolution(new Solution(solutionPerson, solutionRoom, solutionWeapon));

		while (deck.size() > 0) {
			for (Player p : players) {
				if (deck.size() == 0)
					break;
				Card c = deck.get((int) (Math.random() * deck.size()));
				p.getCards().add(c);
				if (p.getClass() == ComputerPlayer.class) {
					((ComputerPlayer) p).revealCard(c);
				}
				deck.remove(c);
			}
		}

	}

	public boolean checkAccusation(Solution accusation) {
		return accusation.equals(gameSolution);
	}

	public Solution getGameSolution() {
		return gameSolution;
	}

	public void setGameSolution(Solution gameSolution) {
		this.gameSolution = gameSolution;
	}

	public static Card getRoomWithInitial(char initial) {
		String roomname = getInstance().getLegend().get(initial);
		for (Card c : getInstance().getRooms()) {
			if (c.getCardName().equals(roomname))
				return c;
		}
		return null; // shouldnt happen
	}

	
	public static Set<Card> getAllCards(){
		return getInstance().allCards;
	}

	public static Card getCardWithName(String cardName) {
		if (getAllCards() == null) {
			getInstance().allCards = getPeople();
			for (Card c : getRooms()) {
				getInstance().allCards.add(c);
			}
			for (Card c : getWeapons()) {
				getInstance().allCards.add(c);
			}
		}
		
		for (Card c : getInstance().allCards){
			if (c.getCardName().equals(cardName)) return c;
		}
		
		return null;
		
	}

	public Card handleSuggestion(Solution suggestion, Player accuser) {

		
		int index = players.indexOf(accuser);
		for (int i = index + 1; i < players.size(); i++){
			Player p = players.get(i);
			if (p.disproveSolution(suggestion) != null){
				return p.disproveSolution(suggestion);
			}
		}
		for (int i = 0; i < index; i++){
			Player p = players.get(i);
			if (p.disproveSolution(suggestion) != null){
				return p.disproveSolution(suggestion);
			}
		}
		
		
		return null;
	}
}
