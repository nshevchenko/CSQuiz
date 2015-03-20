package com.example.oopquiz;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.os.Bundle;

import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.ProfileManager;
import com.example.oopquiz.Managers.SaveLoadClear;


public class SplashScreen extends MyActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		ColorManager.init(this.getApplicationContext());
		try {
			InputStream is = getAssets().open("data_questions.xml");
			AppManager.categories = AppManager.parse(is);
		} catch (IOException e) {
			System.out.println("error in reading xml");
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			System.out.println("error in reading xml");
			e.printStackTrace();
		}
		ProfileManager.init();
		SaveLoadClear.loadData(this);
		ProfileManager.myProfile.updateProfileInfo();
		
		//update friends facebook list just once as soon as loggedin 
		getPreferences(MODE_PRIVATE).edit().putInt("updateFriendList",1).commit(); 
		System.out.println("saved this : "  + getPreferences(MODE_PRIVATE).getInt("updateFriendList", 0));
		
		callMenu();
	}
	
	public void callMenu()
	{
		Intent intent = new Intent(SplashScreen.this , MainMenuScreen.class);
		startActivity(intent);
		this.finish();
	}
}
