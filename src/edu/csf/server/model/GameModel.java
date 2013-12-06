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
	
	public String notifyAttackerToAttack()
	{
		String target = "";
		for (IWatcher w : watchers)
		{
			if (w.getName() == cultistList.get(currentPlayer).getName())
			{
				target = w.chooseTarget();
			}
		}
		
		return target;
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
	}
	
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	
	private Cultist checkEndGame()
	{
		int notCrazy = 0;
		Cultist winner = null;
		
		for (Cultist c : cultistList)
		{
			if(c.getSanity() > 0)
			{
				notCrazy ++;
				winner = c;
			}
		}
		
		if (notCrazy <= 1)
		{
			endGame = true;
		}
		
		return winner;
	}
	
	private void notifyOfPlayerChange(String _nextPlayer)
	{
		for (IWatcher w : watchers)
		{
			w.showNextPlayer(_nextPlayer);
		}
	}

}
