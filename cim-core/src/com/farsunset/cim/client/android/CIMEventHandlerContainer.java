 
package com.farsunset.cim.client.android;

import android.os.Handler;

 /**
  * 消息处理，用于ui线程处理消息事件
  * @author 3979434@qq.com
  *
  */
public abstract class CIMEventHandlerContainer {

	protected Handler messageReceivedHandler;

	protected Handler replyReceivedHandler;

	protected Handler messageSentSuccessfulHandler;

	protected Handler exceptionCaughtHandler;

	protected Handler sessionClosedHandler;

	protected Handler sessionCreatedHandler;

	protected Handler messageSentFailedHandler;

	protected Handler connectionFailedHandler;
	
	protected Handler networkChangedHandler;
	
	
	public abstract void createMessageReceivedHandler();

	public abstract void createConnectionFailedHandler();

	public abstract void createReplyReceivedHandler();

	public abstract void createMessageSentSuccessfulHandler();

	public abstract void createExceptionCaughtHandler();

	public abstract void createSessionClosedHandler();

	public abstract void createSessionCreatedHandler();

	public abstract void createMessageSentFailedHandler();
	

}
