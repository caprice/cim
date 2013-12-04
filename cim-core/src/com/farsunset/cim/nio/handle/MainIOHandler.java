 
package com.farsunset.cim.nio.handle;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.farsunset.cim.nio.constant.CIMConstant;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;

/**
 *  
 * 
 * @author farsunset (3979434@qq.com)
 */
public class MainIOHandler extends IoHandlerAdapter {

	private static final Log log = LogFactory.getLog(MainIOHandler.class);

	private HashMap<String, AbstractHandler> handlers = null;

	public MainIOHandler() {

		handlers = new HashMap<String, AbstractHandler>();
	}

	public void setHandlers(HashMap<String, AbstractHandler> handlers) {
		this.handlers = handlers;
	}

	 
	public void sessionCreated(IoSession session) throws Exception {
		log.warn("sessionCreated()... from "+session.getRemoteAddress().toString());
		
	}

	 
	public void sessionOpened(IoSession session) throws Exception {

	}

	 
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		log.debug("message: " + message.toString());

		ReplyBody reply = new ReplyBody();
		SentBody body = (SentBody) message;
		String key = body.getKey();

		AbstractHandler handler = handlers.get(key);
		if (handler == null) {
			reply.setCode(CIMConstant.ReturnCode.CODE_405);
			reply.setCode("KEY ["+key+"] 服务端未定义");
		} else {
			reply = handler.process(session, body);
		}
		
        if(reply!=null)
        {
        	reply.setKey(key);
    		session.write(reply);
    		log.debug("-----------------------process done. reply: " + reply.toString());
        }
		

	}

	/**
	 */
	public void sessionClosed(IoSession session) throws Exception {
		try{
			log.warn("sessionClosed()... from "+session.getRemoteAddress());
			AbstractHandler handler = handlers.get("sessionClosedHander");
			if(handler!=null && session.getRemoteAddress()==null)
			{
				handler.process(session, null);
			}
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 */
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		log.warn("sessionIdle()... from "+session.getRemoteAddress().toString());
	}

	/**
	 */
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		log.error("exceptionCaught()... from "+session.getId());
		log.error(cause);
		cause.printStackTrace();
		session.close(true);
	}

	/**
	 */
	public void messageSent(IoSession session, Object message) throws Exception {
	}

}