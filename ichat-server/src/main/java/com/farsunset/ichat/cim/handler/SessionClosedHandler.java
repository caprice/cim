 
package com.farsunset.ichat.cim.handler;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.farsunset.cim.nio.handle.AbstractHandler;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;
import com.farsunset.cim.nio.session.DefaultSessionManager;
import com.farsunset.framework.container.ContextHolder;

/**
 * 断开连接，清除session
 * 
 * @author
 */
public class SessionClosedHandler implements AbstractHandler {

	protected final Logger logger = Logger.getLogger(SessionClosedHandler.class);

	public ReplyBody process(IoSession ios, SentBody message) {

	    String account = ((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).getAccount(ios);
		
		if(account != null)
		{
			((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).removeSession(account);
			
		}
		return null;
	}
	
 
	
}