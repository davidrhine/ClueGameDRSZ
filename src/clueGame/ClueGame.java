package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{
	Board board;
	private MyCardsGUI cardsGUI;
	private DetectiveNotesDialog Detective;
	static String message = "You are Professor Plum, press Next Player to begin play";
	private String humanMustFinishString = "You must finish your turn";
	private String humanMustFinishTitle = "Error";
	private String incorrectLocationString = "Incorrect Cell Choice";
	static String title = "Welcome to Clue";
	private ArrayList<Player> players;
	boolean humanMustFinish = true;
	boolean initialClick = true;
	boolean incorrectLocation = false;
	private ControlGUI gui;
	private int currentPlayer = 0;
	private int roll;
	private Random rand;
	private ArrayList<BoardCell> targets = new ArrayList<BoardCell>();

	class NextPlayerClicked implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			

			if (humanMustFinish && !initialClick) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), humanMustFinishString, humanMustFinishTitle, JOptionPane.INFORMATION_MESSAGE);

			}
			else {
				roll = rand.nextInt(6) + 1;
				Player tempPlayer = players.get(currentPlayer);
				board.calcTargets(tempPlayer.getRow(), tempPlayer.getCol(), roll);
				targets.clear();
				for (BoardCell c : board.getTargets()) {
					targets.add(c);
				}
				if (players.get(currentPlayer) instanceof HumanPlayer) {
					for (BoardCell c : targets) {
						c.setATarget(true);
					}
					humanMustFinish = true;
				}
				else {
					int randomTarget = rand.nextInt(targets.size());
					players.get(currentPlayer).setCol(targets.get(randomTarget).getColumn());
					players.get(currentPlayer).setRow(targets.get(randomTarget).getRow());
					for (BoardCell c : targets) {
						c.setATarget(false);
					}

				}

				String s = Integer.toString(roll);
				gui.dieRoll.setText(s);
				gui.currentPlayerDisplay.setText(players.get(currentPlayer).getPlayerName());
				currentPlayer++;
				if (currentPlayer == 6) {
					currentPlayer = 0;
				}
			}
			initialClick = false;
			board.repaint();

		}
	}

	class MoveHumanPlayer implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			boolean notFinished = true;
			incorrectLocation = true;
			for (BoardCell c : targets) {
				if (e.getX() >= c.getColumn() * 25 && e.getX() <= (c.getColumn() * 25) + 25) {
					if (e.getY() >= c.getRow() * 25 + 50 && e.getY() <= (c.getRow() * 25) + 75) {
						players.get(0).setCol(c.getColumn());
						players.get(0).setRow(c.getRow());
						for (BoardCell a : targets) {
							a.setATarget(false);
						}
						incorrectLocation = false;
						notFinished = false;
						board.repaint();
					}
				}
			}
			humanMustFinish = notFinished;
			if (incorrectLocation) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), incorrectLocationString, humanMustFinishTitle, JOptionPane.INFORMATION_MESSAGE);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public ClueGame() {
		gui = new ControlGUI();
		setSize(900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Board();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.deal();
		players = board.getPlayers();

		rand = new Random();

		addMouseListener(new MoveHumanPlayer());
		gui.nextPlayer.addActionListener(new NextPlayerClicked());
		cardsGUI = new MyCardsGUI(players.get(0));
		cardsGUI.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(FileMenu());
		add(cardsGUI, BorderLayout.EAST);
	}

	private JMenu FileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(DetectiveOption());
		menu.add(ExitOption());
		return menu;
	}

	private JMenuItem DetectiveOption() {
		JMenuItem option = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {

			@Override

			public void actionPerformed(ActionEvent arg0) {
				Detective = new DetectiveNotesDialog();
				Detective.setVisible(true);
			}
		}
		option.addActionListener(new MenuItemListener());
		return option;
	}

	private JMenuItem ExitOption() {
		JMenuItem option = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {

			@Override

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		}
		option.addActionListener(new MenuItemListener());
		return option;
	}

	public static void main(String[] args) {
		boolean gameOver = false;
		ClueGame game = new ClueGame();
		JOptionPane pane = new JOptionPane();
		pane.showMessageDialog(game, message, title, JOptionPane.INFORMATION_MESSAGE);
		game.setVisible(true);

		while (!gameOver) {


		}
	}

}
