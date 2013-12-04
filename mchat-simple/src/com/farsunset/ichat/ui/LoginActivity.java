package com.farsunset.ichat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.farsunset.cim.client.android.CIMConnectorManager;
import com.farsunset.cim.client.android.CIMConnectorService;
import com.farsunset.cim.nio.constant.CIMConstant;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;
import com.farsunset.ichat.R;
import com.farsunset.ichat.app.CIMMonitorActivity;
public class LoginActivity extends CIMMonitorActivity implements OnClickListener {

	EditText accountEdit;
	Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
	}

	private void initViews()
	{
		
		accountEdit = (EditText) this.findViewById(R.id.account);
		loginButton = (Button) this.findViewById(R.id.login);
		loginButton.setOnClickListener(this);
		
	}
	 
	private void  doLogin()
	{
			
		    if(!"".equals(accountEdit.getText().toString().trim()))
		    {
		    	SentBody sent = new SentBody();
				sent.setKey(CIMConstant.RequestKey.CLIENT_AUTH);
				sent.put("account", accountEdit.getText().toString().trim());
				sent.put("channel", "android");
				showProgressDialog("提示","正在登陆，请稍后......");
				CIMConnectorManager.getManager(this).send(sent);
		    }
			
		
	}

	@Override
	public void onReplyReceived(final ReplyBody reply) {

		if (reply.getKey().equals(CIMConstant.RequestKey.CLIENT_AUTH)) {
			
			if (reply.getCode().equals(CIMConstant.ReturnCode.CODE_200)) {
				
				hideProgressDialog();
				Intent intent = new Intent(this, SystemMessageActivity.class);
				intent.putExtra("account",accountEdit.getText().toString().trim());
				startActivity(intent);
			} 
		}

	}

	
	@Override
	public void onSentFailed(Exception e, SentBody sent) {

		showToask("发送失败");
		hideProgressDialog();
	}



	@Override
	public void onClick(View v) {
		 switch(v.getId())
		 {
		      
		     case  R.id.login:
		    	 doLogin();
				 break;
		 }
		
	}

	 @Override
		public void onBackPressed() {
			 
				stopService(new Intent(this, CIMConnectorService.class));
				this.finish();
		}
}
