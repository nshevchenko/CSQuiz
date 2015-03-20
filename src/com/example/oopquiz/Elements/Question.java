package com.example.oopquiz.Elements;

import com.example.oopquiz.Managers.AppManager;


public class Question{
	
	private int plusExpPointsID; // must be 0 (max=10) - 2 (min=1) to add to user info (exp/coins)
	private int id;
	private String question;
	private String link;
	private String[] answers;
	private String path; // example path = "OOP > Basic Theory";
	private int correctAnswer, userAnswer;
	
	public boolean answeredCorrect, savedQuestion;
	
	public Question()
	{
		plusExpPointsID = 0;
	}
	
	public Question(String question) {
		plusExpPointsID = 0;
		this.question = question;
	}
	
	public Question(String question, String link, String[] answers, int corrAns, String path) {
		plusExpPointsID = 0;
		this.link = link;
		this.question = question;
		this.answers = answers;
		this.correctAnswer = corrAns;
		this.path = path;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public String[] getAnswers()
	{
		return answers;
	}
	
	public int getCorrectAnswer()
	{
		return correctAnswer;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public int getUserAnswer()
	{
		return userAnswer;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public void updatePlusExpPoints(int plusPoints)
	{
		plusExpPointsID = plusPoints;
	}
	
	public int getPlusPointsID()
	{
		return plusExpPointsID;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int _id)
	{
		id = _id;
	}
	
	public void setSavedQuest(int result, boolean removeFromArray)
	{
		if(result == 1)
		{
			savedQuestion = true;
			AppManager.questionsSaved.add(this);
		}
		else
		{
			savedQuestion = false;
			if(removeFromArray)
				AppManager.questionsSaved.remove(this);
		}
	}
	
	public int getSavedInfo()
	{
		if(savedQuestion)
			return 1;
		return 0;
					
	}
	
	public void setUserAnswer(int userAnswer)
	{
		this.userAnswer = userAnswer;
	}
}