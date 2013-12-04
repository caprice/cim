 
package com.farsunset.ichat.cim.push;

import java.io.UnsupportedEncodingException;
 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.aspectj.bridge.MessageUtil;

import com.farsunset.cim.nio.mutual.Message;
import com.farsunset.cim.nio.session.DefaultSessionManager;
import com.farsunset.framework.container.ContextHolder;
import com.farsunset.framework.tools.ConfigManager;
 

/**
 * 消息发送实现类
 * 
 * @author farsunset (3979434@qq.com)
 */
public class MessageHandler {


	private final Log log = LogFactory.getLog(getClass());

	private DefaultSessionManager sessionManager;
	
	 

	/**
	 * Constructor.
	 */
	public MessageHandler() {
		
		sessionManager =  (DefaultSessionManager) ContextHolder.getBean("defaultSessionManager");
	}
	
	
 
    /**
     * 向用户发送消息
     * @param msg
     */
	public void sendMessageToUser(Message msg) {
		log.debug("sendNotifcationToUser()...");
		IoSession session = sessionManager.getSession(msg.getReceiver());
		
		if (session != null && session.isConnected()) {
			//推送消息
			session.write(msg);
		} 
	}

 
}
