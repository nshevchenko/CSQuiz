package com.example.oopquiz.Managers;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oopquiz.R;
import com.example.oopquiz.Elements.ButtonUI;
import com.example.oopquiz.Elements.Profile;
import com.example.oopquiz.Elements.activityName;
import com.facebook.widget.ProfilePictureView;

public class ProfileViewAdapter extends ArrayAdapter<Profile>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    
    public ProfileViewAdapter ( Context ctx, int resourceId ) 
    {
        super( ctx, resourceId, ProfileManager.profiles);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = ( LinearLayout ) inflater.inflate( resource, null );
        Profile profile = ProfileManager.getProfile(position);
        int[] res = {R.id.top1, R.id.top2, R.id.top3};
        
        for(int i = 0; i < res.length; i++)
        {
        	ButtonUI button = new ButtonUI((Button)convertView.findViewById(res[i]), activityName.leadboards, false);
        	ColorManager.changeButtonColor(button);
        	button.getButton().setText(profile.top3[i]);
        }
        
        //ProfilePicture
        
        ProfilePictureView profilePictureView = (ProfilePictureView) convertView.findViewById(R.id.selection_profile_pic);
        profilePictureView.setProfileId(profile.getId());
        
        /*
		Drawable avaDraw = avatar.getBackground().getCurrent();
		Bitmap bitmap = ((BitmapDrawable)avaDraw).getBitmap();
		bitmap = ProfileManager.getRoundedCornerBitmap(bitmap, 150);
		avaDraw = new BitmapDrawable(context.getResources(), bitmap);
		avatar.setImageDrawable(avaDraw);
		*/
        //image.setImageDrawable((profile.getImage(context)));
        
        
        String[] splitName = profile.getName().split(" ");
        TextView txtName = (TextView) convertView.findViewById(R.id.name);
        txtName.setText(splitName[0]);
        txtName = (TextView) convertView.findViewById(R.id.surname);
        txtName.setText(splitName[1]);
        return convertView;
    }
}
