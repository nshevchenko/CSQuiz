package com.example.oopquiz.Elements;

public class MyProfile extends Profile{

	/**
     * Returns the amount of coins
     * @return coins of the user
     */
	public int coins;
	
	public MyProfile(String _name)
	{
		super(_name);
	}
	
	public MyProfile(String _name, int _coins)
	{
		super(_name);
		coins = _coins;
	}
	
	public MyProfile(String _name, String[] top3)
	{
		super(_name, top3);
	}
	
	public int getCoins()
	{
		return coins;
	}
	
	public void addCoins(int _coins)
	{
		coins += _coins;
	}
	
	public void setCoins(int _coins)
	{
		coins = _coins;
	}
	
	
}
