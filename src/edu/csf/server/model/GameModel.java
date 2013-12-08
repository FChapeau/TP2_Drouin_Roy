package edu.csf.server.model;

import java.util.ArrayList;

import edu.csf.common.IWatcher;
import edu.csf.server.Controller;

public class GameModel 
{

	private int chtulhuSanity;
	private ArrayList<Cultist> cultistList;
	private final GameMechanics gameMechanics;
	private final Controller controller;
	private ArrayList<IWatcher> watchers;
	private int currentPlayer;
	private boolean endGame;
	
	public GameModel(Controller _controller)
	{
		chtulhuSanity = 0;
		controller = _controller;
		cultistList = new ArrayList<Cultist>();
		gameMechanics = new GameMechanics(this);
		endGame = false;
		currentPlayer = 0;
		watchers = new ArrayList<IWatcher>();
		endGame = false;
		currentPlayer = 0;
	}
	

	public boolean getEndGame()
	{
		return endGame;
	}
	
	public void attack(String _attacker, String _defenderName)
	{
		Cultist attacker = getCultistFromName(_attacker);
		Cultist defender = getCultistFromName(_defenderName);
		
		gameMechanics.rollDie(attacker, defender);	
	}
	
	private Cultist getCultistFromName(String _name)
	{
		Cultist cultistFound = null;
		
		for (Cultist cultist : cultistList)
		{
			if (cultist.getName().equals(_name))
			{
				cultistFound = cultist;
			}
		}
		
		return cultistFound;
	}
	
	public void notifyDiceResult(String _result, String _throwerName)
	{
		for (IWatcher w : watchers)
		{
			w.showResult(_result, _throwerName);
		}
	}
	
	public void notifyAttackerToAttack()
	{
		for (IWatcher w : watchers)
		{
			if (w.getName() == cultistList.get(currentPlayer).getName())
			{
				w.yourTurn();
			}
		}
	}
	
	public void notifyNewSanity(Cultist _cultist)
	{
		for (IWatcher w : watchers)
		{
			w.showNewSanity(_cultist.getName(), _cultist.getSanity());
			if (w.getName().equals(_cultist.getName()))
			{
				w.setSanity(_cultist.getSanity());
			}
		}
	}
	
	public boolean addCultist(String _name, IWatcher _watcher)
	{
		for (Cultist cultist : cultistList)
		{
			if (cultist.getName().equals(_name))
			{
				return false;
			}
		}
		
		notifyOfCultistAdded(_name);
		watchers.add(_watcher);
		cultistList.add(new Cultist(_name));
		
		return true;
	}
	
	public boolean decrementChtulhuSanity()
	{
		boolean tookSanity = false;
		if (chtulhuSanity > 0)
		{
			chtulhuSanity -= 1;
			tookSanity = true;
		}
		return tookSanity;
	}

	public void incrementCthulhuSanity() 
	{
		chtulhuSanity += 1;
		
	}

	public ArrayList<Cultist> getCultistList() 
	{
		return cultistList;
	}

	public int notifyOfHorus(Cultist _attacker) 
	{
		int result = 0;
		for (IWatcher w : watchers)
		{
			if(w.getName().equals(_attacker.getName()))
			{
				result = w.askHorus();
			}
		}
		return result;
	}

	public void nextPlayer()
	{
		if (currentPlayer >= cultistList.size())
		{
			currentPlayer = 0;
		}
		else
		{
			currentPlayer += 1;
		}
		notifyOfPlayerChange(cultistList.get(currentPlayer).getName());
	}
	
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	
	public void checkEndGame()
	{
		int notCrazy = 0;
		String winner = null;		
		for (Cultist c : cultistList)
		{
			if(c.getSanity() > 0)
			{
				notCrazy ++;
				winner = c.getName();
			}
		}		
		if (notCrazy <= 1)
		{
			endGame = true;
			if (notCrazy == 0)
			{
				notifyOfGameEnd("Chtulhu");
			}
			else
			{
				notifyOfGameEnd(winner);
			}
		}		
	}
	
	public void notifyOfPlayerChange(String _nextPlayer)
	{
		for (IWatcher w : watchers)
		{
			w.showNextPlayer(_nextPlayer);
		}
	}
	
	private void notifyOfGameEnd(String _winner)
	{
		for (IWatcher w : watchers)
		{
			w.showWinner(_winner);
		}
	}


	public void broadcastMessage(String _sender, String _message) 
	{
		messageThread mess = new messageThread(_sender, _message); 
		mess.start();
	}
	
	private class messageThread extends Thread
	{
		String sender;
		String message;
		
		public messageThread(String _sender, String _message)
		{
			sender = _sender;
			message = _message;
		}

		@Override
		public void run() 
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (IWatcher w : watchers)
			{
				w.printMessage(sender, message);
			}
		}
		
	}
	
	private void notifyOfCultistAdded(String _name)
	{
		for (IWatcher w : watchers)
		{
			w.addNewPlayer(_name);
		}
	}
	
	public String[] getCultistNameList()
	{
		String[] output = new String[cultistList.size()];
		
		for (int i = 0; i < cultistList.size(); i++)
		{
			output[i] = cultistList.get(i).getName();
		}
		
		return output;
	}

}


