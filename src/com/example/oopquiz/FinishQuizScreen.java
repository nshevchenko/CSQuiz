package com.example.oopquiz;


import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.ListQuestionsViewAdapter;
import com.example.oopquiz.Managers.QuizManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class FinishQuizScreen extends MyActivity {
	
	private TextView score;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_quiz_screen);
		AppManager.finishQuizScreen = this;
		backActivityName = CategorySelectScreen.class;
		ButtonManager.initNewScreen(this, activityName.finishQuiz);
		ListView listView = (ListView)findViewById(R.id.listItems);
		listView.setAdapter( new ListQuestionsViewAdapter(this, R.layout.item_list_finish_quiz));
		findObjects();
		assignMarkText();
	}
	
	public void findObjects()
	{
		ButtonUI temp = ButtonManager.createButton(R.id.menuButton);
		ColorManager.changeButtonColor(temp);
		temp = ButtonManager.createButton(R.id.restartQuizButton);
		ColorManager.changeButtonColor(temp);
		temp = ButtonManager.createButton(R.id.title);
		temp.getButton().setText("Results Screen");
		ColorManager.changeButtonColor(temp);
		score = (TextView)findViewById(R.id.score);
	}
	
	public void assignMarkText()
	{
		int mark = 0;
		for(Question quest : QuizManager.getCurrentQuiz().getQuestions())
		{
			if(quest.answeredCorrect)
				mark++;
		}
		QuizManager.getCurrentQuiz().setMark((int)mark, true); // assign mark to the quiz which calcualtes exp points by itself
		score.setText("Mark : " + (int)mark + "/" + QuizManager.getCurrentQuiz().getQuestions().size() );
		
	}
	
	public void clickOn(ButtonUI button)
	{
		int id = button.getButton().getId();
		switch(id)
		{
			case R.id.menuButton :
				QuizManager.startScreen(this, MainMenuScreen.class);
				
				break;
			case R.id.restartQuizButton :
				QuizManager.startNewQuiz(this, QuizManager.getCurrentQuiz());
				
				break;
		}
		finishScreen();
	}
	
	public void finishScreen()
	{
		ButtonManager.cleanScreenButtons(activityName.finishQuiz);
		this.finish();
	}
}