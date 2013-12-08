package edu.csf.common;

import java.io.IOException;


public interface IServer {
	public boolean connect(String _name, IWatcher _controller) throws IOException;
	public void attack(String _defenderName);
	public void receiveMessage(String _sender, String _message);
	public String[] getCultistList();
}
