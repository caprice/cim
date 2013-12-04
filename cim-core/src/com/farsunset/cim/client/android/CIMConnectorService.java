 
package com.farsunset.cim.client.android;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


 
public class CIMConnectorService extends Service {

    
    CIMConnectorManager manager;

	private ExecutorService connectExecutor;

    @Override
    public void onCreate() {
       
    	manager = CIMConnectorManager.getManager(this.getApplicationContext());
    	
    	connectExecutor =  Executors.newSingleThreadExecutor();
    }

    @Override
    public void onStart(Intent intent, int startId) {
    	
    	manager.createExceptionCaughtHandler();
    	manager.createMessageReceivedHandler();
    	manager.createMessageSentFailedHandler();
    	manager.createMessageSentSuccessfulHandler();
    	manager.createReplyReceivedHandler();
    	manager.createSessionClosedHandler();
    	manager.createSessionCreatedHandler();
    	manager.createConnectionFailedHandler();
    	
    	connectExecutor.execute(new Runnable() {
			@Override
			public void run() {
				manager.connect(); 
			}
		});
    	
    	
    	
    }

    @Override
    public void onDestroy() {
    	manager.close(); 
    }

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
 
}
