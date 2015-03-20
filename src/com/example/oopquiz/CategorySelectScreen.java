package com.example.oopquiz;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.Category;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.CategoryGridAdapter;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.QuizManager;


public class CategorySelectScreen extends MyActivity {

	private GridView gridView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_level_screen);
		AppManager.levelSelect = this;
		ButtonManager.initNewScreen(this, activityName.levelSelect);
		findObjects();
		backActivityName = MainMenuScreen.class;
		CategoryGridAdapter gridAdapter = new CategoryGridAdapter(this, AppManager.categories);
        gridView.setAdapter(gridAdapter);
	}
	
	public void findObjects()
	{
		ButtonUI temp = ButtonManager.createButton(R.id.back);
		gridView = (GridView)findViewById(R.id.gridViewCategories);
		temp = new ButtonUI((Button)findViewById(R.id.title), activityName.levelSelect, false);
		temp.getButton().setText("Choose Category");
		ColorManager.changeButtonColor(temp);
	}
	
	public void clickOn(ButtonUI button)
	{
		System.out.println("clicked on " + button.getButton().getText());
		int id = button.getButton().getId();
		switch(id)
		{
			case R.id.back :
				QuizManager.startScreen(this, MainMenuScreen.class);
				break;
			default :
				for(Category category : AppManager.categories)
				{
					if(button.getButton().getText().equals(category.getName()))
					{
						Intent intent = new Intent(this, QuizSelectScreen.class);
						intent.putExtra("categoryName", category.getName());
						startActivity(intent);
					}
				}
				break;
		}
		finishScreen();
	}
	
	
	public void finishScreen()
	{
		ButtonManager.cleanScreenButtons(activityName.levelSelect);
		this.finish();
	}
}
