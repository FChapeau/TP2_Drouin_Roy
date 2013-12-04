package edu.csf.server;

import edu.csf.common.IServer;
import edu.csf.common.IWatcher;
import edu.csf.server.model.GameModel;
import net.sf.lipermi.net.Server;

public class Controller extends Server implements IServer 
{

	private final GameModel gameModel;
	
	public Controller()
	{
		gameModel = new GameModel(this);
	}
	
	@Override
	public void attackCultist(int _idAttacker, String _nameDefender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean connect(String _name, IWatcher _controller) 
	{
		return gameModel.addCultist(_name, _controller);
	} 
	

}
