package com.example.oopquiz.Managers;

import java.util.List;

import com.example.oopquiz.Elements.Profile;
import com.facebook.model.GraphUser;

public class SocialManager {

	
	/* -_-_-_-_-_-_-_-_-_-_-_-_  FACEBOOK HELPER METHODS SECTION -_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ */
	public static void updateMyProfileInfo(GraphUser user) {
	    
	    ProfileManager.myProfile.setName(user.getName());
	    ProfileManager.myProfile.setId(user.getId());
	}
	
	public static String getUserName(GraphUser user)
	{
		return user.getName() + " " + user.getLastName();
	}
	
	public static void fetchFriends(List<GraphUser> friends)
	{
		System.out.println("size friends : " + friends.size());
		for(GraphUser friend : friends)
		{
			if(friend == null)
			{
				System.out.println("friend is null");
				return;
			}
			String tempId = friend.getId();
			
			boolean alreadyExist = false;
			for(Profile profile : ProfileManager.profiles)
			{
				if(profile == null)
					System.out.println("profile is null");
				
				if(profile.getId().equals(tempId))
					alreadyExist = true;
			}
			
			if( !alreadyExist )
			{
				System.out.println("fridn name :" + getUserName(friend));
				Profile newFriend = new Profile(getUserName(friend));
				newFriend.setId(tempId);
				ProfileManager.profiles.add(newFriend);
			}
		}
	}
}
