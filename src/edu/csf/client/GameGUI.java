package edu.csf.client;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GameGUI {

	JFrame frmCthulhuDice;
	private JTextField messageToSend;
	private JTextArea messageBoard;
	private Controller controller;
	private JButton attackButton;
	private JList<String> playerList;
	private DefaultListModel<String>playerListData;
	private JMenuItem connectClient;

	/**
	 * Create the application.
	 */
	public GameGUI(Controller _controller) {
		controller = _controller;
		playerListData = new DefaultListModel<String>();
		initialize();
		setAttackButtonAvailabileState(false);
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
		
		connectClient = new JMenuItem("Connect");
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
		
		messageToSend = new JTextField();
		sl_chatPanel.putConstraint(SpringLayout.WEST, messageToSend, 10, SpringLayout.WEST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, messageToSend, -10, SpringLayout.SOUTH, chatPanel);
		chatPanel.add(messageToSend);
		messageToSend.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sl_chatPanel.putConstraint(SpringLayout.EAST, sendButton, -10, SpringLayout.EAST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.EAST, messageToSend, -6, SpringLayout.WEST, sendButton);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, sendButton, -10, SpringLayout.SOUTH, chatPanel);
		chatPanel.add(sendButton);
		
		messageBoard = new JTextArea();
		sl_chatPanel.putConstraint(SpringLayout.NORTH, messageBoard, 60, SpringLayout.NORTH, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.WEST, messageBoard, 10, SpringLayout.WEST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.EAST, messageBoard, 224, SpringLayout.WEST, chatPanel);
		messageBoard.setLineWrap(true);
		messageBoard.setText("Welcome to Cthulhu Dice!");
		messageBoard.setEditable(false);
		sendButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(messageBoard);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_chatPanel.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, sendButton);
		sl_chatPanel.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, sendButton);
		chatPanel.add(scrollPane);
		
		
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
	}
	
	public void addCultist(String name)
	{
		
	}
	
	private void btnAttack ()
	{
		controller.Attack(playerList.getSelectedValue());
		
	}
	
	private void sendChatMessage()
	{
		controller.sendChatMessage(messageToSend.getText());
	}
	
	public void printChatMessage(String source, String message)
	{
		Calendar calendar = Calendar.getInstance();
		String timestamp = "[" + Integer.toString(calendar.get(Calendar.HOUR)) + ":" + Integer.toString(calendar.get(Calendar.MINUTE)) + "]";
		messageBoard.setText(messageBoard.getText() + "\n" + timestamp + " " + source + ": " + message);
		messageBoard.setCaretPosition(messageBoard.getDocument().getLength());
	}
	
	public void setAttackButtonAvailabileState(boolean state)
	{
		attackButton.setEnabled(state);
	}
	
	public void PlayerConnected (String name, int life)
	{
		playerListData.addElement(formatNameAndLife(name, life));
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
			if (playerListData.get(i).substring(0, playerListData.get(i).length()-4).equals(name))
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
	
	public void setDisplayName(String name)
	{
		frmCthulhuDice.setTitle(frmCthulhuDice.getTitle() + " - " + name);
	}
	public JMenuItem getConnectClient() {
		return connectClient;
	}
	
	public void setConnetButtonAvailableState(boolean state)
	{
		connectClient.setEnabled(state);
	}

	public void cantAttackThisPlayer() {
		// TODO Auto-generated method stub
		
	}
}
