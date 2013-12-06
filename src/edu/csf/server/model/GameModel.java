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
	
	public GameModel(Controller _controller)
	{
		chtulhuSanity = 0;
		controller = _controller;
		cultistList = new ArrayList<Cultist>();
		gameMechanics = new GameMechanics(this);
		
	}
	
	public void attack(String _attackerName, String _defenderName)
	{
		Cultist attacker = getCultistFromName(_attackerName);
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

	public void incrementCthulhuSanity() {
		chtulhuSanity += 1;
		
	}

	public ArrayList<Cultist> getCultistList() {
		return cultistList;
	}

	public int notifyOfHorus(Cultist _attacker) {
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

	

}
