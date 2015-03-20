package com.example.oopquiz.Managers;

import java.util.ArrayList;
import java.util.Random;

import com.example.oopquiz.FinishQuizScreen;
import com.example.oopquiz.QuestionScreen;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.Quiz;


import android.content.Context;
import android.content.Intent;

public class QuizManager {
	
	private static int currentQuestionID;
	private static Quiz currentQuiz;
	private static ArrayList<Question> tempForRandom;
	
	public static int getCurrentQuestion()
	{
		return currentQuestionID;
	}
	
	public static void nextQuestion(Context context)
	{
		currentQuestionID++;
		if(currentQuestionID < currentQuiz.getQuestions().size())
			startScreen(context, QuestionScreen.class, true);
		else
			finishCurrentQuiz(context);
	}
	
	public static void startNewQuiz(Context context, Quiz curQuiz)
	{
		currentQuiz = curQuiz;
		currentQuestionID = 0;
		tempForRandom = new ArrayList<Question>(currentQuiz.getQuestions());
		startScreen(context, QuestionScreen.class, true);		
	}
	
	public static void startScreen(Context context, Class _class)
	{
		Intent intent = new Intent(context, _class);
		context.startActivity(intent);
	}
	
	public static void startScreen(Context context, Class _class, boolean extra)
	{
		Intent intent = new Intent(context, _class);
		if(extra)
			intent.putExtra("questId", currentQuestionID);
		context.startActivity(intent);
	}
	
	public static Question getQuestion(int questId)
	{
		return currentQuiz.getQuestions().get(questId);
	}
		
	public static Question pickRandomQuestion()
	{
		Random random = new Random();
		int randomN = 0;
		if(tempForRandom.size() > 1)
			randomN = random.nextInt(tempForRandom.size()-1); 		
		Question questToRemove = tempForRandom.get(randomN);
		
		for(Question question : QuizManager.getCurrentQuiz().getQuestions())
		{
			if(questToRemove.getId() == question.getId())
			{
				tempForRandom.remove(questToRemove);
				return question;
			}
		}
		return questToRemove;
	}
	
	public static Quiz getCurrentQuiz()
	{
		return currentQuiz;
	}
	
	public static void answerQuestion(boolean correct)
	{
		getQuestion(currentQuestionID).answeredCorrect = correct;
	}
	
	public static void finishCurrentQuiz(Context context)
	{
		Intent intent = new Intent(context, FinishQuizScreen.class);
		context.startActivity(intent);
	}
}

