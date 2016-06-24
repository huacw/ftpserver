package com.sea.ftp.server.context.response;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import com.sea.ftp.constants.Constants;
import com.sea.ftp.context.AbstractContext;
import com.sea.ftp.context.response.FTPResponse;

/**
 * 默认的FTP服务器的响应对象
 * 
 * @author sea
 * 
 */
public class DefaultFTPServerResponse extends AbstractContext implements
		FTPResponse {

	@Override
	public Channel getResponseChannel() {
		Object channelContext = getAtrribute(Constants.KEY_SESSION_STREAM);
		if (channelContext == null) {
			return null;
		}
		if (channelContext instanceof ChannelHandlerContext) {
			return ((ChannelHandlerContext) channelContext).channel();
		}
		return null;
	}

}
