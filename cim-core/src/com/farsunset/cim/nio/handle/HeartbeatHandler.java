 
package com.farsunset.cim.nio.handle;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.farsunset.cim.nio.constant.CIMConstant;
import com.farsunset.cim.nio.handle.AbstractHandler;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;

/**
 *客户端心跳
 * 
 * @author
 */
public class HeartbeatHandler implements AbstractHandler {

	protected final Logger logger = Logger.getLogger(HeartbeatHandler.class);

	public ReplyBody process(IoSession session, SentBody message) {

		logger.warn("heartbeat... from "+session.getRemoteAddress().toString());
		ReplyBody reply = new ReplyBody();
		reply.setKey(CIMConstant.RequestKey.CLIENT_HEARTBEAT);
		reply.setCode(CIMConstant.ReturnCode.CODE_200);
		session.setAttribute("heartbeat", System.currentTimeMillis());
		 
		return reply;
	}
	
 
	
}