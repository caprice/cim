/**
 * @author (3979434@qq.com)
 */
package com.farsunset.cim.client.android;

import android.net.NetworkInfo;

import com.farsunset.cim.nio.mutual.Message;
import com.farsunset.cim.nio.mutual.ReplyBody;
import com.farsunset.cim.nio.mutual.SentBody;

/**
 * 连接 会话事件 接口
 * @author xiajun
 *
 */
public interface OnCIMMessageListener {

	/**
	 * 当客户端和服务端连接断开时调用
	 */
	public void onConnectionClosed();
	/**
	 * 当客户端和服务端连接成功时调用
	 */
	public void onConnectionSuccessful();
	/**
	 * 当客户端和服务端连接失败时调用
	 */
	public void onConnectionFailed(Exception e);
	/**
	 * 当客户端和服务端通信过程中出现异常调用
	 */
	public void onExceptionCaught(Throwable throwable);
	/**
	 * 当客户端收到消息后调用
	 */
	public void onMessageReceived(Message message);
	/**
	 * 当客户端收到请求的回复时调用
	 */
	public void onReplyReceived(ReplyBody reply);
	/**
	 * 当客户端发送请求成功时
	 */
	public void onSentSuccessful(SentBody sentbody);
	/**
	 * 当客户端发送请求失败时
	 */
	public void onSentFailed(Exception e,SentBody sent);
	
	/**
	 * 当手机网络状态发生变化
	 */
	public void onNetworkChanged(NetworkInfo info);
}
