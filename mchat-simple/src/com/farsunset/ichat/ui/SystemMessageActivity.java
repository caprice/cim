package com.farsunset.ichat.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.farsunset.cim.client.android.CIMConnectorManager;
import com.farsunset.cim.client.android.CIMConnectorService;
import com.farsunset.cim.nio.constant.CIMConstant;
import com.farsunset.cim.nio.mutual.Message;
import com.farsunset.cim.nio.mutual.SentBody;
import com.farsunset.ichat.R;
import com.farsunset.ichat.adapter.SystemMsgListViewAdapter;
import com.farsunset.ichat.app.CIMMonitorActivity;
import com.farsunset.ichat.app.Constant;
import com.farsunset.ichat.util.API;

public class SystemMessageActivity extends CIMMonitorActivity implements OnClickListener{

	protected ListView chatListView;
	int currentPage = 1;
	protected SystemMsgListViewAdapter adapter;

	private ArrayList<Message> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_chat);

		initViews();
	}

	public void initViews() {

		list = new ArrayList<Message>();

		chatListView = (ListView) findViewById(R.id.chat_list);
		findViewById(R.id.TOP_BACK_BUTTON).setOnClickListener(this);
		findViewById(R.id.TOP_BACK_BUTTON).setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.TOP_BACK_BUTTON)).setText("登录");
		((TextView) findViewById(R.id.TITLE_TEXT)).setText("系统消息");
		((TextView) findViewById(R.id.account)).setText(this.getIntent().getStringExtra("account"));
		 
		 adapter = new SystemMsgListViewAdapter(this, list);
		 chatListView.setAdapter(adapter);

	}

 
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.TOP_BACK_BUTTON: {
				
				
				onBackPressed();
				break;
			}

	 
		}
	}

	@Override
	public void onBackPressed() {

		
		//
		SentBody sent = new SentBody();
		sent.setKey(CIMConstant.RequestKey.CLIENT_LOGOUT);
		sent.put("account",this.getIntent().getStringExtra("account"));
	    CIMConnectorManager.getManager(this).send(sent);
	    this.finish();
	}

	@Override
	public void onMessageReceived(com.farsunset.cim.nio.mutual.Message message) {
		 
		if(message.getType().equals(Constant.MessageType.TYPE_999))
		{
			  this.showToask("你被迫下线!");
			  startService(new Intent(this, CIMConnectorService.class));
			  this.finish();
		}else
		{
			MediaPlayer.create(this, R.raw.classic).start();
			list.add(message);
			adapter.notifyDataSetChanged();
			chatListView.setSelection(chatListView.getTop());
			
		}

	}

 
    //发送消息示范
	private void  sendMessage() throws Exception
	{
		
		final  Message msg = new Message();
		 msg.setContent("message内容");
		 msg.setSender("xiaogou");
		 msg.setReceiver("xiaomao");
		 msg.setType(Constant.MessageType.TYPE_0);  
		 //注意需要再子线程里面发送消息用handler 或者AsyncTask
		 
		 new Thread(){
			 
			 
			 public void run()
			 {
				 
				try {
					LinkedHashMap<String, String> resultData = API.sendMessage(msg, null);
					
					 if(resultData.get("code").equals(CIMConstant.ReturnCode.CODE_200))
					 {
						 //消息发送成功
						 //消息ID resultData.get("code")
						//消息发送时间 resultData.get("timestamp")
						 System.out.print("发送成功");
					 }
					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//如果发送文件，在这里传入文件
					
				
				 
				 
			 }
		 }.start();
		
		
		
	}

}
