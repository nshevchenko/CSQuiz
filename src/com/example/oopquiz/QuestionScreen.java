package com.example.oopquiz;


import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Elements.screenState;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.QuizManager;

public class QuestionScreen extends MyActivity {
		
	private TextView timer, title, question;
	private ButtonUI[] answers;
	private Question currentQuestion;
	private int questionTimer; 
	
	Handler m_handler;
	Runnable m_handlerTask ;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_screen);
		answers = new ButtonUI[4];
		AppManager.questionScreen = this;
		ButtonManager.initNewScreen(this, activityName.question);
		findScreenObjects();
		
		ButtonManager.setButtonsClickable(true);
		System.out.println("ButtonManager.currentScreenStat " + ButtonManager.currentScreenState);
		questionTimer = AppManager.QUESTION_TIMER;
		currentQuestion = QuizManager.pickRandomQuestion();
		
		improviseColorButtons();
		updateSaveQuestionInfo(true);
		
		if(currentQuestion != null)
		{	
			drawQuest(currentQuestion);
		
			m_handler = new Handler();   
			m_handlerTask = new Runnable()
			{
				@Override 
				public void run() { 
					updateTime();
					m_handler.postDelayed(m_handlerTask, 1000);    
				} 
			};
			m_handlerTask.run();
		} 
		else		
			finishCurrentScreen(false);
	}
	
	public void improviseColorButtons()
	{
		for(int i = 0; i < 4; i++)
			ColorManager.changeButtonColor(answers[i]);
		ColorManager.changeViewColor(timer);
	}
	

	public void updateTime()
	{
		questionTimer--;
    	timer.setText("0:" + questionTimer);
    	if(questionTimer <=0)
    		finishCurrentScreen(false);
	}
	
	public void findScreenObjects()
	{
		timer 	 = (TextView) findViewById(R.id.timer);
		title 	 = (TextView) findViewById(R.id.title);
		question = (TextView) findViewById(R.id.question);
		
		int[] resHelpButtons = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4 };
		for(int i = 0; i < resHelpButtons.length; i++)
		{
			answers[i]  = ButtonManager.createButton(resHelpButtons[i]);
		}
		
		resHelpButtons = new int[]{ R.id.fiftyfifty, R.id.saveQuestion, R.id.skip, R.id.deleteOne };
		for(int i = 0; i < resHelpButtons.length; i++)
		{
			ButtonUI temp = ButtonManager.createButton(resHelpButtons[i]);
			ColorManager.changeButtonColor(temp);
		}
	}
	
	public void drawQuest(Question quest)
	{
		title.setText("Question : " + (QuizManager.getCurrentQuestion()+1) + "/" + QuizManager.getCurrentQuiz().getQuestions().size());
		question.setText(quest.getQuestion());
		answers[0].getButton().setText(quest.getAnswers()[0]);
		answers[1].getButton().setText(quest.getAnswers()[1]);
		answers[2].getButton().setText(quest.getAnswers()[2]);
		answers[3].getButton().setText(quest.getAnswers()[3]);
	}
	
	public void clickOn(ButtonUI button)
	{
		System.out.println("ButtonManager.currentScreenStat " + ButtonManager.currentScreenState);
		if(ButtonManager.currentScreenState == screenState.blocked || button == null)
			return;
		
		for(int i = 0; i < 4 ; i++)
		{
			if(button == answers[i])
			{
				if(currentQuestion.getCorrectAnswer() == i)
					answerQuestion(true, i);
				else
					answerQuestion(false, i);
				ButtonManager.setButtonsClickable(false);
			}
		}
		
		switch(button.getButton().getId())
		{
			
			case R.id.deleteOne : 
				//QuizManager.deleteAnswers(1);
				break;
			case R.id.fiftyfifty : 
				//QuizManager.deleteAnswers(2);
				break;
			case R.id.saveQuestion : 
				int prevState = currentQuestion.getSavedInfo();
				if(prevState == 1)
					currentQuestion.setSavedQuest(0, true);
				else
					currentQuestion.setSavedQuest(1, true);
				updateSaveQuestionInfo(false);
				
				String text = (currentQuestion.getSavedInfo() == 1) ? "Saved Question" : "Removed Question";
				Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 20);
				toast.show();
				
				break;
			case R.id.skip :
				answerQuestion(false, -1);
				//ButtonManager.setButtonsClickable(false);
				break;
			default : 
				break;
		}
	}
	
	public void updateSaveQuestionInfo(boolean init)
	{
		ImageView icon = (ImageView) findViewById(R.id.saveQuestionImg);
		ColorManager.changeButtonColor(ButtonManager.getButton(R.id.saveQuestion));
		if(currentQuestion.getSavedInfo() == 1)
			icon.setImageResource(R.drawable.save_question_icon_on);
		else
			icon.setImageResource(R.drawable.saved_questions);		
	}
	
	public void answerQuestion(boolean correctAnswer, int buttonClicked)
	{
		currentQuestion.setUserAnswer(buttonClicked);
		currentQuestion.answeredCorrect = correctAnswer;
		
		if(buttonClicked >= 0)
		{
			if(correctAnswer)
				answers[buttonClicked].getButton().getBackground().setColorFilter(Color.GREEN, Mode.MULTIPLY);
			else
				answers[buttonClicked].getButton().getBackground().setColorFilter(Color.RED, Mode.MULTIPLY);
		}
		finishCurrentScreen(true);
	}
	
	public void finishCurrentScreen(boolean answered)
	{
		m_handler.removeCallbacks(m_handlerTask);
		if( !answered )
			currentQuestion.answeredCorrect = false;
		ButtonManager.cleanScreenButtons(activityName.question);
		QuizManager.nextQuestion(this);
		this.finish();
	}
}
