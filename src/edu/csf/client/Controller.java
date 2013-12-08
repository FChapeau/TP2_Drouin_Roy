package edu.csf.client;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.Serializable;

import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import edu.csf.common.IServer;
import edu.csf.common.IWatcher;

public class Controller implements IWatcher, Serializable{

	private static final long serialVersionUID = 1689248112899834256L;
	IServer myRemoteObject;
	String name;
	GameGUI window;
	int idClient;
	
	public Controller()
	{
		initializeGUI();
	}
	
	private void initializeGUI()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new GameGUI(Controller.this);
					window.frmCthulhuDice.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initializeConnection(String connectionString)
	{
		CallHandler callHandler = new CallHandler();
		String[] splitConnectionString = connectionString.split(":");
		try
		{
			if (splitConnectionString[0].matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))
			{
				if (splitConnectionString.length == 2)
				{
					int port = Integer.parseInt(splitConnectionString[2]);
					
					if (0 < port && port < 65535)
					{
						Client client = new Client(splitConnectionString[0], port, callHandler);
						myRemoteObject = (IServer) client.getGlobal(IServer.class);
						callHandler.registerGlobal(IWatcher.class, this);
					}
					else
					{
						// TODO Throw something relevant
						throw new ArrayIndexOutOfBoundsException();
					}
				}
				else if (splitConnectionString.length == 1)
				{
					Client client = new Client(splitConnectionString[0], 12345, callHandler);
					myRemoteObject = (IServer) client.getGlobal(IServer.class);
					callHandler.registerGlobal(IWatcher.class, this);
				}
				else
				{
					// TODO Throw something relevant
				}
			}
			
			Boolean connected = false;
			while (!connected)
			{
				name = window.askClientForName();
				connected = myRemoteObject.connect(name, this);
			}
			
			String[] cultistNameList = myRemoteObject.getCultistList();
			for (String s : cultistNameList)
			{
				if (!s.equals(name))
				{
					window.PlayerConnected(s, 3);
				}
			}
			window.setDisplayName(name);
		}
		catch (IOException | LipeRMIException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int showResult(String _result, String _throwerName) {
		String message = new String();
		message = _throwerName + " rolled " + _result + " on the dice!";
		window.printChatMessage("Server", message);
		
		return 0;
	}

	@Override
	public void setSanity(int _newSanity) {
		//window.changePlayerShownHealth(name, _newSanity);
		
	}
	
	public void Attack(String _nameDefender)
	{
		myRemoteObject.attack(_nameDefender.substring(0, _nameDefender.length()-4));
	}

	@Override
	public int askHorus() {
		// TODO Auto-generated method stub
		String input = window.askClientForHorus();
		
		switch (input)
		{
		case "Cthulhu":
			return 12;
		case "Elder Sign":
			return 10;
		case "Tentacle":
			return 6;
		case "Yellow Sign":
			return 1;
		}
		
		return 0;
	}

	@Override
	public void showNewSanity(String name, int sanity) {
		window.changePlayerShownHealth(name, sanity);
	}

	@Override
	public void showNextPlayer(String _nextPlayer) {
		window.printChatMessage("Server", "It is now " + _nextPlayer + "'s turn");
		window.toggleAttackButtonState(false);
	}


	@Override
	public void showWinner(String _winner) {
		window.printChatMessage("Server", "Game ends! The winner is: " + _winner + "!");
	}

	@Override
	public void printMessage(String _sender, String _message) {
		// TODO Auto-generated method stub
		window.printChatMessage(_sender, _message);
	}

	@Override
	public void yourTurn() {
		window.printChatMessage("Server", "It's your turn! Choose a target and press \"Attack\".");
		window.toggleAttackButtonState(true);
	}
	
	public void sendChatMessage(String message)
	{	
		myRemoteObject.receiveMessage(name, message);
	}
	
	@Override
	public void addNewPlayer(String name) {
		window.PlayerConnected(name, 3);
		
	}
}
