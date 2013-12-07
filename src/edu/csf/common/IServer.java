package edu.csf.common;

import edu.csf.client.Controller;
import edu.csf.server.model.Cultist;


public interface IServer {
	public boolean connect(String _name, IWatcher _controller);
	public void attack(String _defenderName);

}
