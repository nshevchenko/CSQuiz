package com.example.oopquiz.Elements;

import java.util.ArrayList;

import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ProfileManager;


public class Quiz {
	
	private int attempts;
	private String quizName;
	private int mark, expPoints;
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	public Quiz(String _quizName)
	{
		attempts = 0;
		quizName = _quizName;
		questions = new ArrayList<Question>();
	}
	
	public Question findQuestionById(int id)
	{
		for(Question quest : questions)
			if(quest.getId() == id)
				return quest;
		return null;
	}
	
	public void setMark(int curQuizMark, boolean updateMyProfile)
	{
		if(curQuizMark > mark)
			mark = curQuizMark;
		int coinsToAdd = 0;
		
		for(Question question : questions)
		{
			int questPlusPointsID = question.getPlusPointsID();
			
			if(question.answeredCorrect)
			{
				System.out.println("attemps : " + attempts + "... questPlusPointsID " + questPlusPointsID + "... coinsToAdd : " + AppManager.QUESTION_COINS[questPlusPointsID] );
				coinsToAdd +=  AppManager.QUESTION_COINS[questPlusPointsID];
				expPoints  +=  AppManager.QUESTION_POINTS[questPlusPointsID];
			}
			if(questPlusPointsID < AppManager.QUESTION_COINS.length-1)
				question.updatePlusExpPoints(questPlusPointsID++);
		}
		
		if(updateMyProfile)
		{
			ProfileManager.myProfile.addCoins(coinsToAdd);
			ProfileManager.myProfile.updateProfileInfo();
		}
		// update counter attempts
		attempts++;
		
	}
	
	public int getAttempts()
	{
		return attempts;
	}
	
	public void setAttempts(int att)
	{
		attempts = att;
	}
	
	public int getMark()
	{
		return mark;
	}
	
	public String getName()
	{
		return quizName;
	}
	
	public void setName(String name)
	{
		quizName = name;
	}
	
	public ArrayList<Question> getQuestions()
	{
		return questions;
	}
	
	public void setQuestions(ArrayList<Question> _questions)
	{
		questions = _questions;
	}
	
	public void updateExpPoints(int exp)
	{
		expPoints = exp;
	}
	
	public int getExpPoints()
	{
		return expPoints;
	}
	
}
