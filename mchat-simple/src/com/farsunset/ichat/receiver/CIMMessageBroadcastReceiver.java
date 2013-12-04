package com.farsunset.ichat.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.farsunset.cim.nio.mutual.Message;
import com.farsunset.ichat.R;
import com.farsunset.ichat.ui.SystemMessageActivity;
/**
 *新 消息广告
 * @author xiajun
 *
 */
public final class CIMMessageBroadcastReceiver extends BroadcastReceiver {
 
	private NotificationManager notificationManager;

 
	@Override
	public void onReceive(Context context, Intent it) {

		
		this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Message msg = (Message) it.getSerializableExtra("message");
		

		
		String title  =  "系统消息: "+ msg.getContent();
				

		Notification notification = new Notification();
		notification.icon = R.drawable.icon_notify;
		notification.defaults = Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.when = Long.valueOf(msg.getTimestamp());
		notification.tickerText = title;

		Intent notificationIntent = new Intent(context,SystemMessageActivity.class);
        
		PendingIntent contentIntent = PendingIntent.getActivity(context,1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		notification.setLatestEventInfo(context,"系统消息", msg.getContent(),contentIntent);
		notificationManager.notify(1, notification);
		

	}

}
