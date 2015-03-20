package com.example.oopquiz.Managers;

import java.util.ArrayList;

import com.example.oopquiz.R;
import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.Category;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.R.id;
import com.example.oopquiz.R.layout;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CategoryGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> items;
    LayoutInflater inflater;
     
    
    public CategoryGridAdapter(Context context, ArrayList<Category> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
        	convertView = inflater.inflate(R.layout.icon_category_menu, null);
        	
        }
        ButtonUI button = ButtonManager.createButton(
        		(Button) convertView.findViewById(R.id.grid_item_button), 
        		activityName.levelSelect
        	);
        
        LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.linearLayout);
        
        TextView text = (TextView)layout.findViewById(R.id.expPoints);
        text.setText(Integer.toString((items.get(position).makeSumExpPoints())));
        
        ProgressBar progress = (ProgressBar)convertView.findViewById(R.id.progress);
        progress.setProgress(items.get(position).getProgress());
        
        int width = parent.getWidth();
        if (width > 0) 
        {
            LayoutParams layoutParams = button.getButton().getLayoutParams();
            layoutParams.height = (int) (width / 3);
        } 
        
        String title = items.get(position).getName();
        button.getButton().setText(title);
        
        
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