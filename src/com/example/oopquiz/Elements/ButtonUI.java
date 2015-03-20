package com.example.oopquiz.Elements;

import android.graphics.PorterDuff.Mode;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;

public class ButtonUI {
	
	private Button button;
	private activityName name;
	private int buttonColor, onFocusColor, id;
	private boolean clickable;
	
	public Button getButton()
	{
		return button;
	}
	
	public void setNotClickable()
	{
		clickable = false;
	}
	
	public int getId()
	{
		return id;
	}
	public void changeColor(int newColor)
	{
		button.getBackground().setColorFilter(newColor, Mode.MULTIPLY);
		buttonColor = newColor;
		onFocusColor = ColorManager.getOnFocusColor(newColor, -0.2F);
	}
	
	public activityName getActivityName()
	{
		return name;
	}
	
	
	public ButtonUI(Button _button, activityName actName, boolean _clickable)
	{
		clickable = false;
		buttonSetUp(_button, actName);
	}
	
	public ButtonUI(Button _button, activityName actName, int id)
	{
		clickable = true;
		this.id = id;
		buttonSetUp(_button, actName);
	}
	
	public ButtonUI(Button _button, activityName actName)
	{
		clickable = true;
		buttonSetUp(_button, actName);
	}
	
	public void buttonSetUp(Button _button, activityName actName)
	{
		button = _button;
		name = actName;
		addListeners();
		addTouchListener();
		buttonColor = 0;
	}
	
	public void onFocusUpdate(boolean onFocus)
	{
		if(buttonColor == 0 )
			return;
		if(onFocus)
			button.getBackground().setColorFilter(onFocusColor, Mode.MULTIPLY);
    	else
    		button.getBackground().setColorFilter(buttonColor, Mode.MULTIPLY);
	}
	
	public void buttonClickable(boolean clickable)
	{
		this.clickable = clickable; 
	}
	
	public void addTouchListener()
	{
		if( ! clickable ) return;
		button.setOnTouchListener(new OnTouchListener() {

		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		    	 if (event.getAction() == MotionEvent.ACTION_DOWN) {
		             // pointer goes down
		    		 onFocusUpdate(true);
		         } else if (event.getAction() == MotionEvent.ACTION_HOVER_EXIT) {
		             // pointer goes up
		        	 onFocusUpdate(false);
		         }
		     return false;
		    }
		   });
	}
	
	public void addListeners()
	{
		if( !clickable || ButtonManager.currentScreenState == screenState.blocked) 
		{
			System.out.println("blokced");
			return;
		}
		
		button.setOnClickListener(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						button.getBackground().setColorFilter(buttonColor, Mode.MULTIPLY);
					
						switch(name)
						{
							case mainMenu :
								AppManager.mainScreen.clickOn(ButtonUI.this);
								break;
							case levelSelect :
								AppManager.levelSelect.clickOn(ButtonUI.this);
								break;
							case question :
								AppManager.questionScreen.clickOn(ButtonUI.this);
								break;
							case finishQuiz :
								AppManager.finishQuizScreen.clickOn(ButtonUI.this);
								break;
							case quizSelect :
								AppManager.quizSelectScreen.clickOn(ButtonUI.this);
								break;
							case profileScreen :
								AppManager.profileScreen.clickOn(ButtonUI.this);
								break;
							case settings :
								AppManager.settingsScreen.clickOn(ButtonUI.this);
								break;
							case leadboards :
								AppManager.leadBoardScreen.clickOn(ButtonUI.this);
								break;
							case savedQuestion :
								AppManager.savedQuestions.clickOn(ButtonUI.this);
						}
						
					}
				}
		);
	}
	
}
