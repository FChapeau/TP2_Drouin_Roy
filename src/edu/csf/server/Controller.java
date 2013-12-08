package edu.csf.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;

import edu.csf.common.IServer;
import edu.csf.common.IWatcher;
import edu.csf.server.model.GameModel;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.IServerListener;
import net.sf.lipermi.net.Server;

public class Controller extends Server implements IServer 
{
	CallHandler callHandler;
	private final GameModel gameModel;
	private boolean gameStarted;
	
	public Controller()
	{
		callHandler = new CallHandler();
		gameModel = new GameModel(this);
		
		try {
			callHandler.registerGlobal(IServer.class, this);
			this.bind(12345, callHandler);
			System.out.println("Server ready");
		} catch (IOException e)
		{
			
		} catch (LipeRMIException e) {
			e.printStackTrace();
		}
		gameStarted = false;
		registerServerListener();
		waitForStart wait = new waitForStart();
		wait.run();
	}

	@Override
	public boolean connect(String _name, IWatcher _controller) throws IOException 
	{
		if(gameStarted)
		{
			throw new IOException("The game already started");
		}
		return gameModel.addCultist(_name, _controller);
	} 
	
	private void startGame()
	{
		gameStarted = true;
		gameModel.notifyOfPlayerChange(gameModel.getCultistList().get(gameModel.getCurrentPlayer()).getName());
	}

	@Override
	public void attack(String _defenderName) 
	{

		Attacker a = new Attacker(_defenderName);
		a.start();
	}

	@Override
	public void receiveMessage(String _sender, String _message) {
		Messenger m = new Messenger(_sender, _message);
		m.start();
	}
	
	private void registerServerListener()
	{
		this.addServerListener(new ServerListener());
	}
	
	private class ServerListener implements IServerListener{
		public void clientConnected(Socket socket)
		{
			System.out.println("Client connected: " + socket.getInetAddress());
		}
		
		public void clientDisconnected(Socket socket)
		{
			System.out.println("Client disconnected: " + 
					socket.getInetAddress());
			gameModel.notifyToClose();
			
		}
	}
	


	@Override
	public String[] getCultistList() {
		return gameModel.getCultistNameList();
		
	}
	private class waitForStart extends Thread
	{
		String start = "";
		@Override
		public void run() 
		{
			while (!start.equals("start") && gameModel.getCultistList().size() < 2)
			{
				System.out.println("Enter «start» to start : ");
				 
				try{
				    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				    start = bufferRead.readLine();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}

			}
			
			startGame();
			
		}
		
	}
	
	private class Attacker extends Thread
	{
		String target;
		
		public Attacker(String _target)
		{
			target = _target;
		}
		
		@Override
		public void run ()
		{
			if (!gameModel.getEndGame())
			{
				gameModel.attack(gameModel.getCultistList().get(gameModel.getCurrentPlayer()).getName(), target);
				gameModel.attack(target, gameModel.getCultistList().get(gameModel.getCurrentPlayer()).getName());
				gameModel.checkEndGame();
				if(!gameModel.getEndGame())
				{
					gameModel.nextPlayer();
				}	
			}
		}
	}
	
	private class Messenger extends Thread
	{
		String sender;
		String message;
		
		public Messenger(String _sender, String _message)
		{
			sender = _sender;
			message = _message;
		}

		@Override
		public void run() 
		{
			gameModel.broadcastMessage(sender, message);
		}
	}

}
