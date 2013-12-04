package com.farsunset.ichat.admin.action;


import java.io.IOException;

import com.farsunset.cim.nio.mutual.Message;
import com.farsunset.cim.nio.session.DefaultSessionManager;
import com.farsunset.framework.container.ContextHolder;
import com.farsunset.framework.web.SuperAction;
public class SessionAction extends SuperAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	 
 
	  
	   
	 public String list()  
	 {  
		  request.setAttribute("sessionList", ((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).getSessions());
		  
		  return "list";
	}
 
	 public void offline() throws IOException  
	 {  
		 
		 String account = request.getParameter("account");
		  Message msg = new Message();
		  msg.setType("999");//强行下线消息类型
		  msg.setReceiver(account);
		  
		  ((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).getSession(account).write(msg);
		  
		  ((DefaultSessionManager) ContextHolder.getBean("defaultSessionManager")).removeSession(account);
	 
	}
}
