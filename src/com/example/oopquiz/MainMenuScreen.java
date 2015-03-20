package com.example.oopquiz;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.ProfileManager;
import com.example.oopquiz.Managers.QuizManager;

public class MainMenuScreen extends MyActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_screen);
		AppManager.mainScreen = this;
		ButtonManager.initNewScreen(this, activityName.mainMenu);
		createButtons();
		assignText();
	}
	
	ButtonUI temp ;
	public void createButtons()
	{
		int[] buttons = new int[]{ R.id.play, R.id.leadboards, R.id.exit, R.id.bgLogo, R.id.settings, R.id.fb, R.id.twitter, R.id.savedQuestions};
		for(int i = 0; i < buttons.length; i++)
		{
			temp = ButtonManager.createButton(buttons[i]);
			ColorManager.changeButtonColor(temp);
			int width = temp.getButton().getWidth();
	        if (width > 0) 
	        {
	        	if(temp.getButton().getId() != R.id.play || temp.getButton().getId() != R.id.bgLogo)
	        	{
	        		LayoutParams layoutParams = temp.getButton().getLayoutParams();
	        		layoutParams.height = (int) (width / 3);
	        	}
	        }
		}
		ColorManager.changeLayoutColor((ViewGroup)findViewById(R.id.layoutBarMenu));
		
	}
	
	public void assignText()
	{
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.layoutBarMenu);
		TextView coins = (TextView)layout.findViewById(R.id.coins);
		coins.setText(Integer.toString(ProfileManager.myProfile.getCoins()));
		TextView exp = (TextView)layout.findViewById(R.id.expPoints);
		exp.setText(Integer.toString((int)ProfileManager.myProfile.getExp()));
	}
	
	public void clickOn(ButtonUI button)
	{
		int id = button.getButton().getId();
		switch(id)
		{
			case R.id.play :
				QuizManager.startScreen(this, CategorySelectScreen.class);
				break;
			case R.id.leadboards :
				QuizManager.startScreen(this, LeadBoardScreen.class);
				break;
			case R.id.settings :
				QuizManager.startScreen(this, SettingsScreen.class);
				break;
			case R.id.savedQuestions : 
				QuizManager.startScreen(this, SavedQuestionsScreen.class);
				break;
			case R.id.fb : 
				//QuizManager.startScreen(this, .class);
				break;
			case R.id.twitter : 
				//QuizManager.startScreen(this, LeadBoardScreen.class);
				break;
			case R.id.exit :
				
				break;
		}
		finishScreen();
	}
	
	public void finishScreen()
	{
		ButtonManager.cleanScreenButtons(activityName.mainMenu);
		this.finish();
	}
}
