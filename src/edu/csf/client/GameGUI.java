package edu.csf.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class GameGUI {

	JFrame frmCthulhuDice;
	private JTextField messageToSend;

	/**
	 * Create the application.
	 */
	public GameGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCthulhuDice = new JFrame();
		frmCthulhuDice.setTitle("Cthulhu Dice");
		frmCthulhuDice.setBounds(100, 100, 589, 472);
		frmCthulhuDice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmCthulhuDice.getContentPane().setLayout(springLayout);
		
		JMenuBar menuBar = new JMenuBar();
		springLayout.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, menuBar, 0, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, menuBar, 573, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		frmCthulhuDice.getContentPane().add(menuBar);
		
		JPanel chatPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, chatPanel, 6, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.WEST, chatPanel, 10, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		frmCthulhuDice.getContentPane().add(chatPanel);
		
		JPanel gamePanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, gamePanel, 6, SpringLayout.SOUTH, menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		springLayout.putConstraint(SpringLayout.WEST, gamePanel, 389, SpringLayout.WEST, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, gamePanel, -10, SpringLayout.SOUTH, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, gamePanel, -10, SpringLayout.EAST, frmCthulhuDice.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, chatPanel, 0, SpringLayout.SOUTH, gamePanel);
		springLayout.putConstraint(SpringLayout.EAST, chatPanel, -6, SpringLayout.WEST, gamePanel);
		SpringLayout sl_chatPanel = new SpringLayout();
		chatPanel.setLayout(sl_chatPanel);
		
		JTextPane textPane = new JTextPane();
		sl_chatPanel.putConstraint(SpringLayout.NORTH, textPane, 10, SpringLayout.NORTH, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.WEST, textPane, 10, SpringLayout.WEST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.EAST, textPane, 363, SpringLayout.WEST, chatPanel);
		chatPanel.add(textPane);
		
		messageToSend = new JTextField();
		sl_chatPanel.putConstraint(SpringLayout.WEST, messageToSend, 10, SpringLayout.WEST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, textPane, -6, SpringLayout.NORTH, messageToSend);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, messageToSend, -10, SpringLayout.SOUTH, chatPanel);
		chatPanel.add(messageToSend);
		messageToSend.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sl_chatPanel.putConstraint(SpringLayout.EAST, sendButton, -10, SpringLayout.EAST, chatPanel);
		sl_chatPanel.putConstraint(SpringLayout.EAST, messageToSend, -6, SpringLayout.WEST, sendButton);
		sl_chatPanel.putConstraint(SpringLayout.SOUTH, sendButton, -10, SpringLayout.SOUTH, chatPanel);
		chatPanel.add(sendButton);
		frmCthulhuDice.getContentPane().add(gamePanel);
		SpringLayout sl_gamePanel = new SpringLayout();
		gamePanel.setLayout(sl_gamePanel);
		
		JList playerList = new JList();
		sl_gamePanel.putConstraint(SpringLayout.NORTH, playerList, 10, SpringLayout.NORTH, gamePanel);
		sl_gamePanel.putConstraint(SpringLayout.WEST, playerList, 10, SpringLayout.WEST, gamePanel);
		sl_gamePanel.putConstraint(SpringLayout.EAST, playerList, 164, SpringLayout.WEST, gamePanel);
		gamePanel.add(playerList);
		
		JButton attackButton = new JButton("Attack");
		sl_gamePanel.putConstraint(SpringLayout.WEST, attackButton, 10, SpringLayout.WEST, gamePanel);
		sl_gamePanel.putConstraint(SpringLayout.SOUTH, playerList, -6, SpringLayout.NORTH, attackButton);
		sl_gamePanel.putConstraint(SpringLayout.SOUTH, attackButton, -10, SpringLayout.SOUTH, gamePanel);
		gamePanel.add(attackButton);
	}
}
