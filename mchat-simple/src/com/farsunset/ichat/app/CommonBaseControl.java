package com.farsunset.ichat.app;



import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.farsunset.ichat.R;
 

public   class CommonBaseControl   {
	
	private ProgressDialog progressDialog;  
	
	Context mMontent;
	
	
	public   CommonBaseControl(Context content)
	{
		 this.mMontent = content;
	}
	
	
	
	public void showProgressDialog(String title,String message)
	{
		if(progressDialog==null)
		{
			
			 progressDialog = ProgressDialog.show(mMontent, title, message, true, false);
		}else if(progressDialog.isShowing())
		{
			progressDialog.setTitle(title);
			progressDialog.setMessage(message);
		}
	
		progressDialog.show();
		
	}
	
	public void hideProgressDialog()
	{
	
		if(progressDialog!=null&&progressDialog.isShowing())
		{
			progressDialog.dismiss();
		}
		
	}
	
	public void showToask(String hint){
		
		   Toast toast=Toast.makeText(mMontent,hint,Toast.LENGTH_LONG);
		   toast.show();
	}
 
  
}
