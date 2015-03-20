package com.example.oopquiz.Managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oopquiz.Elements.ButtonUI;

public class ColorManager {
	
	public static int[] colorList = {
		Color.parseColor("#FF7D7D"), 
		Color.parseColor("#ff7dc6"), 
		Color.parseColor("#ee7dff"), 
		Color.parseColor("#ae7dff"),
		Color.parseColor("#7db4ff"),
		Color.parseColor("#7dfdff"),
		Color.parseColor("#7dffc6"),
		Color.parseColor("#83ff7d"),
		Color.parseColor("#d9ff7d"),
		Color.parseColor("#ffdc7d"),
	};
	
	public static ArrayList<Integer> colors = new ArrayList<Integer>();
	public static ArrayList<Integer> copyColors = new ArrayList<Integer>();
	private static Typeface font;
	
	public static int getOnFocusColor(int c, float delta)
	{
		int r = Color.red(c);
	    int b = Color.blue(c);
	    int g = Color.green(c);
	    return Color.rgb(
	    		(int)Math.min(255, r + 255 * delta), 
	    		(int)Math.min(255, g + 255 * delta),
	    		(int)Math.min(255, b + 255 * delta)
	    	);
	}	
	
	public static void init(Context ctx)
	{
		for(int i = 0; i < colorList.length; i++)
		{	
			colorList[i] = getOnFocusColor(colorList[i], 0.1F);
			colors.add(colorList[i]);
			
		}
		copyColors = new ArrayList<Integer>(colors);
		Collections.shuffle(copyColors, new Random(System.nanoTime()));
		font = Typeface.createFromAsset(ctx.getAssets(), "eurostilebold.ttf");
	}
	
	public static void changeViewColor(TextView view)
	{
		view.getBackground().setColorFilter(ColorManager.generateColor(), Mode.MULTIPLY); 
		view.setTypeface(font);
	}
	
	public static void updateFont(TextView view)
	{
		view.setTypeface(font);
	}
	
	public static void updateFont(Button view)
	{
		view.setTypeface(font);
	}
	
	public static void changeLayoutColor(ViewGroup layout)
	{
		if(layout instanceof LinearLayout)
			layout.getBackground().setColorFilter(ColorManager.generateColor(), Mode.MULTIPLY); 
		else if(layout instanceof RelativeLayout)
			layout.getBackground().setColorFilter(ColorManager.generateColor(), Mode.MULTIPLY);
	}
	
	public static int changeButtonColor(ButtonUI button)
	{
		int color = ColorManager.generateColor();
		button.changeColor(color);
		button.getButton().setTypeface(font);
		return color;
	}
	
	public static int generateColor()
	{
		if(copyColors.size() == 0)
		{
			copyColors = new ArrayList<Integer>(colors);
			Collections.shuffle(copyColors, new Random(System.nanoTime()));
		}
		int randomN = 0;
		Random rand = new Random();
		if(copyColors.size() > 1)
			randomN = rand.nextInt(copyColors.size()-1);
		Integer randomColor = copyColors.get(randomN);
		copyColors.remove((Integer)randomColor);
		
		return randomColor;
	}
	
}
