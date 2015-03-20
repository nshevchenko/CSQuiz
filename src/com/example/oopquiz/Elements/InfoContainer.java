package com.example.oopquiz.Elements;

import java.util.ArrayList;

import com.example.oopquiz.Managers.ProfileManager;

public class InfoContainer {
	
	
	private static ArrayList<InfoProfile> data;
	
	
	public static void init()
	{
		data = new ArrayList<InfoProfile>();
		data.add(new InfoProfile("Exp Points  ", ProfileManager.myProfile.getExp() ));
		data.add(new InfoProfile("Quiz Played  ", 2));
		data.add(new InfoProfile("Ratio  ", ProfileManager.myProfile.getExp() ));
		data.add(new InfoProfile("Best score ", ProfileManager.myProfile.getExp() ));
		data.add(new InfoProfile("Level ", ProfileManager.myProfile.getExp() ));
	}
	
	public static ArrayList<InfoProfile> getData()
	{
		return data;
	}
}
