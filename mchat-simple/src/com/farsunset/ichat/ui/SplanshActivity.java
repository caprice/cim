package com.farsunset.ichat.ui;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.farsunset.cim.client.android.CIMConnectorManager;
import com.farsunset.cim.client.android.CIMConnectorService;
import com.farsunset.ichat.R;
import com.farsunset.ichat.app.CIMMonitorActivity;

public class SplanshActivity extends CIMMonitorActivity{
	
	boolean initComplete = false;
	public void onCreate(Bundle savedInstanceState)
	{
		
		startService(new Intent(SplanshActivity.this, CIMConnectorService.class));
		
		super.onCreate(savedInstanceState);
		
		final View view = View.inflate(this, R.layout.activity_splansh, null);
		setContentView(view);
	 
		AlphaAnimation aa = new AlphaAnimation(0.5f,1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
	}
	@Override
	public void onConnectionSuccessful() {
		
		Intent intent = new Intent(SplanshActivity.this,LoginActivity.class);  
		startActivity(intent);
		finish();
	}

	
	/**
	 * 当与服务端连接失败的时候，再次重新连接
	 */
	@Override
	public void onConnectionFailed(Exception  e) {
		
	 
		showToask("与服务端连接失败，请检查IP和端口是否设置正确");
		
		super.onDestroy();
		stopService(new Intent(this, CIMConnectorService.class));
		this.finish();
		
	}
	
	
	 @Override
		public void onBackPressed() {
			 
				stopService(new Intent(this, CIMConnectorService.class));
				this.finish();
		}
}
