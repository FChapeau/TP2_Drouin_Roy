package edu.csf.server.model;

public class Cultist 
{
	
	private int sanity;
	private String name;
	
	private final int STARTING_SANITY = 3; 
	
	public Cultist(String _name)
	{
		sanity = STARTING_SANITY;
		name = _name;
	}
	
	
	public int getSanity() 
	{
		
		return sanity;
	}
	
	public void incrementSanity() 
	{
		sanity += 1;
	}
	public boolean decrementSanity() 
	{
		if (sanity > 0)
		{
			sanity -= 1;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getName() 
	{
		
		return name;
	}
	
	
	
}
