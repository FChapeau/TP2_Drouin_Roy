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
		}
		catch (IOException | LipeRMIException e)
		{
			e.printStackTrace();
		}
		//System.out.println("client done");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void showResult(String _result, String _throwerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSanity(int _newSanity) {
		// TODO Auto-generated method stub
		
	}
	
	public void Attack(String nameDefender)
	{
		//myRemoteObject.attackCultist(idClient, nameDefender);
	}

	@Override
	public int askHorus() {
		// TODO Auto-generated method stub
		 
		return 0;
	}

	@Override
	public void showNewSanity(String name, int sanity) {
		// TODO Auto-generated method stub
		window.changePlayerShownHealth(name, sanity);
	}

	@Override
	public void showNextPlayer(String _nextPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String chooseTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
