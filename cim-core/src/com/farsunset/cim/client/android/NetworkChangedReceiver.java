package com.farsunset.cim.client.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 手机网络状态变化时激发
 * @author xiajun
 *
 */
public class NetworkChangedReceiver extends BroadcastReceiver  {

	@Override
	public void onReceive(Context content, Intent intent) {
		String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
        	ConnectivityManager  connectivityManager = (ConnectivityManager)content.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo   info = connectivityManager.getActiveNetworkInfo();  
            CIMConnectorManager.getManager(content).onNetworkChanged(info);
        }
		
	}

}
