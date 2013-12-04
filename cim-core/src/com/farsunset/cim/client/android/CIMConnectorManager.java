package com.farsunset.cim.client.android;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.farsunset.cim.nio.constant.CIMConstant;
import com.farsunset.cim.nio.filter.ClientMessageCodecFactory;
import com.farsunset.cim.nio.mutual.Message;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;

/**
 * 连接服务端管理，处理消息接收分发activity处理
 * 
 * @author 3979434@qq.com
 */
public class CIMConnectorManager extends CIMEventHandlerContainer {

	private NioSocketConnector connector;
	private ConnectFuture connectFuture;
	private IoSession session;

	Context context;

	ArrayList<OnCIMMessageListener> cimListeners;
	
	static CIMConnectorManager manager;

	//消息广播action
	static final String NEW_MSG_ACTION = "com.farsunset.cim.message.RECEIVED";
	
	static int cimServerPort ;
	
	static String  cimServerHost ;
	
	private BlockingQueue<Runnable> queue;
	
	private ThreadPoolExecutor sendExecutor;
	
	
	//客户端是否登录成功到服务端
	public static  boolean CIMLoggedToServer = false;
	
	private CIMConnectorManager(Context context) {

		this.context = context;
		cimListeners = new ArrayList<OnCIMMessageListener>();
		queue = new LinkedBlockingQueue<Runnable>();
		sendExecutor = new ThreadPoolExecutor(2, 3, 10,	TimeUnit.SECONDS, queue);
	}

	public static CIMConnectorManager getManager(Context context) {
		if (manager == null) {
			manager = new CIMConnectorManager(context);
			ComponentName cn=new ComponentName(context, CIMConnectorService.class);
	    	ServiceInfo info = null;
			try {
				info = context.getPackageManager() .getServiceInfo(cn, PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) {e.printStackTrace();}
	    	   
	    	cimServerHost = info.metaData.getString("CIM_SERVER_HOST");
	    	
	    	cimServerPort = info.metaData.getInt("CIM_SERVER_PORT");
		}
		return manager;

	}

	public boolean connect() {

		try {
			
			if(!netWorkAvailable())
			{
				connectionFailedHandler.sendEmptyMessage(0);
				return false;
			}
			
			if(connectFuture!=null&&session!=null&&connectFuture.isConnected() && session.isConnected() )
			{
				return true;
			}
			
			
			if(connector==null || connector.isDisposed())
			{
				connector = new NioSocketConnector();
				connector.setConnectTimeoutMillis(10 * 1000);
				connector.getSessionConfig().setBothIdleTime(180);
				connector.getSessionConfig().setKeepAlive(true);
				connector.getFilterChain().addLast("logger", new LoggingFilter());
				connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ClientMessageCodecFactory()));
				connector.setHandler(iohandler);
			}
			
	    	
			connectFuture= connector.connect(new InetSocketAddress(cimServerHost, cimServerPort));
			
			connectFuture.awaitUninterruptibly();
			
			session = connectFuture.getSession();
			
			
			return true;

		} catch (Exception e) {

			android.os.Message msg = new android.os.Message();
			msg.getData().putSerializable("e", e);
			connectionFailedHandler.sendMessage(msg);
            e.printStackTrace();
			return false;
		}

	}

	public void registerMessageListener(OnCIMMessageListener listener) {
		
		if(!cimListeners.contains(listener))
		{
			cimListeners.add(listener);
			//按照接收顺序倒序
			Collections.sort(cimListeners, new CIMMessageReceiveComparator(context));
		}
	}

	public boolean close() {
		if(session!=null)
		{
			session.close(true);
		}
		
		if(connector!=null&&!connector.isDisposed())
		{
			connector.dispose();
		}
		return true;
	}
	
	public boolean isConnected()
	{
		if(session==null || connector==null)
		{
			return false;
		}
		return session.isConnected();
	}

	public void removeMessageListener(OnCIMMessageListener listener) {
		 for(int i=0;i<cimListeners.size();i++)
		 {
			 if(listener.getClass() == cimListeners.get(i).getClass())
			 {
				 cimListeners.remove(i);
			 }
		 }
	}
	
	
	 

	public void send(final SentBody body) {
		
		
		sendExecutor.execute(new Runnable() {
			@Override
			public void run() {
				 
				if(session==null || !session.isConnected())
				{
				   connect();
				}
			   boolean 	fialg  = session.write(body).awaitUninterruptibly().isWritten();
		       if(!fialg)
		       {
		    	   android.os.Message msg = new android.os.Message();
				   msg.getData().putSerializable("body",body);
				   messageSentFailedHandler.sendMessage(msg);
		       }
			}
		});
		
	 
		
	}

	IoHandlerAdapter iohandler = new IoHandlerAdapter() {

		@Override
		public void sessionCreated(IoSession session) throws Exception {
 
			System.out.println("******************CIM连接服务器成功^_^ ^_^ ^_^ ^_^ ^_^ ^_^ ^_^");
			sessionCreatedHandler.sendEmptyMessage(0);
			
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {

		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {

			System.out.println("******************CIM与服务器断开连接-_-! -_-! -_-! -_-! -_-! -_-!");
			sessionClosedHandler.sendEmptyMessage(0);
		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus status)throws Exception {

			System.out.println("******************CIM与服务器连接空闲+_+ +_+ +_+ +_+ +_+ +_+ +_+ +_+");
			SentBody sent = new SentBody();
			sent.setKey(CIMConstant.RequestKey.CLIENT_HEARTBEAT);
			send(sent);
		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {

			cause.printStackTrace();
			android.os.Message msg = new android.os.Message();
			msg.getData().putSerializable("e", cause);
			exceptionCaughtHandler.sendMessage(msg);
		}

		@Override
		public void messageReceived(IoSession session, Object obj)
				throws Exception {

			try {
				Log.d(CIMConnectorManager.class.getName(), obj.toString());
				if (obj instanceof Message) {

					if (cimListeners.isEmpty()|| isInBackground()) {
						Intent intent = new Intent();
						intent.setAction(NEW_MSG_ACTION);
						intent.putExtra("message",(Message) obj);
						context.sendBroadcast(intent);
					}
					
					android.os.Message msg = new android.os.Message();
					msg.getData().putSerializable("message", (Message) obj);
					messageReceivedHandler.sendMessage(msg);

				}
				if (obj instanceof ReplyBody) {

					android.os.Message msg = new android.os.Message();
					msg.getData().putSerializable("reply", (ReplyBody) obj);
					replyReceivedHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void messageSent(IoSession session, Object message)
				throws Exception {

			android.os.Message msg = new android.os.Message();
			msg.getData().putSerializable("message", (SentBody) message);
			messageSentSuccessfulHandler.sendMessage(msg);
		}
	};

	@Override
	public void createMessageReceivedHandler() {

		super.messageReceivedHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				Message body = (Message) msg.getData().getSerializable( "message");
				StringBuilder sb = new StringBuilder("******************CIMMessageListeners: \n[\n");
				for (OnCIMMessageListener   listener:cimListeners)
				{
					sb.append(listener.getClass().getName()).append("\n");
				}
				sb.append("]");
				System.out.println(sb.toString());
				
				for (OnCIMMessageListener   listener:cimListeners)
				{
					System.out.println("########################"+(listener.getClass().getName()+".onMessageReceived################"));
					listener.onMessageReceived(body);
				}
				 
			}

		};
	}

	@Override
	public void createReplyReceivedHandler() {
		super.replyReceivedHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				ReplyBody reply = (ReplyBody) msg.getData().getSerializable("reply");
				for (OnCIMMessageListener   listener:cimListeners) {

					listener.onReplyReceived(reply);
				}
			}
		};
	}

	@Override
	public void createMessageSentSuccessfulHandler() {
		super.messageSentSuccessfulHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				SentBody sentbody = (SentBody) msg.getData().getSerializable("body");
				for (OnCIMMessageListener   listener:cimListeners)
				{

					listener.onSentSuccessful(sentbody);
				}
			}
		};
	}

	@Override
	public void createExceptionCaughtHandler() {
		exceptionCaughtHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				Throwable e = (Throwable) msg.getData().getSerializable("e");
				try {
					for (OnCIMMessageListener   listener:cimListeners)
					{

						listener.onExceptionCaught(e);
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	@Override
	public void createSessionClosedHandler() {
		super.sessionClosedHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				for (OnCIMMessageListener   listener:cimListeners)
				{

					listener.onConnectionClosed();
				}

				 
			}
		};
	}

	@Override
	public void createSessionCreatedHandler() {
		super.sessionCreatedHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				for (OnCIMMessageListener   listener:cimListeners) {

					listener.onConnectionSuccessful();
				}
			}
		};
	}

	@Override
	public void createMessageSentFailedHandler() {
		super.messageSentFailedHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				Exception e = (Exception) msg.getData().getSerializable("e");
				SentBody sent = (SentBody) msg.getData().getSerializable("body");
				for (OnCIMMessageListener   listener:cimListeners){

					listener.onSentFailed(e, sent);
				}
			}
		};
	}

	protected void onNetworkChanged(NetworkInfo info){
		
		
		for (OnCIMMessageListener   listener:cimListeners){

			listener.onNetworkChanged(info);
		}
		
	}
	
	
	
	@Override
	public void createConnectionFailedHandler() {
		super.connectionFailedHandler = new Handler() {

			public void handleMessage(android.os.Message msg) {

				Exception e = (Exception) msg.getData().getSerializable("e");
				for (OnCIMMessageListener   listener:cimListeners) {
					listener.onConnectionFailed(e);
				}
			}
		};
	};

	 
	private boolean isInBackground(){  
		List<RunningTaskInfo>  tasksInfo = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);  
        if(tasksInfo.size() > 0){  
            
            if(context.getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())){  
            	
                return false;  
            }  
        }  
        return true;  
    }

	
	public   boolean netWorkAvailable() {
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=connectivityManager.getActiveNetworkInfo();
        if(net==null){
          return false;
       }else{
    	   return true;
       }
	}
	
 

		
}
