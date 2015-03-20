package com.example.oopquiz.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.oopquiz.Managers.AppManager;


public class Profile {

	private String id, userName;
	public int expPoints;
	public String[] top3;
	
	
	/**
     * Instantiates a new Profile with param name
     * @param name of the user
     */
	public Profile(String name)
	{
		userName = name;
		loadAvatar();
	}
	
	/**
     * Instantiates a new Profile with param name
     * @param String name - of the user
     * @param int _expPoints  - experience points
     */
	public Profile(String name, int _expPoints)
	{
		userName = name;
		expPoints = _expPoints;
		loadAvatar();
	}
	
	/**
     * Instantiates a new Profile with param name
     * @param String name - of the user
     * @param int _expPoints  - experience points
     * @param String[] _top3  - top 3 categories of the user
     */
	public Profile(String name, int _expPoints, String[] _top3)
	{
		userName = name;
		expPoints = _expPoints;
		top3 = _top3;
		loadAvatar();
	}

	/**
     * Instantiates a new Profile with param name
     * @param String name - name of the user
     * @param String[] _top3 - top 3 categories of the user
     */
	public Profile(String name, String[] _top3)
	{
		userName = name;
		top3 = _top3;
		loadAvatar();
	}
	
	public void loadAvatar()
	{
		top3 = new String[]{"...", "...", "..."};
	}
	

	public String getId()
	{
		return id;
	}
	
	public void setName(String name)
	{
		userName = name;
	}
	
	public void setId(String _id)
	{
		id = _id;
	}
	
	public String getName()
	{
		return userName;
	}
	
	public int getExp()
	{
		return expPoints;
	}
	
	public void updateProfileInfo() // calculate exp points and update top 3 categories
	{
		expPoints = 0;
		
		for(Category category : AppManager.categories)
		{
			int singleCategoryExp = category.makeSumExpPoints();
			expPoints += singleCategoryExp;
		}
		Collections.sort(AppManager.categories, new Comparator<Category>(){
			  public int compare(Category cat1, Category cat2) {
			     return Integer.valueOf(cat1.getSumExpPoints()).compareTo(cat2.getSumExpPoints());
			  }
		});
		Collections.reverse(AppManager.categories);
		
		ArrayList<String> catWithPoints = new ArrayList<String>();
		for(int i = 0; i < AppManager.categories.size(); i++)
		{
			if(AppManager.categories.get(i).getSumExpPoints() > 0)
				catWithPoints.add(AppManager.categories.get(i).getName());
		}
		for(int i = 0; i < 3; i ++)
			if( i < catWithPoints.size())
				top3[i] = catWithPoints.get(i);
			else
				top3[i] = "...";
	}

}
