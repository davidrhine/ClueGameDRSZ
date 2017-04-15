package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel{
	private JTextField currentPlayer;
	private JTextField dieRoll;
	private JTextField guess;
	private JTextField guessResponse;

	public ControlGUI()  //main function laying out the entire GUI
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = createTurnPanel();
		add(panel);
		panel = createInfoPanel();
		add(panel);
	}

	 private JPanel createTurnPanel() {    // makes the panel thats displays whose turn it is
		 	JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
		 	JLabel turnLabel = new JLabel("Current turn:");
		 	currentPlayer = new JTextField(20);
		 	currentPlayer.setText("");
		 	currentPlayer.setEditable(false);
		 	currentPlayer.setPreferredSize(new Dimension(20, 20));
			JButton nextPlayer = new JButton("Next Player");
			JButton accusation = new JButton("Make Accusation");
			panel.add(turnLabel);
			panel.add(currentPlayer);
			panel.add(nextPlayer);
			panel.add(accusation);
			panel.setBorder(new TitledBorder (new EtchedBorder()));
			return panel;
	}
	 
	private JPanel createInfoPanel() { //creates the panel that displays the roll of the dice
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new FlowLayout()); 
		
		JPanel diePanel = new JPanel();
		diePanel.setLayout(new FlowLayout());
		diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		diePanel.add(new JLabel("Roll: "));
		dieRoll = new JTextField();
		dieRoll.setEditable(false);
		dieRoll.setPreferredSize(new Dimension(35, 20));
		diePanel.add(dieRoll);
		
			
		JPanel guessPanel = new JPanel();		//creates guess panel
		guessPanel.setLayout(new GridLayout(2, 1));
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessPanel.add(new JLabel("Guess:"));
		guess = new JTextField();
		guess.setEditable(false);
		guess.setPreferredSize(new Dimension(300, 20));
		guessPanel.add(guess);
		
		
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1, 2));
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		guessResultPanel.add(new JLabel("Response:   "));
		guessResponse = new JTextField();
		guessResponse.setEditable(false);
		guessResponse.setPreferredSize(new Dimension(50, 20));
		guessResultPanel.add(guessResponse);
		
		
		masterPanel.add(diePanel, FlowLayout.LEFT); //Orients the GUI to our desired display
		masterPanel.add(guessPanel, FlowLayout.CENTER);
		masterPanel.add(guessResultPanel, FlowLayout.RIGHT);
		return masterPanel;
	}
	
	public static void main(String[] args) { //actually making the GUI
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Control GUI");
		frame.setSize(800, 190);	
		
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
}
