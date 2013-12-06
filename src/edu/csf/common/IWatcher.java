package edu.csf.common;

public interface IWatcher {
	
	String getName();
	void showResult(String _result, String _throwerName);
	void setSanity(int _newSanity);
	int askHorus();
	void showNewSanity(String name, int sanity);
	void showNextPlayer(String _nextPlayer);
	String chooseTarget();
	void showWinner(String _winner);

}
