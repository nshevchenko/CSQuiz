package com.example.oopquiz.Elements;

import java.util.ArrayList;



import android.widget.Button;

public class Category {
	
	private String categoryName;
	private ArrayList<Quiz> quizzes;
	private int expPoints, progress, sumExpPoints;
	
	
	public Category(String name)
	{
		categoryName = name;
		quizzes = new ArrayList<Quiz>();
		progress = 2;
		updateProgress(0);
	}
	
	public Quiz findQuiz(String quizName)
	{
		for(Quiz quiz : quizzes)
		{
			if(quiz.getName().equals(quizName))
				return quiz;
		}
		return null;
	}
	public int getProgress()
	{
		return progress;
	}
	
	public void updateProgress(int updateProgress)
	{
		updateProgress = 5 + (updateProgress * 95/100);
		progress = updateProgress;
	}
	
	public void setExpPoints(int exp)
	{
		expPoints = exp;
	}
	
	public String getName()
	{
		return categoryName;
	}
	
	public ArrayList<Quiz> getQuizzes()
	{
		return quizzes;
	}
	
	public int getSumExpPoints()
	{
		return sumExpPoints;
	}
	
	public int makeSumExpPoints()
	{
		int overall = 0;
		for(int i = 0; i < quizzes.size(); i++)
			overall += quizzes.get(i).getExpPoints();
		sumExpPoints = overall;
		return overall;
	}
}
