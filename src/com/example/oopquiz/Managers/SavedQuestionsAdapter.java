package com.example.oopquiz.Managers;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oopquiz.R;
import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.activityName;

public class SavedQuestionsAdapter extends ArrayAdapter<Question>{

	// for the getView method
    private int resource; 
    private LayoutInflater inflater;
    // current app context
    private Context context;
    
    // method variables
    private ArrayList<Integer> prevFbButtonColors, prevWikiButtonColors; // keep the same colors for buttons which won't be refreshed
    
    public SavedQuestionsAdapter ( Context ctx, int resourceId ) 
    {	 
        super( ctx, resourceId, AppManager.questionsSaved);
        this.resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
        prevFbButtonColors = new ArrayList<Integer>();
        prevWikiButtonColors =  new ArrayList<Integer>();
    }


    public Animation slideItemUp(ListView listView, final int position, final SavedQuestionsAdapter adapter, int height )
    {
    	View v = listView.getChildAt(position);
    	if(v == null)
    		return null;
    	
    	final int selectedRow = AppManager.savedQuestions.selectedRow;
    	if(position <= selectedRow)
    		return null;
    	
    	final TranslateAnimation trans = new TranslateAnimation(0, 0, 
    			TranslateAnimation.ABSOLUTE, 0, 0, 0, 
    			TranslateAnimation.ABSOLUTE,  -height);    	
    	trans.setDuration(500);
    	v.startAnimation(trans);
    	
    	return trans;
    }
    
	@SuppressLint("ViewHolder")
	@Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = ( LinearLayout ) inflater.inflate( resource, null );
    	Question quest = AppManager.questionsSaved.get(position);
       
    	LinearLayout buttonsLinLay = (LinearLayout)convertView.findViewById(R.id.buttonsLinLay);
    	RelativeLayout wikiBtnLay = (RelativeLayout)buttonsLinLay.findViewById(R.id.wikilayout);
    	RelativeLayout fbBtnLay = (RelativeLayout)buttonsLinLay.findViewById(R.id.fbRelLay);
    	
    	TextView txtTemp = (TextView) convertView.findViewById(R.id.question);
    	ColorManager.updateFont(txtTemp);
        txtTemp.setText(quest.getQuestion());
        txtTemp = (TextView) convertView.findViewById(R.id.path);
        ColorManager.updateFont(txtTemp);
        txtTemp.setText(quest.getPath());
        
    	ButtonUI fbBtn = ButtonManager.createButton((Button)fbBtnLay.findViewById(R.id.fbItem), activityName.savedQuestion, position);
    	ButtonUI wikiBtn = ButtonManager.createButton((Button)wikiBtnLay.findViewById(R.id.wikiItem), activityName.savedQuestion, position);
       
    	if( position >= prevFbButtonColors.size()) 
        {
        	int color = ColorManager.changeButtonColor(fbBtn);
        	prevFbButtonColors.add(color);
            color = ColorManager.changeButtonColor(wikiBtn);
            prevWikiButtonColors.add(color);
        }
        else
        {
        	if(position >= AppManager.savedQuestions.selectedRow)
        	{
        		if(position == AppManager.savedQuestions.selectedRow)
        		{
        			Integer temp = prevFbButtonColors.get(position); 
        			prevFbButtonColors.remove(temp);
        			temp = prevWikiButtonColors.get(position); 
        			prevWikiButtonColors.remove(temp);
        		}
        	}
        	fbBtn.getButton().getBackground().setColorFilter(prevFbButtonColors.get(position), Mode.MULTIPLY);
        	wikiBtn.getButton().getBackground().setColorFilter(prevWikiButtonColors.get(position), Mode.MULTIPLY);
        }     
        return convertView;
    }
}
