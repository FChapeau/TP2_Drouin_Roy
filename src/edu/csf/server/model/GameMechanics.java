package edu.csf.server.model;

import java.util.ArrayList;

public class GameMechanics 
{

	private final GameModel gameModel;
	
	private final String YELLOW_SIGN = "Signe Jaune";
	private final String TENTACLE = "Tentacule";
	private final String CHTULHU = "Chtulhu";
	private final String ELDER_SIGN = "Signe des Anciens";
	private final String HORUS_EYE = "Oeil d'Horus";
	
	public GameMechanics(GameModel _gameModel)
	{
		gameModel = _gameModel;
	}
	
	public void rollDie(Cultist _attacker, Cultist _defender)
	{
		int result = getRandomNumber(1, 12);
		
		interpretResult(result, _attacker, _defender);
	}
	
	private int getRandomNumber(int _lower, int _upper)
	{

		int random = (int)(Math.random() * (_upper + 1 - _lower)) + _lower;
		
		return random;
	}
	
	
	private void interpretResult(int _result, Cultist _attacker, Cultist _defender)
	{
		
		if (isBetween(_result, 1, 5))
		{
			gameModel.notifyDiceResult(YELLOW_SIGN, _attacker.getName());
			doYellowSign(_defender);
			
		}
		else if (isBetween(_result, 6, 9))
		{
			gameModel.notifyDiceResult(TENTACLE, _attacker.getName());
			doTentacle(_attacker, _defender);
		}
		else if (_result == 10)
		{
			gameModel.notifyDiceResult(ELDER_SIGN, _attacker.getName());
			doElderSign(_attacker);
		}
		else if (_result == 11)
		{
			gameModel.notifyDiceResult(HORUS_EYE, _attacker.getName());
			doHorus(_attacker, _defender);
		}
		else if (_result == 12)
		{
			gameModel.notifyDiceResult(CHTULHU, _attacker.getName());
			doChtulhu();
		}
	}
	
	private void doYellowSign(Cultist _defender)
	{
		if (_defender.decrementSanity())
		{
			gameModel.incrementCthulhuSanity();
			gameModel.notifyNewSanity(_defender);
		}
		
	}
	
	private void doTentacle(Cultist _attacker, Cultist _defender)
	{
		if (_defender.decrementSanity())
		{
			gameModel.notifyNewSanity(_defender);
			if (_attacker.getSanity() > 0)
			{
				_attacker.incrementSanity();
				gameModel.notifyNewSanity(_attacker);
			}
		}
	}
	
	private void doElderSign(Cultist _attacker)
	{
		if (gameModel.decrementChtulhuSanity())
		{
			_attacker.incrementSanity();
			gameModel.notifyNewSanity(_attacker);
		}
	}
	
	private void doChtulhu()
	{
		ArrayList<Cultist> cultistList = gameModel.getCultistList();
		
		for (Cultist cultist : cultistList)
		{
			if (cultist.decrementSanity())
			{
				gameModel.incrementCthulhuSanity();
				gameModel.notifyNewSanity(cultist);
			}
		}
		
	}
	
	private void doHorus(Cultist _attacker, Cultist _defender)
	{
		int result = gameModel.notifyOfHorus(_attacker);
		interpretResult(result, _attacker, _defender);
	}
	
	private boolean isBetween(int _number, int _lower, int _upper)
	{
		if (_number >= _lower && _number <= _upper)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
