package edu.csf.client;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JMenuItem;

public class GameGUI {

	JFrame frmCthulhuDice;
	private JTextField messageToSend;
	private JTextPane messageBoard;
	private Controller controller;
	private JButton attackButton;
	private JList<String> playerList;
	private DefaultListModel<String>playerListData;

	/**
	 * Create the application.
	 */
	public GameGUI(Controller _controller) {
		controller = _controller;
		playerListData = new DefaultListModel<String>();
		initialize();
		toggleAttackButtonState(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCthulhuDice = new JFrame();
		frmCthulhuDice.setResizable(false);
		frmCthulhuDice.setTitle("Cthulhu Dice");
		frmCthulhuDice.setBounds(100, 100, 600, 470);
		frmCthulhuDice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmCthulhuDice.getContentPane().setLayout(springLayout);
		
		JMenuBar menuBar = new JMenuBar();
		springLayout.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, menuBar, 0, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, menuBar, 598, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		frmCthulhuDice.getContentPane().add(menuBar);
		
		JPanel chatPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, chatPanel, 6, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.WEST, chatPanel, 10, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, chatPanel, -10, SpringLayout.SOUTH, frmCthulhuDice.getContentPane());
		frmCthulhuDice.getContentPane().add(chatPanel);
		
		JPanel gamePanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, gamePanel, 6, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.SOUTH, gamePanel, -10, SpringLayout.SOUTH, frmCthulhuDice.getContentPane());
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		JMenuItem connectClient = new JMenuItem("Connect");
		connectClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectClient();
			}
		});
		mnGame.add(connectClient);
		
		JMenuItem showRules = new JMenuItem("Show the rules");
		mnGame.add(showRules);
		springLayout.putConstraint(SpringLayout.WEST, gamePanel, 389, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, gamePanel, -10, SpringLayout.EAST, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, chatPanel, -6, SpringLayout.WEST, gamePanel);
		SpringLayout sl_chatPanel = new SpringLayout();
		chatPanel.setLayout(sl_chatPanel);
		
		messageBoard = new JTextPane();
		messageBoard.setText("Welcome to Cthulhu Dice!");
		messageBoard.setEditable(false);
		sl_chatPanel.putConstraint(SpringLayout.NORTH, messageBoard, 10, SpringLayout.NORTH, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.WEST, messageBoard, 10, SpringLayout.WEST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.EAST, messageBoard, 363, SpringLayout.WEST, chatPanel);
		chatPanel.add(messageBoard);
		
		messageToSend = new JTextField();
		sl_chatPanel.putConstraint(SpringLayout.WEST, messageToSend, 10, SpringLayout.WEST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, messageBoard, -6, SpringLayout.NORTH, messageToSend);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, messageToSend, -10, SpringLayout.SOUTH, chatPanel);
		chatPanel.add(messageToSend);
		messageToSend.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sl_chatPanel.putConstraint(SpringLayout.EAST, sendButton, -10, SpringLayout.EAST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.EAST, messageToSend, -6, SpringLayout.WEST, sendButton);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, sendButton, -10, SpringLayout.SOUTH, chatPanel);
		chatPanel.add(sendButton);
		sendButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
			}
		});
		
		frmCthulhuDice.getContentPane().add(gamePanel);
		SpringLayout sl_gamePanel = new SpringLayout();
		gamePanel.setLayout(sl_gamePanel);
		
		playerList = new JList<String>();
		playerList.setModel(playerListData);
		sl_gamePanel.putConstraint(SpringLayout.NORTH, playerList, 10, SpringLayout.NORTH, gamePanel);
		sl_gamePanel.putConstraint(SpringLayout.WEST, playerList, 10, SpringLayout.WEST, gamePanel);
		sl_gamePanel.putConstraint(SpringLayout.EAST, playerList, 189, SpringLayout.WEST, gamePanel);
		gamePanel.add(playerList);
		
		attackButton = new JButton("Attack");
		attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAttack();
			}
		});
		sl_gamePanel.putConstraint(SpringLayout.WEST, attackButton, 10, SpringLayout.WEST, gamePanel);
		sl_gamePanel.putConstraint(SpringLayout.SOUTH, playerList, -6, SpringLayout.NORTH, attackButton);
		sl_gamePanel.putConstraint(SpringLayout.SOUTH, attackButton, -10, SpringLayout.SOUTH, gamePanel);
		gamePanel.add(attackButton);
	}
	
	private void connectClient()
	{
		controller.initializeConnection("127.0.0.1");
		toggleAttackButtonState(true);
	}
	
	private void btnAttack ()
	{
		
	}
	
	private void sendChatMessage()
	{
		printChatMessage("Me", messageToSend.getText());
	}
	
	public void printChatMessage(String source, String message)
	{
		Calendar calendar = Calendar.getInstance();
		String timestamp = "[" + Integer.toString(calendar.get(Calendar.HOUR)) + ":" + Integer.toString(calendar.get(Calendar.MINUTE)) + "]";
		messageBoard.setText(messageBoard.getText() + "\n" + timestamp + " " + source + ": " + message);
	}
	
	private void toggleAttackButtonState(boolean state)
	{
		attackButton.setEnabled(state);
	}
	
	public void PlayerConnected (String name, int life, int playerID)
	{
		playerListData.add(playerID, formatNameAndLife(name, life));
	}
	
	public String askClientForName()
	{
		return JOptionPane.showInputDialog("What is your name brother?");
	}
	
	public void changePlayerShownHealth (String name, int life)
	{
		int wantedIndex = 0;
		
		for (int i = 0; i < playerListData.getSize(); i++)
		{
			if (playerListData.get(i).equals(name))
			{
				wantedIndex = i;
			}
		}
		String nameToDisplay = formatNameAndLife(name, life);
		playerListData.setElementAt(nameToDisplay, wantedIndex);
	}
	
	private String formatNameAndLife(String name, int life)
	{
		return name + " (" + Integer.toString(life) + ")";
	}
	
	public String askClientForHorus()
	{
		String[] data = {"Cthlhu", "Elder Sign", "Tentacle", "Yellow Sign"};
		return (String) JOptionPane.showInputDialog(null, 
				"The dice came up on the Eye of Horus! Choose what symbol to apply.", 
				"Eye of Horus", 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				data, 
				data[0]);
	}
}
