package edu.csf.server;

import java.io.IOException;

import edu.csf.common.IServer;
import edu.csf.common.IWatcher;
import edu.csf.server.model.GameModel;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;

public class Controller extends Server implements IServer 
{
	CallHandler callHandler;
	private final GameModel gameModel;
	
	public Controller()
	{
		callHandler = new CallHandler();
		gameModel = new GameModel(this);
		
		try {
			callHandler.registerGlobal(IServer.class, this);
			this.bind(12345, callHandler);
			System.out.println("Server ready");
		} catch (LipeRMIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			
		}
	}

	@Override
	public boolean connect(String _name, IWatcher _controller) 
	{
		return gameModel.addCultist(_name, _controller);
	} 
	
	public void startGame()
	{
		if (gameModel.getCultistList().size() >= 2)
		{
			gameRun();
		}
	}
	
	private void gameRun()
	{
		while (!gameModel.getEndGame())
		{
			String defenderName;
			defenderName = gameModel.notifyAttackerToAttack();
			gameModel.attack(gameModel.getCultistList().get(gameModel.getCurrentPlayer()).getName(), defenderName);
			gameModel.attack(defenderName, gameModel.getCultistList().get(gameModel.getCurrentPlayer()).getName());
			gameModel.nextPlayer();
		}	
	}
	

}
