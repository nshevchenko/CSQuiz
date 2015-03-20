package com.example.oopquiz.Managers;

import java.util.ArrayList;

import com.example.oopquiz.R;
import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.Quiz;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.R.id;
import com.example.oopquiz.R.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class QuizGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Quiz> items;
    LayoutInflater inflater;
     
    
    public QuizGridAdapter(Context context, ArrayList<Quiz> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
        	convertView = inflater.inflate(R.layout.icon_quiz_menu, null);
        	
        }
        ButtonUI button = ButtonManager.createButton(
        		(Button) convertView.findViewById(R.id.grid_item_button), 
        		activityName.quizSelect
        	);
        
        TextView text = (TextView) convertView.findViewById(R.id.grade); 
        text.setTextColor(Color.parseColor("#3b3b3b"));
        text.setText(Integer.toString(items.get(position).getMark()) + "/" + Integer.toString(items.get(position).getQuestions().size()));
        
        text = (TextView) convertView.findViewById(R.id.expPoints); 
        text.setText(Integer.toString(items.get(position).getExpPoints()));
        
        
        String title = items.get(position).getName();
        button.getButton().setText(title);
        
        int width = parent.getWidth();
        if (width > 0) 
        {
            LayoutParams layoutParams = button.getButton().getLayoutParams();
            layoutParams.height = (int) (width / 3);
        }
        
        ColorManager.changeButtonColor(button);
        
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_anim); 
        animation.setDuration(500);
        convertView.startAnimation(animation);
        animation.setStartOffset(position * 50);
        
        
        return convertView;
    }
    
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}