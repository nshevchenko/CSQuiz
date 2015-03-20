package com.example.oopquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.Question;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.SavedQuestionsAdapter;

public class SavedQuestionsScreen extends MyActivity {

	public int selectedRow;
	public boolean animating;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//activity init
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_questions_screen);
		selectedRow = -1;
		//activity settings
		backActivityName = MainMenuScreen.class;
		AppManager.savedQuestions = this;
		ButtonManager.initNewScreen(this, activityName.savedQuestion);
		
		//init adapter list view of saved questions
		final SavedQuestionsAdapter adapter = new SavedQuestionsAdapter(this, R.layout.item_saved_question);
		final ListView listView = (ListView)findViewById(R.id.listItems);
		listView.setAdapter(adapter);		
		
		//update font of the title
		TextView title = (TextView)findViewById(R.id.title);
		ColorManager.updateFont(title);
		
		// show tutorial if no questions are saved and animation listener
		showTutorial(AppManager.questionsSaved.size());		
		addAnimationToList(listView, adapter);
		findObjects();
	}
	
	public void findObjects()
	{
		ButtonUI buttonTemp = ButtonManager.createButton(R.id.back);
		buttonTemp = ButtonManager.createButton(R.id.title);
		ColorManager.changeButtonColor(buttonTemp);
	}
	
	public void addAnimationToList(final ListView listView, final SavedQuestionsAdapter adapter)
	{
		final Animation animationItem = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
		
		animationItem.setAnimationListener(new AnimationListener() {
	        @Override	
	        public void onAnimationStart(Animation animation) {}
		    @Override
		    public void onAnimationRepeat(Animation animation) {}

		    @Override
		    public void onAnimationEnd(Animation animation) {
		    	View v = listView.getChildAt(selectedRow);
		    	v.setVisibility(View.INVISIBLE);
		    	Animation tempAnimListener = null;
		        for(int i = selectedRow; i < listView.getCount(); i++)
		        	if(tempAnimListener == null)
		        		tempAnimListener = adapter.slideItemUp(listView, i, adapter, v.getHeight() + listView.getDividerHeight());
		        	else
		        		adapter.slideItemUp(listView, i, adapter, v.getHeight() + listView.getDividerHeight());
		        
		        if(tempAnimListener != null)
		        {
		        	//apply this code only once.. to any animation.
		        	tempAnimListener.setAnimationListener(new AnimationListener(){
		        		@Override	
		        		public void onAnimationStart(Animation animation){}
		        		@Override
		        		public void onAnimationRepeat(Animation animation){}
		        		@Override
		        		public void onAnimationEnd(Animation animation) {
		        			Question quest = AppManager.questionsSaved.get(selectedRow);
		        			quest.setSavedQuest(0, false);
		        			AppManager.savedQuestions.animating = false;
		        			adapter.remove(quest);
		        			adapter.notifyDataSetChanged();
		        		}		
		        	});
		        }
		     }
		 });	
        
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) 
			{
				if(animating) return;
			    	animating = true;
			    	selectedRow = position;
			    	v.startAnimation(animationItem);
			}
		});
	}
		
	
	public void showTutorial(int questSavedSize)
	{
		TextView text1 = (TextView) findViewById(R.id.tutorialText1);
		TextView text2 = (TextView) findViewById(R.id.tutorialText2);
		ImageView textImg = (ImageView) findViewById(R.id.tutorialImg1);
		ColorManager.updateFont(text1);
		ColorManager.updateFont(text2);
		LinearLayout layoutQuestion = (LinearLayout) findViewById(R.id.exampleQuestionSaved);
		
		if(questSavedSize >= 1)
		{
			layoutQuestion.setVisibility(View.GONE);
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			textImg.setVisibility(View.GONE);
		}
		else
		{
			ButtonUI but = new ButtonUI((Button)findViewById(R.id.fb), activityName.savedQuestion, false);
			ColorManager.changeButtonColor(but);
			but = new ButtonUI((Button)findViewById(R.id.wiki), activityName.savedQuestion, false);
			ColorManager.changeButtonColor(but);
		}
	}
	
	
	public void clickOn(ButtonUI button)
	{
		int id = button.getButton().getId();
		switch(id)
		{
			case R.id.back:
				Intent intent = new Intent(this, MainMenuScreen.class);
				startActivity(intent);
				finishScreen();
				break;
			case R.id.wikiItem : // wiki button on every item
				int itemPressed = button.getId();
				Question quest = AppManager.questionsSaved.get(itemPressed);
				String url = quest.getLink();
				if (!url.startsWith("http://") && !url.startsWith("https://"))
					   url = "http://" + url;
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browserIntent);
				break;
			case R.id.fbItem : // share button on every item
				System.out.println("asdf");
				DialogFragment dialog = new SharePopup("Titke","Description I guess");
			    dialog.show(getSupportFragmentManager(), "tag");
				break;
			default : 
				break;
		}
	}
	@Override
	public void onStop()
	{
		super.onStop();
	}
	
	public void finishScreen()
	{
		ButtonManager.cleanScreenButtons(activityName.savedQuestion);
		this.finish();
	}
}