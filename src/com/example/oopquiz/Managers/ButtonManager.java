package com.example.oopquiz.Managers;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.widget.Button;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Elements.screenState;

public class ButtonManager {
	
	
	private static ArrayList<ButtonUI> buttons;
	private static Activity currentActivity;
	private static activityName currentActivityName;
	
	public static screenState currentScreenState;
	
	public static void initNewScreen(Activity act, activityName actName)
	{
		currentActivity = act;
		currentActivityName = actName;
		currentScreenState = screenState.clickable;
		int[] a = new int[3];
	}
	
	public static void setButtonsClickable(boolean clickable) 
	{
		if(clickable)
			currentScreenState = screenState.clickable;
		else
			currentScreenState = screenState.blocked;
	}

	public static ButtonUI getButton(int resButton)
	{
		for(ButtonUI button : buttons)
		{
			if(button.getButton().getId() == resButton)
				return button;
		}
		return null;
	}
	
	public static ButtonUI createButton(Button button, activityName actName, int id)
	{
		
		ButtonUI buttonUI = new ButtonUI(button, actName, id); // id used of reference to buttons on items of viewList. onClick -> id knows which button was pressed of the item. (id=0,1st item), id=2 3rd element 
		buttons.add(buttonUI);
		return buttonUI;
	}
	
	public static ButtonUI createButton(Button button, activityName actName)
	{
		ButtonUI buttonUI = new ButtonUI(button, actName);
		buttons.add(buttonUI);
		return buttonUI;
	}
	
	
	public static ButtonUI createButton(int res)
	{
		if(buttons == null)
			buttons = new ArrayList<ButtonUI>();
			
		for(ButtonUI button : buttons)
		{
			if(res == button.getButton().getId())
				return button;
		}
		
		Button button = (Button)currentActivity.findViewById(res);
		ButtonUI newButton = new ButtonUI(button, currentActivityName);
		buttons.add(newButton);
		return newButton;
	}
	
	public static void cleanScreenButtons(activityName actName)
	{
		ArrayList<ButtonUI> toRemove = new ArrayList<ButtonUI>();
		for(ButtonUI but : buttons)
		{
			if(actName.equals(but.getActivityName()))
				toRemove.add(but);
		}
		for(ButtonUI but : toRemove)
			buttons.remove(but);
		
		toRemove.clear();
	}
}
