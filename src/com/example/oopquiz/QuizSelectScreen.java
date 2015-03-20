package com.example.oopquiz;


import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.Category;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.Quiz;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.QuizGridAdapter;
import com.example.oopquiz.Managers.QuizManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


public class QuizSelectScreen extends MyActivity {

	private GridView gridView;
	private Category currentCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_level_screen);
		backActivityName = CategorySelectScreen.class;
		String categoryName = "";
		Bundle extra = getIntent().getExtras();
		if(extra != null)
			categoryName = extra.getString("categoryName");
		
		if(categoryName.length() == 0) 
			finish();
		
		currentCategory = AppManager.findCategory(categoryName);
		
		AppManager.quizSelectScreen = this;
		ButtonManager.initNewScreen(this, activityName.quizSelect);
		findObjects();
		
		QuizGridAdapter gridAdapter = new QuizGridAdapter(this, currentCategory.getQuizzes());
        gridView.setAdapter(gridAdapter);
	}
	
	
	
	public void findObjects()
	{
		ButtonUI temp = ButtonManager.createButton(R.id.back);
		gridView = (GridView)findViewById(R.id.gridViewCategories);
		temp = new ButtonUI((Button)findViewById(R.id.title), activityName.levelSelect, false);
		temp.getButton().setText("Choose Quiz");
		ColorManager.changeButtonColor(temp);
	}
	
	public void clickOn(ButtonUI button)
	{
		int id = button.getButton().getId();
		switch(id)
		{
			case R.id.back :
				QuizManager.startScreen(this, CategorySelectScreen.class);
				break;
			default :
				for(Quiz quiz : currentCategory.getQuizzes())
				{
					System.out.println("buttons name + " + button.getButton().getText());
					if(button.getButton().getText().equals(quiz.getName()))
					{
						QuizManager.startNewQuiz(this, quiz );
						for(Question quest : quiz.getQuestions())
						{
							System.out.println(quest.getId() + " " + quest.getQuestion() );
						}
					}
				}
				break;
		}
		finishScreen();
	}
	
	
	public void finishScreen()
	{
		ButtonManager.cleanScreenButtons(activityName.quizSelect);
		this.finish();
	}
}
