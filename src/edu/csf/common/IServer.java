package edu.csf.common;

import edu.csf.client.Controller;
import edu.csf.server.model.Cultist;


public interface IServer {
	public int connect(String _name, IWatcher _controller) throws NameTakenException;
	public void attackCultist(int _idAttacker, String _nameDefender);

}
