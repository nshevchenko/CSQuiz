package com.example.oopquiz.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.example.oopquiz.R;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.SaveLoadClear;

public class MyActivity extends FragmentActivity{
	
	public Class backActivityName; // previous (back activity name)
	
	
	@Override
	protected void onStop()
	{
		System.out.println("onStop " + this.toString());
		SaveLoadClear.saveData(this);
		super.onStop();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	if( !(backActivityName == null))
	    	{
	    		Intent intent = new Intent(this, backActivityName);
	    		startActivity(intent);
	    	}
	    	else
	    		SaveLoadClear.saveData(this);
	    	finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
