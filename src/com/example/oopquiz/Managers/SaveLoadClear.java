package com.example.oopquiz.Managers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

import com.example.oopquiz.Elements.Category;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.Quiz;

public class SaveLoadClear {


	public static void saveData(Context ctx)
	{
		String fileName = "openlibrary";
		String string = "";
		
		String topcat = "";
		for(int i = 0; i < ProfileManager.myProfile.top3.length; i++)
			topcat += ProfileManager.myProfile.top3[i] + ",";
		
		string += ProfileManager.myProfile.getCoins() + "\n"
				+ topcat + "\n";
		
		for(Category category : AppManager.categories)
		{
			string += category.getName() + "\n" +
					  category.getProgress() + "\n" + 
					  category.makeSumExpPoints() + "\n";
			for(int i = 0; i < category.getQuizzes().size(); i++)
			{
				Quiz quiz = category.getQuizzes().get(i);
				string += quiz.getName() + "\n" +
						  quiz.getMark() + "\n" + 
						  quiz.getExpPoints() + "\n" +
						  quiz.getAttempts() + "\n";
				
				for(Question quest : quiz.getQuestions())
				{
					string += quest.getId() + "\n" +
							  quest.getPlusPointsID() + "\n" + // id defines how many points/money to add in case question is correct
							  quest.getSavedInfo() + "\n";
					
				}
				string += "\n";
			}
			string += "\n";
			
		}
		try {
			FileOutputStream fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			System.out.println("SAVING");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException io){
			io.printStackTrace();
		}
		
	}
	
	public static void loadData(Context ctx)
	{
		String fileName = "openlibrary";
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(ctx.openFileInput(fileName)));
		    String inputString;
    		
		    inputString = inputReader.readLine();
		    ProfileManager.myProfile.setCoins(Integer.parseInt(inputString));
		    
		     
		    inputString = inputReader.readLine();
		    String[] topcat = inputString.split(",");
		    ProfileManager.myProfile.top3 = topcat;
		    
		    inputString = inputReader.readLine();
	        Category currentCategory = AppManager.findCategory(inputString);
		        
	        for(Category category : AppManager.categories)
			{
	        	if(category.getName().equals(currentCategory.getName()))
	        	{
	        		inputString = inputReader.readLine();
					category.updateProgress(Integer.parseInt(inputString));
	 		        inputString = inputReader.readLine();
	 		        category.setExpPoints(Integer.parseInt(inputString));
	 		        
	 		        for(int i = 0; i < category.getQuizzes().size(); i++)
	 		        {
	 		        	inputString = inputReader.readLine();
	 		        	for(Quiz quiz : category.getQuizzes())
		 		        {
		 		        	
	 		        		if(quiz.getName().equals(inputString))
	 		        		{
	 		        			inputString = inputReader.readLine();
	 		        			quiz.setMark(Integer.parseInt(inputString), false);
	 		        			inputString = inputReader.readLine();
	 		        			quiz.updateExpPoints(Integer.parseInt(inputString));
	 		        			inputString = inputReader.readLine();
	 		        			quiz.setAttempts(Integer.parseInt(inputString));
	 		        			for(int j = 0; j < quiz.getQuestions().size() ; j++)
	 		        			{
	 		        				inputString = inputReader.readLine();
	 		        				Question question = quiz.findQuestionById(Integer.parseInt(inputString));
	 		        				inputString = inputReader.readLine();
	 		        				question.updatePlusExpPoints(Integer.parseInt(inputString));	
	 		        				inputString = inputReader.readLine();
	 		        				int savedInt = Integer.parseInt(inputString);
	 		        				question.setSavedQuest(savedInt, true);
	 		        			}
	 		        		}	
		 		        }
	 		        }
	        	}
			}
		    System.out.println("READING");
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (NumberFormatException ex )
		{
			System.out.println("abort reading file .. empty shit");
		}
	}
	
	public static void clearData(Context ctx)
	{
		String fileName = "openlibrary";
		String string = "";
		
		for(Category category : AppManager.categories)
		{
			string += category.getName() + "\n" +
					  5 + "\n" + 
					  0 + "\n";
			for(int i = 0; i < category.getQuizzes().size(); i++)
			{
				Quiz quiz = category.getQuizzes().get(i);
				string += quiz.getName() + "\n" +
						  0 + "\n" + 
						  0 + "\n";
				
				for(Question quest : quiz.getQuestions())
				{
					string += quest.getId() + "\n" +
							  10 + "\n" +
							  0 + "\n";
					
				}
				string += "\n";
			}
			string += "\n";
			
		}
		try {
			FileOutputStream fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			System.out.println("CLEARING");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException io){
			io.printStackTrace();
		}
	}
	
}
