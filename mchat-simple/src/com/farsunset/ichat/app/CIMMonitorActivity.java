package com.farsunset.ichat.app;



import android.app.Activity;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.farsunset.cim.client.android.CIMConnectorManager;
import com.farsunset.cim.client.android.OnCIMMessageListener;
import com.farsunset.cim.nio.mutual.Message;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;

public  abstract  class CIMMonitorActivity extends Activity implements OnCIMMessageListener{
	
	
	CommonBaseControl commonBaseControl;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		CIMConnectorManager.getManager(this).registerMessageListener(this);
		
		commonBaseControl = new CommonBaseControl(this);
		
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		CIMConnectorManager.getManager(this).removeMessageListener(this);
		
	}
 
	@Override
	public void onRestart() {
		super.onRestart();
		CIMConnectorManager.getManager(this).registerMessageListener(this);
	}
	
	
	public void showProgressDialog(String title,String content)
	{
		commonBaseControl.showProgressDialog(title, content);
	}
	
	public void hideProgressDialog()
	{
		commonBaseControl.hideProgressDialog();
	}
	
	public void showToask(String hint){
		
		commonBaseControl.showToask(hint);
	}
	
	
	 

	

	@Override
	public void onConnectionFailed(Exception  e) {	}
	 
	@Override
	public void onConnectionClosed() {}

	@Override
	public void onConnectionSuccessful() {}
	
	@Override
	public void onReplyReceived(ReplyBody reply) {}
	
	@Override
	public void onMessageReceived(Message arg0) {}

	@Override
	public void onSentSuccessful(SentBody sentbody) {}

	@Override
	public void onSentFailed(Exception exception, SentBody sentbody) {}
	
	@Override
	public void onExceptionCaught(Throwable throwable) {}
	
	@Override
	public   void onNetworkChanged(NetworkInfo info){};
}
