package edu.csf.client;

import java.awt.EventQueue;
import java.io.IOException;
<<<<<<< HEAD
import edu.csf.common.IServer;
import edu.csf.common.IWatcher;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;


=======

import edu.csf.common.IServer;
import edu.csf.common.IWatcher;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;


>>>>>>> master
public class Controller implements IWatcher{

	IServer myRemoteObject;
	String name;
<<<<<<< HEAD
=======
	
>>>>>>> master
	private void initializeGUI()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI(Controller.this);
<<<<<<< HEAD
					window.frmCthulhuDice.setVisible(true);
=======
					window.frame.setVisible(true);
>>>>>>> master
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeConnection()
	{
		CallHandler callHandler = new CallHandler();
		try
		{
			Client client = new Client("10.200.33.112", 12345, callHandler);
			myRemoteObject = (IServer) client.getGlobal(IServer.class);
			
			boolean connected = false;
			while (connected == false)
			{
				//prompt an input box to select name
				connected = myRemoteObject.connect(name, this);
			}
			
			//System.out.println(myRemoteObject.sayHello("d:^D"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//System.out.println("client done");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showResult(String _result, String _throwerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSanity(int _newSanity) {
		// TODO Auto-generated method stub
		
	}
}
