 
package com.farsunset.ichat.cim.handler;


import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.farsunset.cim.nio.constant.CIMConstant;
import com.farsunset.cim.nio.handle.AbstractHandler;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;
import com.farsunset.cim.nio.session.DefaultSessionManager;
import com.farsunset.framework.container.ContextHolder;

/**
 * 鉴权实现
 * 
 * @author
 */
public class AuthHandler implements AbstractHandler {

	protected final Logger logger = Logger.getLogger(AuthHandler.class);

	public ReplyBody process(IoSession ios, SentBody message) {

		
		logger.debug("begin auth :account:" +message.get("account")+"-----------------------------" );
		ReplyBody reply = new ReplyBody();
		try {
			String account = message.get("account");
		 
	 
			ios.setAttribute("channel", message.get("channel"));
			ios.setAttribute("loginTime", System.currentTimeMillis());	
			((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).addSession(account, ios);
				
				 
			reply.setCode(CIMConstant.ReturnCode.CODE_200);
		} catch (Exception e) {
			reply.setCode(CIMConstant.ReturnCode.CODE_500);
			e.printStackTrace();
		}
		logger.debug("end auth :account:" +message.get("account")+"-----------------------------" +reply.getCode());
		return reply;
	}
	
}