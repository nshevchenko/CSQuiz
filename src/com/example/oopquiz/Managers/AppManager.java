package com.example.oopquiz.Managers;


//SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
//editor.putInt("showedTutorial", 1);
//editor.commit();
//
//getPreferences(MODE_PRIVATE).edit().putString("Name of variable",value).commit();


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.example.oopquiz.CategorySelectScreen;
import com.example.oopquiz.FinishQuizScreen;
import com.example.oopquiz.LeadBoardScreen;
import com.example.oopquiz.MainMenuScreen;
import com.example.oopquiz.ProfileScreen;
import com.example.oopquiz.QuestionScreen;
import com.example.oopquiz.QuizSelectScreen;
import com.example.oopquiz.SavedQuestionsScreen;
import com.example.oopquiz.SettingsScreen;
import com.example.oopquiz.Elements.Category;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.Quiz;

public class AppManager {
	
	public static MainMenuScreen mainScreen;
	public static CategorySelectScreen levelSelect;
	public static QuestionScreen questionScreen;
	public static QuizManager quizGameManager;
	public static FinishQuizScreen finishQuizScreen;
	public static QuizSelectScreen quizSelectScreen;
	public static ProfileScreen profileScreen;
	public static SettingsScreen settingsScreen;
	public static LeadBoardScreen leadBoardScreen;
	public static SavedQuestionsScreen savedQuestions;
	 
	public static int loadLeadBoardsFirstTime = 1; // 1- update friends and show. When it's 0 the update happens manually  
	
	/* -_-_-_-_-_-_-_-_-_-_-_-_  SAVE QUESTIONS SECTION -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ */
	
	public static ArrayList<Question> questionsSaved = new ArrayList<Question>();
	
	public static void saveQuestions(Question quest)
	{
		questionsSaved.add(quest);
	}
	
	public static void removeQuestion(Question quest)
	{
		questionsSaved.remove(quest);
	}
	
	
	
	/* -_-_-_-_-_-_-_-_-_-_-_-_  CONSTANT VARS SECTION -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ */
	public static final int[] QUESTION_POINTS = {10, 5, 1};
	public static final int[] QUESTION_COINS  = {3, 2, 1};
	public static int QUESTION_TIMER = 10; //seconds
	
	
	
	
	
	
	/* -_-_-_-_-_-_-_-_-_-_-_-_  CATEGORY SETTING SECTION -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ */
	
	public static ArrayList<Category> categories = new ArrayList<Category>();
	private static final String ns = null;
	
	
	public static Category findCategory(String name)
	{
		for(Category cat : categories)
			if(cat.getName().equals(name))
				return cat;
		return null;
	}
	
	/* -_-_-_-_-_-_-_-_-_-_-_-_  PARSING QUESTIONS XML -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ */
	
	
	public static ArrayList<Category> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
	
	private static ArrayList<Category> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
	    categories = new ArrayList<Category>();

	    parser.require(XmlPullParser.START_TAG, ns, "data");
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name.equals("category")) {
	        	categories.add(readCategory(parser));
	        } else {
	            skip(parser);
	        }
	    }  
	    return categories;
	}
	
	private static Category readCategory(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, "category");
		String catName = parser.getAttributeValue(null, "title");
		Category category = new Category(catName);
	    
		while (parser.next() != XmlPullParser.END_TAG) {
			 if (parser.getEventType() != XmlPullParser.START_TAG) {
		         continue;
		     }
		     String name = parser.getName();
		     // Starts by looking for the entry tag
		     if (name.equals("quiz")) {
		    	 category.getQuizzes().add(readQuiz(parser, catName));
		     } else {
		        skip(parser);
		     }
		}  
		return category;
	}
	     
	private static Quiz readQuiz(XmlPullParser parser, String categoryName) throws XmlPullParserException, IOException {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		parser.require(XmlPullParser.START_TAG, ns, "quiz");
		String nameQuiz = parser.getAttributeValue(null, "title");
		Quiz quiz = new Quiz(nameQuiz); 
		
		int index = 0;
		while (parser.next() != XmlPullParser.END_TAG) {
			 if (parser.getEventType() != XmlPullParser.START_TAG) {
		         continue;
		     }
		     String name = parser.getName();
		     // Starts by looking for the entry tag
		     if (name.equals("quest")) {
		    	 index++;
		    	 questions.add(readQuest(parser, index, nameQuiz));
		     } else {
		        skip(parser);
		     }
		}  
		quiz.setQuestions(questions);
		return quiz;
	}  
	
	private static Question readQuest(XmlPullParser parser, int id, String path) throws XmlPullParserException, IOException {

		parser.require(XmlPullParser.START_TAG, ns, "quest");
		String questionStr = "", link = "";
		String[] answers = {};
		int correctAnswer = -1;
		
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        
	        if (name.equals("question")) {
	        	questionStr = readQuestion(parser);
	        } else if (name.equals("answers")) {
	            answers = readAnswers(parser);
	        } else if (name.equals("correctAnswer")) {
	            correctAnswer = readCorrectAnswer(parser);
	        } else if (name.equals("link")) {
	            link = readLink(parser);
	        } else {
	           skip(parser);
	        }
	    }
	    Question question = new Question(questionStr, link, answers, correctAnswer, path); 
	    question.setId(id);
	    return question;
	}

	private static String readQuestion(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "question");
	    String title = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "question");
	    return title;
	}
	
	private static String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, ns, "link");
	    String link = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "link");
	    return link;
	}
	  
	// Processes link tags in the feed.
	private static String[] readAnswers(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String[] answers = new String[4];
	    parser.require(XmlPullParser.START_TAG, ns, "answers");
	    String answersList = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "answers");
	    answers = answersList.split(";");
	    return answers;
	}
	
	private static int readCorrectAnswer(XmlPullParser parser) throws IOException, XmlPullParserException {
		int correctAnswer = 0;
	    parser.require(XmlPullParser.START_TAG, ns, "correctAnswer");
	    String correctAnswerString = readText(parser);
	    parser.require(XmlPullParser.END_TAG, ns, "correctAnswer");
	    correctAnswer = Integer.parseInt(correctAnswerString);
	    return correctAnswer;
	}

	// For the tags title and summary, extracts their text values.
	private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	
	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
	
	
	
	
}
