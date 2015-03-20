package com.example.oopquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.InfoContainer;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.InfoProfileScreenAdapter;
import com.example.oopquiz.Managers.ProfileManager;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;


public class ProfileScreen extends MyActivity {
	
	//private MainFragment mainFragment;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.profile_screen);
			
			//LoginButton authButton = (LoginButton)findViewById(R.id.authButton);
			
			AppManager.profileScreen = this;
			backActivityName = MainMenuScreen.class;
			ButtonManager.initNewScreen(this, activityName.profileScreen);
			InfoContainer.init();
			ListView listView = (ListView)findViewById(R.id.listItems);
			listView.setAdapter( new InfoProfileScreenAdapter(this, R.layout.item_dataprofile));
			
			findElements();
		}
		
		public void findElements()
		{
			ButtonManager.createButton(R.id.back);
			ButtonUI temp = ButtonManager.createButton(R.id.title);
			ColorManager.changeButtonColor(temp);
			int[] res = {R.id.top1, R.id.top2, R.id.top3};
	        
	        for(int i = 0; i < res.length; i++)
	        {
	        	temp = new ButtonUI((Button)findViewById(res[i]), activityName.profileScreen, false);
	        	ColorManager.changeButtonColor(temp);
	        	temp.getButton().setText(ProfileManager.myProfile.top3[i]);
	        }
	        
	        TextView text = (TextView) findViewById(R.id.surname);
	        String[] names = ProfileManager.myProfile.getName().split(" ");
	        if(names.length == 2)
	        {
	        	text.setText(names[0]);
	        	ColorManager.updateFont(text);
	        	text = (TextView) findViewById(R.id.name);
	        	text.setText(names[1]);
	        	ColorManager.updateFont(text);
	        }
	        
	        ProfilePictureView image = (ProfilePictureView)findViewById(R.id.avatar);
	        image.setProfileId(ProfileManager.myProfile.getId());
	        /*Drawable avaDraw = image.getDrawable();
			Bitmap bitmap = ((BitmapDrawable)avaDraw).getBitmap();
			final int pixelsRoundCorners = 120; //chosen by eye 
			bitmap = ProfileManager.getRoundedCornerBitmap(bitmap, pixelsRoundCorners);
			avaDraw = new BitmapDrawable(getResources(), bitmap);
			image.setImageDrawable(avaDraw);
			*/
		}
		
		public void clickOn(ButtonUI button)
		{
			int id = button.getButton().getId();
			switch(id)
			{
				case R.id.back :
					Intent intent = new Intent(this, MainMenuScreen.class);
					startActivity(intent);
					finishScreen();
					break;
			}
		}
		
		public void finishScreen()
		{
			ButtonManager.cleanScreenButtons(activityName.profileScreen);
			this.finish();
		}
}
