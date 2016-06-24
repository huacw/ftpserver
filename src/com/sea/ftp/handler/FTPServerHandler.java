package com.sea.ftp.handler;

import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.log4j.Logger;

import com.sea.ftp.context.application.FTPServerContext;
import com.sea.ftp.message.MessageResource;
import com.sea.ftp.message.i18n.LocalizedMessageResource;

/**
 * 
 * FTP服务器处理器
 * 
 * @author sea 
 */
public abstract class FTPServerHandler extends
		SimpleChannelInboundHandler<String> {
	protected Logger logger = Logger.getLogger(getClass());

	// FTP服务器上下文
	protected FTPServerContext serverContext;
	// FTP回复消息管理器
	protected MessageResource messageResource;

	public FTPServerContext getServerContext() {
		return serverContext;
	}

	public void setServerContext(FTPServerContext serverContext) {
		this.serverContext = serverContext;
	}

	public MessageResource getMessageResource() {
		return messageResource == null ? LocalizedMessageResource.newInstance()
				: messageResource;
	}

	public void setMessageResource(MessageResource messageResource) {
		this.messageResource = messageResource;
	}

}
