package com.example.oopquiz;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SharePopup extends DialogFragment
{
	public String title, message;
	
    public SharePopup(String title, String message)
    {
    	this.title = title;
    	this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
    	String[] items = {"Facebook", "Twitter"};
        return new AlertDialog.Builder(getActivity())
        	.setTitle("Share question with:")
            .setSingleChoiceItems(items, -1, 
            		null
            		)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                	 int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                	 System.out.println(selectedPosition);
                }
            })
            .show();
    }
}
