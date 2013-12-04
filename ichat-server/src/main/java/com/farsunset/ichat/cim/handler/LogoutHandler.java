 
package com.farsunset.ichat.cim.handler;

import java.util.HashMap;
import java.util.List;
import org.apache.mina.core.session.IoSession;

import com.farsunset.cim.nio.handle.AbstractHandler;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;
import com.farsunset.cim.nio.session.DefaultSessionManager;
import com.farsunset.framework.container.ContextHolder;
 

/**
 * 退出连接实现
 * 
 *  @author 3979434@qq.com 
 */
public class LogoutHandler implements AbstractHandler {

	public ReplyBody process(IoSession ios, SentBody message) {

		

		String account = message.get("account");
		ios =((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).getSession(account);

		if (ios != null) {
			ios.close(true);
			((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).removeSession(account);
		}
		 
		return null;
	}
	
	
}