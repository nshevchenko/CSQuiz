package com.example.oopquiz.Managers;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oopquiz.R;
import com.example.oopquiz.Elements.InfoContainer;
import com.example.oopquiz.Elements.InfoProfile;

public class InfoProfileScreenAdapter extends ArrayAdapter<InfoProfile>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    
    public InfoProfileScreenAdapter ( Context ctx, int resourceId ) 
    {
        super( ctx, resourceId, InfoContainer.getData());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @SuppressWarnings("deprecation")
	@Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = ( RelativeLayout ) inflater.inflate( resource, null );
    	InfoProfile info = InfoContainer.getData().get(position);
       
  
        TextView txtName = (TextView) convertView.findViewById(R.id.id);
        txtName.setText(info.name);
        txtName = (TextView) convertView.findViewById(R.id.points);
        txtName.setText(Integer.toString(info.points));
        
        txtName = (TextView)convertView.findViewById(R.id.divider);
        int color = ColorManager.generateColor();
        int[] colors = {0, color, color, 0}; // red for the example
        GradientDrawable grad = new GradientDrawable(Orientation.RIGHT_LEFT, colors);
        txtName.setBackgroundDrawable(grad);
        
        ColorManager.changeViewColor(txtName);
        
        return convertView;
    }
}
