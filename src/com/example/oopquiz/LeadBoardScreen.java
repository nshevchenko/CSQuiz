package com.example.oopquiz;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.MyActivity;
import com.example.oopquiz.Elements.activityName;
import com.example.oopquiz.Managers.AppManager;
import com.example.oopquiz.Managers.ButtonManager;
import com.example.oopquiz.Managers.ColorManager;
import com.example.oopquiz.Managers.ProfileManager;
import com.example.oopquiz.Managers.ProfileViewAdapter;
import com.example.oopquiz.Managers.SocialManager;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;

public class LeadBoardScreen extends MyActivity {
	
    private ListView listView;
	private ProfileViewAdapter adapter;
	private TextView shareYourScoreText; 
	
	private LinearLayout shareFbFriendsLayout, viewFriendsLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		backActivityName = MainMenuScreen.class;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leadboards);
		AppManager.leadBoardScreen = this;
		ButtonManager.initNewScreen(this, activityName.leadboards);
		findObjects();
		
		facebookInit(savedInstanceState);
	}
	
	public void findObjects()
	{
		ButtonUI temp = ButtonManager.createButton(R.id.back);
		temp = ButtonManager.createButton(R.id.title);
		temp.getButton().setText("LeaderBoards");
		ColorManager.changeButtonColor(temp);	
		
		shareYourScoreText = (TextView) findViewById(R.id.shareScoreRequest);
		ColorManager.updateFont(shareYourScoreText);
		
		listView = (ListView)findViewById(R.id.listProfiles);
		//listView.setVisibility(View.VISIBLE);
		adapter = new ProfileViewAdapter(this, R.layout.item_profile);
		listView.setAdapter(adapter);

		//currentFbState.setVisibility(View.GONE);
	}

	
	public void finishScreen()
	{
		ButtonManager.cleanScreenButtons(activityName.leadboards);
		finish();
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
	
	
	/*
	  			_:_:_:_:_:_:_:_: FACEBOOK BACKEND _:_:_:_::
	 */
	
    private UiLifecycleHelper uiHelper;
    
    
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	private void facebookInit(Bundle savedInstanceState)
	{
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("public_profile", "user_friends"));
		
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	}

	
	@SuppressWarnings("deprecation")
	private void onSessionStateChange(Session session, SessionState state, Exception exception) 
	{
		if (state.isOpened()) 
	    {
			SharedPreferences editor = getPreferences(MODE_PRIVATE);
			if(editor.getInt("updateFriendList", 1) == 1)
			{
				updateFriendsList(session);
				getPreferences(MODE_PRIVATE).edit().putInt("updateFriendList",0).commit();
			}
			else
				updateViewLoggedIn(false);
			
			if(ProfileManager.myProfile.getName().equals(""))
				updateInfoMyProfiles(session);
	    } 
		else if (state.isClosed()) 
	    {
			getPreferences(MODE_PRIVATE).edit().putInt("updateFriendList",1).commit();
			updateViewLoggedIn(false);
			//currentFbState.setText(R.string.fb_connect_intro);
        }
	}
	
	public void updateViewLoggedIn(boolean loggedin)
	{
		
		/*shareLayout = (LinearLayout) findViewById(R.id.shareScoreRequest);
		shareLayout = (LinearLayout) findViewById(R.id.shareScoreRequest);
		
		if(loggedin)
		{
			tempShareLayout.setVisibility(View.)
			tempShareLayout.setVisibility(View.VISIBLE);
		}
		else
		{
			currentFbState.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}
		*/
	}
	
	
	@SuppressWarnings("deprecation")
	public void updateFriendsList(Session session)
	{
		//currentFbState.setText(R.string.fetching_friends);
		Request.executeMyFriendsRequestAsync(session, new Request.GraphUserListCallback() 
    	{
    		@Override
    		public void onCompleted(List<GraphUser> users, Response response) 
    		{
    				if(users == null) System.out.println("users null");
    				SocialManager.fetchFriends(users);
    				System.out.println("on completed friends");
    				
    				adapter.notifyDataSetChanged();
    				updateViewLoggedIn(true);
    		}	
    	});
	}
	
	@SuppressWarnings("deprecation")
	public void updateInfoMyProfiles(Session session)
	{
		//currentFbState.setText(R.string.login);
		if(ProfileManager.myProfile.getName().equals(""))
		{			
			Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
        	
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {
						System.out.println("on completed user");
						SocialManager.updateMyProfileInfo(user);
					}
					else
						System.out.println("user null");
				}
			});
		}
	}

	
	@Override
	public void onResume() {
	    super.onResume();
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
}
