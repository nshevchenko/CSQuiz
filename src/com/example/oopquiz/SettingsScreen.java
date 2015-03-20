package com.example.oopquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import backend.ServerConnection;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;


public class SettingsScreen extends MyActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_screen);
		AppManager.settingsScreen = this;
		backActivityName = MainMenuScreen.class;
		ButtonManager.initNewScreen(this, activityName.settings);
		findObjects();
	}
	ButtonUI temp;
	public void findObjects()
	{
		temp = new ButtonUI( (Button) findViewById(R.id.clear), activityName.settings);
		ColorManager.changeButtonColor(temp);
	}
	
	public void clickOn(ButtonUI button)
	{
		int id = button.getButton().getId();
		switch(id)
		{
			case R.id.clear :
				ServerConnection task = new ServerConnection();
				task.execute("Nikolai");
				//SaveLoadClear.clearData(this);
			//testHttp();
			break;
		}
	}


	public void finishScreen()
	{
		ButtonManager.cleanScreenButtons(activityName.settings);
		this.finish();
	}
}
		