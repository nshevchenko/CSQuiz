package com.example.oopquiz.Managers;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oopquiz.R;
import com.example.oopquiz.Elements.Question;

public class ListQuestionsViewAdapter extends ArrayAdapter<Question>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    
    public ListQuestionsViewAdapter ( Context ctx, int resourceId ) 
    {
        super( ctx, resourceId, QuizManager.getCurrentQuiz().getQuestions());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = ( LinearLayout ) inflater.inflate( resource, null );
        Question quest = QuizManager.getQuestion( position );
        
        TextView txtName = (TextView) convertView.findViewById(R.id.question);
        txtName.setText(quest.getQuestion());
        
        TextView txtNumber = (TextView)convertView.findViewById(R.id.numberText);
        txtNumber.setText( quest.getId() + "");
        
        TextView txtAnswer = (TextView) convertView.findViewById(R.id.userAnswer);
        if(quest.getUserAnswer() == -1)
        	txtAnswer.setText("Skipped");
        else
        	txtAnswer.setText("Answered: " + quest.getAnswers()[quest.getUserAnswer()]);
        
        ColorManager.updateFont(txtAnswer);
        ColorManager.updateFont(txtName);
        ColorManager.updateFont(txtNumber);
        
        TextView correctAnswer = (TextView) convertView.findViewById(R.id.correctAnswer);
        correctAnswer.setText("Correct answer: " + quest.getAnswers()[quest.getCorrectAnswer()]);
        
        Button viewResult = (Button)convertView.findViewById(R.id.viewResult);
        System.out.println("quest.getPlusPoints() = " + quest.getPlusPointsID());
        
        final int darkGreen  = Color.parseColor("#00540F");
        final int darkRed 	 = Color.parseColor("#B30000");
        final int lightGreen = Color.parseColor("#E8FFEB");
        final int ligthRed 	 = Color.parseColor("#FFEDED");
        
        int newColor = 0;
        int bgColor = 0;
        if(quest.answeredCorrect)
        {
        	viewResult.setText("+" + AppManager.QUESTION_POINTS[quest.getPlusPointsID()]);
        	correctAnswer.setVisibility(View.GONE);
        	newColor = darkGreen;
        	bgColor = lightGreen;
        }
        else
        {
        	correctAnswer.setVisibility(View.VISIBLE);
        	bgColor = ligthRed;
        	correctAnswer.setTextColor(darkGreen); //dark green
        	viewResult.setText("0");
        	newColor = darkRed;
        }        
        convertView.getBackground().setColorFilter(bgColor,Mode.MULTIPLY);
        viewResult.getBackground().setColorFilter(newColor, Mode.MULTIPLY);
        
        convertView.invalidate();
        return convertView;
    }
}
